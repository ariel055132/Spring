package com.example.simplelogin.dao;

import com.example.simplelogin.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    /**
     * Create User
     *
     * @param user
     * @return
     */
    int add(User user);


    /**
     * Edit the user message
     *
     * @param user
     * @return
     */
    int update(User user);

    /**
     * Get the User with id
     *
     * @param id
     * @return User
     */
    User getById(Integer id);

    /**
     * Get the User with username
     *
     * @param username
     * @return User
     */
    User getByUsername(String username);
}
