package com.gl.education.login.view;

import com.gl.education.login.model.IdentifyBean;
import com.gl.education.login.model.RegisterBean;

/**
 * Created by zy on 2018/6/28.
 */

public interface RegisterView {

    void registerSuccess(RegisterBean bean);

    void registerFail(String msg);

    void sendCode(IdentifyBean bean);

    void sendCodeError(String msg);

    void registerWXSuccess(RegisterBean bean);

    void registerWXFail(String msg);
}
