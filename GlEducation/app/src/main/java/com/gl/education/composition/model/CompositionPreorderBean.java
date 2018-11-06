package com.gl.education.composition.model;

/**
 * Created by zy on 2018/10/21.
 */

public class CompositionPreorderBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"rw_orderid":"1534925608713"}
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
         * rw_orderid : 1534925608713
         */

        private String rw_orderid;

        public String getRw_orderid() {
            return rw_orderid;
        }

        public void setRw_orderid(String rw_orderid) {
            this.rw_orderid = rw_orderid;
        }
    }
}
