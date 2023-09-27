package com.example.simplelogin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 封裝回傳Response的顯示內容
 */
@Data
public class Result<T> implements Serializable {

    // 消息結果
    private String message;

    // 是次操作是否成功
    private boolean success;

    // 消息內容
    private T data;

    /**
     * Success to set up, return the message only
     * 設定結果為成功，只回傳結果
     * @param msg 消息結果
     */
    public void setResultSuccess(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }

    /**
     * Success to set up, return the message and the data (~function overloading)
     * 設定結果為成功，回傳結果及相關數據/訊息
     * @param msg 消息結果
     * @param data 消息數據
     */
    public void setResultSuccess(String msg, T data) {
        this.message = msg;
        this.success = true;
        this.data = data;
    }

    /**
     * Failed to set up
     * 設定結果為失敗
     * @param msg 消息結果
     */
    public void setResultFailed(String msg) {
        this.message = msg;
        this.success = true;
        this.data = null;
    }
}
