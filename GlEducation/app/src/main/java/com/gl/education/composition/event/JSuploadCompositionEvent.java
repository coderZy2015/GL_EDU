package com.gl.education.composition.event;

import com.gl.education.composition.model.UploadCompositionBean;

/**
 * Created by zy on 2018/10/19.
 */

public class JSuploadCompositionEvent {
    UploadCompositionBean bean;

    public UploadCompositionBean getBean() {
        return bean;
    }

    public void setBean(UploadCompositionBean bean) {
        this.bean = bean;
    }
}
