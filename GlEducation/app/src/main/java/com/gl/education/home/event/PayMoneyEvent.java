package com.gl.education.home.event;

import com.gl.education.home.model.JSPayInfoBean;

/**
 * Created by zy on 2018/7/2.
 * H5点击购买后，将收到的结果发送给对应界面，调起支付
 */

public class PayMoneyEvent {

    private String message;
    private String type;
    private JSPayInfoBean bean;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSPayInfoBean getBean() {
        return bean;
    }

    public void setBean(JSPayInfoBean bean) {
        this.bean = bean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
