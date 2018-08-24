package com.gl.education.login.presenter;

import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.model.LoginBean;
import com.gl.education.login.view.LoginView;
import com.lzy.okgo.model.Response;

/**
 * Created by zy on 2018/6/26.
 */

public class LoginPresenter extends BasePresenter<LoginView>{
    
    public void toLogin(String username, String password){
        HomeAPI.login(username, password, new JsonCallback<LoginBean>() {
            @Override
            public void onSuccess(Response<LoginBean> response) {
                LoginBean responseData = response.body();

                if (responseData == null)
                    return;

                if (responseData.getResult() == 1000){
                    getView().loginSuccess(responseData);
                }else{
                    getView().loginFail(responseData.getMessage());
                }

            }
        });
    }
}
