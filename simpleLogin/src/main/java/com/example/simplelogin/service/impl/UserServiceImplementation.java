package com.example.simplelogin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.example.simplelogin.api.UserAPI;
import com.example.simplelogin.dao.UserDAO;
import com.example.simplelogin.entity.User;
import com.example.simplelogin.model.Result;
import com.example.simplelogin.service.UserService;
import jakarta.servlet.http.HttpSession;
import com.example.simplelogin.util.ClassExamine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        // Find the user from database
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser != null) {
            result.setResultFailed("User exists");
            return result;
        }
        // Encrypt the password
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        // Save the result to database
        userDAO.add(user);
        // Return the message (Register success)
        result.setResultSuccess("Register successfully ", user);
        return result;
    }

    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        // Find the user from database
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser == null) {
            result.setResultFailed("User does not exist");
            return result;
        }
        // Compare the password
        if (!getUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            result.setResultFailed("Wrong Password! ");
            return result;
        }
        // Return the message (Login success)
        result.setResultSuccess("Login Success", getUser);
        return result;
    }

    @Override
    public Result<User> update(User user) throws Exception {
        Result<User> result = new Result<>();
        // Find the user from database
        User getUser = userDAO.getById(user.getId());
        if (getUser == null) {
            result.setResultFailed("User does not exist");
            return result;
        }
        //
        if (!StrUtil.isEmpty(user.getPassword())) {
            // Encrypt saving
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        }

        ClassExamine.objectOverlap(user, getUser);
        // Save the result to database
        userDAO.update(user);
        result.setResultSuccess("Edit success! ", user);
        return result;
    }

    @Override
    public Result<User> isLogin(HttpSession session) {
        Result<User> result = new Result<>();
        // Get the user message / status from session
        User sessionUser = (User) session.getAttribute(UserAPI.SESSION_NAME);
        // Session does not have message --> User does not login
        if (sessionUser == null) {
            result.setResultFailed("User does not login");
            return result;
        }
        User getUser = userDAO.getById(sessionUser.getId());

        result.setResultSuccess("User login ", getUser);
        return result;
    }

}
