package com.gl.education.home.event;

/**
 * Created by zy on 2018/7/2.
 * 支付完成，将相关的结果发送给对应界面，通知js
 */

public class PayFinishEvent {
    private String message;
    private int result;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public PayFinishEvent(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
