package com.gl.education.home.model;

/**
 * Created by zy on 2018/8/29.
 */

public class UserProfileBean {
    
    /**
     * result : 1000
     * message : 操作成功
     * data : 1
     */

    private int result;
    private String message;
    private int data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
