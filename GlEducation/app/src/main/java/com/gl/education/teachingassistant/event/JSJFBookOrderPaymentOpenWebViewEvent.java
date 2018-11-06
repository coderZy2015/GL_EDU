package com.gl.education.teachingassistant.event;

import com.gl.education.home.model.JSOpenWebViewBean;

/**
 * Created by zy on 2018/8/21.
 */

public class JSJFBookOrderPaymentOpenWebViewEvent {
    JSOpenWebViewBean bean;

    public JSOpenWebViewBean getBean() {
        return bean;
    }

    public void setBean(JSOpenWebViewBean bean) {
        this.bean = bean;
    }
}
