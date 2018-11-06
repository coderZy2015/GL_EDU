package com.gl.education.login.view;

import com.gl.education.login.model.ForgetPasswordBean;
import com.gl.education.login.model.IdentifyBean;

/**
 * Created by zy on 2018/6/28.
 */

public interface ForgetPasswordView {

    void reviseSuccess(ForgetPasswordBean bean);
    void reviseFail(String msg);

    void sendCode(IdentifyBean bean);

    void sendCodeError(String msg);
}
