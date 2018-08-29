package com.gl.education.home.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.ApplyTokenBean;
import com.gl.education.home.model.GetUserChannelGradeBean;
import com.gl.education.home.view.HomePageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

/**
 * Created by zy on 2018/6/6.
 */

public class HomePagePresenter extends BasePresenter<HomePageView> {
    private int grade = 0;

    public void autoLogin(){
        //token存在  添加公共header  自动登录
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        if (!token.equals("")){
            HttpHeaders headers = new HttpHeaders();
            headers.put("GL-Token", token);
            OkGo.getInstance().addCommonHeaders(headers);

            HomeAPI.loginAuth(new JsonCallback<ApplyTokenBean>() {
                @Override
                public void onSuccess(Response<ApplyTokenBean> response) {
                    if (response.body().getResult() == 1000){
                        String token = response.body().getData().getToken();
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
                        OkGo.getInstance().getCommonHeaders().put("GL-Token", token);
                        AppCommonData.loginBackground = true;

                        getUserChannelGrade();
                    }else{
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, "");
                        AppCommonData.loginBackground = false;

                    }
                }

                @Override
                public void onError(Response<ApplyTokenBean> response) {
                    super.onError(response);
                    ToastUtils.showShort("失败");
                }
            });
        }
    }

    public void getUserChannelGrade() {

        HomeAPI.getUserChannelGrade(new JsonCallback<GetUserChannelGradeBean>() {
            @Override
            public void onSuccess(Response<GetUserChannelGradeBean> response) {
                if (response.body().getResult() == 1000) {
                    grade = response.body().getData().getGrade();
                    getView().getChannelGradeSuccess(grade, response.body().getData().getChannel_data());
                }

            }

            @Override
            public void onError(Response<GetUserChannelGradeBean> response) {
                super.onError(response);
            }
        });
    }
}
