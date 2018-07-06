package com.gl.education.home.presenter;


import com.gl.education.api.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.helper.LzyResponse;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.UserBean;
import com.gl.education.home.view.LoginRegistView;
import com.lzy.okgo.model.Response;

/**
 * user：lqm
 * desc：登录注册
 */

public class LoginRegistPresenter extends BasePresenter<LoginRegistView> {

    //登录
    public void toLogin(String username, String password) {

        HomeAPI.login(username, password, new JsonCallback<LzyResponse<UserBean>>() {

            @Override
            public void onSuccess(Response<LzyResponse<UserBean>> response) {
                LzyResponse<UserBean> lzyResponse = response.body();

                getView().loginSuccess(lzyResponse.getData());
            }

            @Override
            public void onError(Response<LzyResponse<UserBean>> response) {
                super.onError(response);
            }
        });

    }

}
