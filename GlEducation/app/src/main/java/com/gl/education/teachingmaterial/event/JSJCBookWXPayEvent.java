package com.gl.education.teachingmaterial.event;

import com.gl.education.home.model.JSPayInfoBean;

/**
 * Created by zy on 2018/8/22.
 */

public class JSJCBookWXPayEvent {
    private JSPayInfoBean bean;

    public JSPayInfoBean getBean() {
        return bean;
    }

    public void setBean(JSPayInfoBean bean) {
        this.bean = bean;
    }
}
