package com.example.simplelogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class User implements Serializable {
    /**
     * User ID
     */
    private Integer id;

    /**
     * User name
     */
    @NotEmpty(message = "Username cannot be null!")
    private String username;

    /**
     * Password
     */
    @NotEmpty(message = "Password cannot be null!")
    @Size(min = 8, message = "Password length cannot shorter than 8.")
    private String password;

}
