package com.gl.education.login.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/6/28.
 */

public class ForgetPasswordBean implements Serializable {


    /**
     * result : 1000
     * message : 修改成功
     * data : []
     */

    private int result;
    private String message;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
