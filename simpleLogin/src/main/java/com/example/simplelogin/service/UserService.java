package com.example.simplelogin.service;

import com.example.simplelogin.entity.User;
import com.example.simplelogin.model.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * Register
 * Login
 * Update user message
 */

@Service
public interface UserService {
    /**
     * Register
     *
     * @param user
     * @return register result
     */
    Result<User> register(User user);

    /**
     * Login
     *
     * @param user
     * @return login result
     */
    Result<User> login(User user);

    /**
     * Update user message
     *
     * @param user
     * @return result
     */
    Result<User> update(User user) throws Exception;

    /**
     *
     *
     * @param session
     * @return login status (login already or not)
     */
    Result<User> isLogin(HttpSession session);
}
