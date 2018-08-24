package com.gl.education.home.presenter;

import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.ApplyTokenBean;
import com.gl.education.home.view.GuideView;
import com.lzy.okgo.model.Response;

/**
 * Created by zy on 2018/7/20.
 */

public class GuidePresenter extends BasePresenter<GuideView>{

    public void applayToken(String deviceId){
        HomeAPI.applyToken(deviceId, new JsonCallback<ApplyTokenBean>() {
            @Override
            public void onSuccess(Response<ApplyTokenBean> response) {
                getView().getTokenSuccess( response.body());
            }

            @Override
            public void onError(Response<ApplyTokenBean> response) {
                super.onError(response);
                getView().getTokenError();
            }

        });
    }
}
