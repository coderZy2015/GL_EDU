package com.gl.education.evaluation.event;

import com.gl.education.evaluation.model.GetActivityBean;

/**
 * Created by zy on 2018/10/17.
 */

public class SendAllActivityDataEvent {
    GetActivityBean bean;

    public GetActivityBean getBean() {
        return bean;
    }

    public void setBean(GetActivityBean bean) {
        this.bean = bean;
    }
}
