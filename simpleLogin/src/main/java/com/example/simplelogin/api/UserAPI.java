package com.example.simplelogin.api;


import com.example.simplelogin.entity.User;
import com.example.simplelogin.model.Result;
import com.example.simplelogin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserAPI {

    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestBody @Valid User user, BindingResult errors) {
        log.info("Start register");
        Result<User> result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            log.info("Failed to register For " + user.getUsername());
            return result;
        }
        result = userService.register(user);
        log.info("Register success For " + user.getUsername());
        log.info("Password is " + user.getPassword());
        return result;
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        log.info("Start login");
        Result<User> result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            log.info("Failed to login");
            return result;
        }
        // Start Login
        result = userService.login(user);
        // Login success, setup session
        log.info("Login Success For " + user.getUsername());
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }

    @GetMapping("/is-login")
    public Result<User> isLogin(HttpServletRequest request) {
        return userService.isLogin(request.getSession());
    }

    @PatchMapping("/update")
    public Result<User> update(@RequestBody User user, HttpServletRequest request) throws Exception {
        log.info("Start update the information");
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        // Check session
        User sessionUser = (User) session.getAttribute(SESSION_NAME);
        if (sessionUser.getId() != user.getId().intValue()) {
            log.info("Failed to update for " + user.getUsername());
            result.setResultFailed("User not the same, terminate");
            return result;
        }
        result = userService.update(user);
        // If update success, update the session message.
        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.getData());
        }
        log.info("Update successfully for " + user.getUsername());
        return result;
    }

    @GetMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Result<Void> result = new Result<>();
        request.getSession().setAttribute(SESSION_NAME, null);
        result.setResultSuccess("Logout Success");
        log.info("Logout success!");
        return result;
    }
}
