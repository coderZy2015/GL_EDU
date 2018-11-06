package com.gl.education.home.event;

import com.gl.education.home.model.JSAppGetRecommendData;

/**
 * Created by zy on 2018/8/25.
 */

public class JSRecommentContentGetDataEvent {
    JSAppGetRecommendData bean;

    public JSAppGetRecommendData getBean() {
        return bean;
    }

    public void setBean(JSAppGetRecommendData bean) {
        this.bean = bean;
    }
}
