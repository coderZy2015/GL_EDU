package com.gl.education.composition.event;

import com.gl.education.composition.model.ToUploadComposiseBean;

/**
 * Created by zy on 2018/10/18.
 */

public class JSToUploadComposiseEvent {
    ToUploadComposiseBean bean;

    public ToUploadComposiseBean getBean() {
        return bean;
    }

    public void setBean(ToUploadComposiseBean bean) {
        this.bean = bean;
    }
}
