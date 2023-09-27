package com.example.simplelogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

/**
 * 設定用戶的帳密(Username, Password)，並利用Spring Validation設定驗證軌則
 * NotEmpty, Size
 */
@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class User implements Serializable {
    /**
     * User ID (用戶ID)
     */
    private Integer id;

    /**
     * User name (用戶名字)
     */
    @NotEmpty(message = "Username cannot be null!")
    private String username;

    /**
     * Password (用戶密碼)
     */
    @NotEmpty(message = "Password cannot be null!")
    @Size(min = 8, message = "Password length cannot shorter than 8.")
    private String password;

}
