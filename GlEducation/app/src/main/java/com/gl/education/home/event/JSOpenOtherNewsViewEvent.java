package com.gl.education.home.event;

import com.gl.education.home.model.JSRecommentBean;

/**
 * Created by zy on 2018/10/26.
 */

public class JSOpenOtherNewsViewEvent {
    JSRecommentBean bean;

    public JSRecommentBean getBean() {
        return bean;
    }

    public void setBean(JSRecommentBean bean) {
        this.bean = bean;
    }
}
