package com.gl.education.login.view;

import com.gl.education.login.model.LoginBean;

/**
 * Created by zy on 2018/6/26.
 */

public interface LoginView {

    void loginSuccess(LoginBean bean);

    void loginFail(String msg);

}
