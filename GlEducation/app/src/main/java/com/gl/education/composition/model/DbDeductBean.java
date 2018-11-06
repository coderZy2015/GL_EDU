package com.gl.education.composition.model;

/**
 * Created by zy on 2018/10/22.
 */

public class DbDeductBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"info":"扣除代币成功","code":1}
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
         * info : 扣除代币成功
         * code : 1
         */

        private String info;
        private int code;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
