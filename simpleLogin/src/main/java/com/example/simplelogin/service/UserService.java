package com.example.simplelogin.service;

import com.example.simplelogin.entity.User;
import com.example.simplelogin.model.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * Perform the operation logic. 實現Service的邏輯 (也就是業務邏輯)
 * Register (用戶註冊)
 * Login (用戶登錄)
 * Update user message (更新/修改用戶信息)
 */

@Service
public interface UserService {
    /**
     * Register (用戶註冊)
     * @param user 用戶實例 (instance)
     * @return register result (註冊結果)
     */
    Result<User> register(User user);

    /**
     * Login (用戶登錄)
     * @param user 用戶實例 (instance)
     * @return login result (登錄結果)
     */
    Result<User> login(User user);

    /**
     * Update user message (更新/修改用戶信息)
     * @param user 用戶實例 (instance)
     * @return update result (更新結果)
     */
    Result<User> update(User user) throws Exception;

    /**
     * Determine the login status of user (判斷用戶之登錄狀態 --> 是否已經登錄)
     * @param session 用戶
     * @return login status (login already or not) (當前登錄狀態)
     */
    Result<User> isLogin(HttpSession session);
}
