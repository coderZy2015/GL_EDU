package com.gl.education.home.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/7/17.
 */

public class ApplyTokenBean implements Serializable {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"token":"738ab35cb8432f6437b22ef112ff15cc160ed7bf"}
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
         * token : 738ab35cb8432f6437b22ef112ff15cc160ed7bf
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
