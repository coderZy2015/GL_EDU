package com.gl.education.composition.event;

import com.gl.education.composition.model.OpenArticleBean;

/**
 * Created by zy on 2018/10/19.
 */

public class JSopenArticleEvent {
    OpenArticleBean bean;

    public OpenArticleBean getBean() {
        return bean;
    }

    public void setBean(OpenArticleBean bean) {
        this.bean = bean;
    }
}
