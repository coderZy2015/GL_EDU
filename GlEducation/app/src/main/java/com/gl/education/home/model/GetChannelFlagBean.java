package com.gl.education.home.model;

/**
 * Created by zy on 2018/10/18.
 */

public class GetChannelFlagBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"cn_rw":0,"cn_wspk":0}
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
         * cn_rw : 0
         * cn_wspk : 0
         */

        private int cn_rw;
        private int cn_wspk;

        public int getCn_rw() {
            return cn_rw;
        }

        public void setCn_rw(int cn_rw) {
            this.cn_rw = cn_rw;
        }

        public int getCn_wspk() {
            return cn_wspk;
        }

        public void setCn_wspk(int cn_wspk) {
            this.cn_wspk = cn_wspk;
        }
    }
}
