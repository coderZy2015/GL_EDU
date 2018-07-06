package com.gl.education.login.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/6/28.
 */

public class RegisterBean implements Serializable {

    /**
     * result : 1000
     * message : 注册成功
     * data : {"userid":44096,"guid":"94da8318-79dc-11e8-80a4-7200073c8a50"}
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
         * userid : 44096
         * guid : 94da8318-79dc-11e8-80a4-7200073c8a50
         */

        private int userid;
        private String guid;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
    }
}
