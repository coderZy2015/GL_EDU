package com.gl.education.home.event;

import com.gl.education.home.model.JSOpenWebViewBean;

/**
 * Created by zy on 2018/7/4.
 */

public class OpenWebViewEvent {

    String message;

    String fragType;//fragment里的具体区分

    JSOpenWebViewBean bean;

    public String getFragType() {
        return fragType;
    }

    public void setFragType(String fragType) {
        this.fragType = fragType;
    }

    public JSOpenWebViewBean getBean() {
        return bean;
    }

    public void setBean(JSOpenWebViewBean bean) {
        this.bean = bean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
