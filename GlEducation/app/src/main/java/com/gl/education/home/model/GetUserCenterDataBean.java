package com.gl.education.home.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/8/18.
 */

public class GetUserCenterDataBean implements Serializable {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"phone":"15833113069","username":"15833113069","grade":"0","nick_name":null,
     * "xb":null,"avatar":"clientAvatar/\\default.png","jiaocai":10,"jiaofu":8,"weike":6,"zuowen":5}
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
         * phone : 15833113069
         * username : 15833113069
         * grade : 0
         * nick_name : null
         * xb : null
         * avatar : clientAvatar/\default.png
         * jiaocai : 10
         * jiaofu : 8
         * weike : 6
         * zuowen : 5
         */

        private String phone;
        private String username;
        private String grade;
        private String nick_name;
        private String xb;
        private String avatar;
        private int jiaocai;
        private int jiaofu;
        private int weike;
        private int zuowen;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getJiaocai() {
            return jiaocai;
        }

        public void setJiaocai(int jiaocai) {
            this.jiaocai = jiaocai;
        }

        public int getJiaofu() {
            return jiaofu;
        }

        public void setJiaofu(int jiaofu) {
            this.jiaofu = jiaofu;
        }

        public int getWeike() {
            return weike;
        }

        public void setWeike(int weike) {
            this.weike = weike;
        }

        public int getZuowen() {
            return zuowen;
        }

        public void setZuowen(int zuowen) {
            this.zuowen = zuowen;
        }
    }
}
