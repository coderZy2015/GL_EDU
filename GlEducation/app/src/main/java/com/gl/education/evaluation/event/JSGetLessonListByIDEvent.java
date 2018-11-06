package com.gl.education.evaluation.event;

import com.gl.education.evaluation.model.JSGetLessonListByIdBean;

/**
 * Created by zy on 2018/10/15.
 */

public class JSGetLessonListByIDEvent {
    JSGetLessonListByIdBean bean;

    public JSGetLessonListByIdBean getBean() {
        return bean;
    }

    public void setBean(JSGetLessonListByIdBean bean) {
        this.bean = bean;
    }
}
