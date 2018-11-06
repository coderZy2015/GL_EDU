package com.gl.education.home.model;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    private int result;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}