package com.gl.education.composition.event;

import com.gl.education.composition.model.SetCompositionTitleBean;

/**
 * Created by zy on 2018/10/21.
 */

public class JSsetCompositionTitleEvent {
    SetCompositionTitleBean bean;

    public SetCompositionTitleBean getBean() {
        return bean;
    }

    public void setBean(SetCompositionTitleBean bean) {
        this.bean = bean;
    }
}
