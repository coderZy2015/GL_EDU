package com.gl.education.composition.event;

import com.gl.education.composition.model.OpenTeacherDetailBean;

/**
 * Created by zy on 2018/10/19.
 */

public class JSopenTeacherDetailEvent {
    OpenTeacherDetailBean bean;

    public OpenTeacherDetailBean getBean() {
        return bean;
    }

    public void setBean(OpenTeacherDetailBean bean) {
        this.bean = bean;
    }
}
