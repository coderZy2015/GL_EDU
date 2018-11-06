package com.gl.education.composition.event;

import com.gl.education.composition.model.EnterSubColumnBean;

/**
 * Created by zy on 2018/10/18.
 */

public class JSEnterSubColumnEvent {
    EnterSubColumnBean bean;

    public EnterSubColumnBean getBean() {
        return bean;
    }

    public void setBean(EnterSubColumnBean bean) {
        this.bean = bean;
    }
}
