package com.gl.education.home.model;

/**
 * Created by zy on 2018/7/2.
 * 获取订单系统的订单
 */

public class JSPayInfoBean {
    private String uid;
    private String orderType;
    private String price;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
