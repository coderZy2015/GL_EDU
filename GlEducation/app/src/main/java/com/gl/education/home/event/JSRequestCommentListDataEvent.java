package com.gl.education.home.event;

import com.gl.education.home.model.JSAppGetRecommendData;

/**
 * Created by zy on 2019/1/22.
 */
public class JSRequestCommentListDataEvent {
    JSAppGetRecommendData bean;

    public JSAppGetRecommendData getBean() {
        return bean;
    }

    public void setBean(JSAppGetRecommendData bean) {
        this.bean = bean;
    }
}
