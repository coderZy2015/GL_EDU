package com.gl.education.login.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/6/28.
 */

public class RegisterBean implements Serializable {


    /**
     * result : 1000
     * message : 注册成功
     * data : {"token":"a98523560ee211596074dd2b7480e078de8e20ab"}
     */

    private int result;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : a98523560ee211596074dd2b7480e078de8e20ab
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
