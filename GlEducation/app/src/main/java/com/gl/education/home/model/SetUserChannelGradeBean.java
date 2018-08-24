package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/8.
 */

public class SetUserChannelGradeBean {

    /**
     * result : 1000
     * message : 操作成功
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
