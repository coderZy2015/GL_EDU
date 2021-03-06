package com.gl.education.login.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.model.IdentifyBean;
import com.gl.education.login.model.RegisterBean;
import com.gl.education.login.view.RegisterView;
import com.lzy.okgo.model.Response;

/**
 * Created by zy on 2018/6/28.
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

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

            @Override
            public void onError(Response<IdentifyBean> response) {
                super.onError(response);
                IdentifyBean responseData = response.body();
                getView().sendCodeError(responseData.getMessage());
            }
        });
    }
    
    public void toRegister(String username, String password, String identifyCode){

        HomeAPI.register(username, password, identifyCode, new JsonCallback<RegisterBean>() {
            @Override
            public void onSuccess(Response<RegisterBean> response) {
                RegisterBean responseData = response.body();

                if (responseData.getResult() == 1000){
                    getView().registerSuccess(responseData);
                }else{
                    getView().registerFail(responseData.getMessage());
                }
            }
        });
    }

    public void toWXRegister(String username, String password, String identifyCode, String unid){
        HomeAPI.registWechat(username, password, identifyCode, unid, new JsonCallback<RegisterBean>() {
            @Override
            public void onSuccess(Response<RegisterBean> response) {
                RegisterBean bean = response.body();

                if (bean.getResult() == 1000){
                    getView().registerWXSuccess(bean);
                }else{
                    if (bean.getMessage() != null){
                        getView().registerWXFail(bean.getMessage());
                    }else{
                        ToastUtils.showShort("注册失败");
                    }


                }
            }
        });
    }

}