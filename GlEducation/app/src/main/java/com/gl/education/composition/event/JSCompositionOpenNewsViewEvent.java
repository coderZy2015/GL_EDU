package com.gl.education.composition.event;

import com.gl.education.home.model.JSRecommentBean;

/**
 * Created by zy on 2018/10/20.
 */

public class JSCompositionOpenNewsViewEvent {
    JSRecommentBean bean;

    public JSRecommentBean getBean() {
        return bean;
    }

    public void setBean(JSRecommentBean bean) {
        this.bean = bean;
    }
}
