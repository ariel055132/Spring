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

/**
 * 實現Interface所定義的功能
 * register: 用戶註冊
 * login: 用戶登入
 * update: 修改用戶訊息
 * isLogin: 判斷用戶目前之登入狀態（是否已經登入）
 */


@Component
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserDAO userDAO;

    /**
     * Login (用戶登錄)
     * @param user 用戶實例 (instance)
     * @return login result (登錄結果) 成功/失敗
     */
    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        // 檢查用戶名稱是否存在
        // Check if the username exists (in database)
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser != null) {
            result.setResultFailed("User exists");
            return result;
        }
        // 若用戶名稱不存在(新用戶)，加密用戶之密碼
        // If the username does not exist (in database), that means it is new user.
        // Encrypt user's password.
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        // 儲存用戶資料(名稱與密碼)
        // Save the username and password to database
        userDAO.add(user);
        // 顯示/Return 成功新增用戶訊息
        // Return the message (Register success)
        result.setResultSuccess("Register successfully ", user);
        return result;
    }

    /**
     * Login (用戶登錄)
     * @param user 用戶實例 (instance)
     * @return login result (登錄結果) 成功/失敗
     */
    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        // 透過用戶名稱進行搜尋，判斷用戶是否存在
        // Find the user from database
        User getUser = userDAO.getByUsername(user.getUsername());
        if (getUser == null) {
            result.setResultFailed("User does not exist");
            return result;
        }
        // 比對密碼，先進行解密，然後再進行比對
        // Compare the password
        if (!getUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            result.setResultFailed("Wrong Password! ");
            return result;
        }
        // 顯示/Return 成功登入訊息
        // Return the message (Login success)
        result.setResultSuccess("Login Success", getUser);
        return result;
    }

    /**
     * update (更新用戶資料)
     * @param user 用戶實例 (instance)
     * @return login result (更新資料結果) 成功/失敗
     */
    @Override
    public Result<User> update(User user) throws Exception {
        Result<User> result = new Result<>();
        // 透過用戶名稱進行搜尋，判斷用戶是否存在
        // Find the user from database
        User getUser = userDAO.getById(user.getId());
        if (getUser == null) {
            result.setResultFailed("User does not exist");
            return result;
        }
        // 修改密碼
        if (!StrUtil.isEmpty(user.getPassword())) {
            // Encrypt saving
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        }
        // 處理用戶存在(username exist)，但沒有進行修改(Password欄位為空) --> 補上原本的資料
        // 封裝方法，檢查被補全Object(由使用者輸入)和原本Object(從資料庫取得)是否一致/修改
        // 若被補全的Object的某個欄位為空，代表該欄位不需進行修改，使用原本Object欄位的值進行輸入
        // 若被補全的Object的某個欄位不為空，代表改欄位的值需修改(新的值)，使用被補全Object欄位的值進行輸入
        ClassExamine.objectOverlap(user, getUser);
        // 儲存用戶資料(名稱與密碼)
        // Save the result to database
        userDAO.update(user);
        // Return 更新資料成功
        result.setResultSuccess("Edit success! ", user);
        return result;
    }

    /**
     * isLogin (判斷用戶當前登入狀態)
     * @param user 用戶實例 (instance)
     * @return login result (用戶之登入狀態)
     */
    @Override
    public Result<User> isLogin(HttpSession session) {
        Result<User> result = new Result<>();
        // 透過Session來取得用戶當前登入狀態
        // Get the user message / status from session
        User sessionUser = (User) session.getAttribute(UserAPI.SESSION_NAME);
        // Session中沒有用戶訊息，代表該用戶尚未登入
        // Session does not have message --> User does not login
        if (sessionUser == null) {
            result.setResultFailed("User does not login");
            return result;
        }
        // 若用戶已登入，使用其Username，從資料庫中取得相關訊息進行比對
        User getUser = userDAO.getById(sessionUser.getId());
        // 若Session用戶找不到相關用戶，或密碼不一致，代表用戶session無效，需重新進行登入
        if (getUser == null || !getUser.getPassword().equals(sessionUser.getPassword())) {
            result.setResultFailed("User message invalid");
            return result;
        }
        // Return 用戶目前已登入狀態
        result.setResultSuccess("User login ", getUser);
        return result;
    }

}
