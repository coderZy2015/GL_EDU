package com.gl.education.login.presenter;

import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.model.ForgetPasswordBean;
import com.gl.education.login.model.IdentifyBean;
import com.gl.education.login.view.ForgetPasswordView;
import com.lzy.okgo.model.Response;

/**
 * Created by zy on 2018/6/28.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {

    //发送验证码
    public void getIdentifyCode(String username, String func){
        HomeAPI.sendIdentifyCode(username, func, new JsonCallback<IdentifyBean>() {
            @Override
            public void onSuccess(Response<IdentifyBean> response) {
                IdentifyBean responseData = response.body();
                if (responseData.getResult() == 1000){
                    getView().sendCode(responseData);
                }else{
                    getView().sendCodeError(responseData.getMessage());
                }
            }
        });
    }

    //忘记密码
    public void toSend(String username, String checkcode, String password1, String password2){
        HomeAPI.repeatPassword(username, checkcode, password1, password2, new JsonCallback<ForgetPasswordBean>() {
            @Override
            public void onSuccess(Response<ForgetPasswordBean> response) {
                ForgetPasswordBean responseData = response.body();

                if (responseData.getResult() == 1000){
                    getView().reviseSuccess(responseData);
                }else{
                    getView().reviseFail(responseData.getMessage());
                }
            }
        });
    }
}
