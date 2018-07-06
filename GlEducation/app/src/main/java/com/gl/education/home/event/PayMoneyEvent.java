package com.gl.education.home.event;

import com.gl.education.home.model.JSPayInfoBean;

/**
 * Created by zy on 2018/7/2.
 * H5点击购买后，将收到的结果发送给对应界面，调起支付
 */

public class PayMoneyEvent {

    private String message;
    private JSPayInfoBean bean;

    public JSPayInfoBean getBean() {
        return bean;
    }

    public void setBean(JSPayInfoBean bean) {
        this.bean = bean;
    }

    public PayMoneyEvent(String message, JSPayInfoBean _bean){
        this.message = message;
        this.bean = _bean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
