package com.gl.education.home.view;

import com.gl.education.home.model.ApplyTokenBean;

/**
 * Created by zy on 2018/7/20.
 */

public interface GuideView {
    void getTokenSuccess(ApplyTokenBean bean);
    void getTokenError();
}
