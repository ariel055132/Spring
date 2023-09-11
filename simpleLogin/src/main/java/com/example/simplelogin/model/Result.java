package com.example.simplelogin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    // Messages
    private String message;

    // Login status? (Success/Fail)
    private boolean success;

    private T data;

    /**
     * Login success
     *
     * @param msg
     */
    public void setResultSuccess(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }

    /**
     * Login success (Overloading)
     *
     * @param msg
     * @param data
     */
    public void setResultSuccess(String msg, T data) {
        this.message = msg;
        this.success = true;
        this.data = data;
    }

    /**
     * Login Fail
     *
     * @param msg
     */
    public void setResultFailed(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }
}
