package com.gl.education.home.model;

/**
 * Created by zy on 2018/8/24.
 */

public class GetDbCountBean {


    /**
     * result : 1000
     * message : 操作成功
     * data : {"dbcount":0.01}
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
         * dbcount : 0.01
         */

        private double dbcount;

        public double getDbcount() {
            return dbcount;
        }

        public void setDbcount(double dbcount) {
            this.dbcount = dbcount;
        }
    }
}
