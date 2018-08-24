package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/23.
 */

public class GetHotKeyBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : ["高考","考研"]
     */

    private int result;
    private String message;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
