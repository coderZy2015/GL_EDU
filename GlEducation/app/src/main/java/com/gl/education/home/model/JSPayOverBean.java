package com.gl.education.home.model;

/**
 * Created by zy on 2018/7/5.
 * 通知Js支付结果
 */

public class JSPayOverBean {

    String orderId;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
