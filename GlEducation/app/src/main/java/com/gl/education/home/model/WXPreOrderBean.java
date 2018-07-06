package com.gl.education.home.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/7/3.
 */

public class WXPreOrderBean implements Serializable {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"orderid":"20180703150143cda2ab48a9da6c30e9",
     * "orderStr":{"appid":"wxc3d63044c63e0b27","partnerid":"1508427681",
     * "prepayid":"wx0315014405653646618d1a442183442398","package":"Sign=WXPay",
     * "noncestr":"LvktNfYfuXAQEtARV3TyI3208h3IYJkI","timestamp":1530601304,
     * "sign":"D3DB520618804F4A5EEF7CA807C0B238"}}
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

        private String orderid;
        private OrderStrBean orderStr;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public OrderStrBean getOrderStr() {
            return orderStr;
        }

        public void setOrderStr(OrderStrBean orderStr) {
            this.orderStr = orderStr;
        }

        public static class OrderStrBean {

            private String appid;
            private String partnerid;
            private String prepayid;
//            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
