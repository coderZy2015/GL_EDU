package com.gl.education.evaluation.event;

import com.gl.education.evaluation.model.JSGetLessonDataBean;

/**
 * Created by zy on 2018/10/16.
 */

public class JSGetLessonDataEvent {
    JSGetLessonDataBean bean;

    public JSGetLessonDataBean getBean() {
        return bean;
    }

    public void setBean(JSGetLessonDataBean bean) {
        this.bean = bean;
    }
}
