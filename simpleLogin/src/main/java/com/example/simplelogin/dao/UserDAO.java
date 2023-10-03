package com.example.simplelogin.dao;

import com.example.simplelogin.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * DAO主要負責JAVA對DB操作的介面(Interface)和介面實現(Implementation)
 * 介面主要實現三個功能：新增用戶，修改用戶資料，(根據用戶ID或名稱)獲取相關用戶
 * MyBatis只要定義Interface即可
 */
@Mapper
public interface UserDAO {

    /**
     * Create User
     * 新增用戶
     * @param user 用戶實例(instance)
     * @return Number of records added successfully (可成功新增之用戶數量)
     */
    int add(User user);


    /**
     * Edit the user message
     * 修改用戶資料
     * @param user 需修改之用戶實例
     * @return Number of records updated successfully (可成功修改資料之用戶數量)
     */
    int update(User user);

    /**
     * Get the User with id
     * 根據用戶輸入之ID，獲取相關用戶
     * @param id 用戶Id
     * @return User 用戶實例(instance)
     */
    User getById(Integer id);

    /**
     * Get the User with username
     * 根據用戶名稱，獲取相關用戶
     * @param username 用戶名稱
     * @return User 用戶實例(instance)
     */
    User getByUsername(String username);
}
