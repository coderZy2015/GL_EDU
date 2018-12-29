package com.gl.education.home.presenter;

import android.content.Context;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.ApplyTokenBean;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.GetAllChannelBean;
import com.gl.education.home.model.GetChannelFlagBean;
import com.gl.education.home.model.GetUserChannelGradeBean;
import com.gl.education.home.model.GetVersionBean;
import com.gl.education.home.model.SetUserChannelGradeBean;
import com.gl.education.home.model.setFlagBean;
import com.gl.education.home.view.HomePageView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by zy on 2018/6/6.
 */

public class HomePagePresenter extends BasePresenter<HomePageView> {

    private int grade = 1;

    private List<ChannelEntity> allList;
    private List<ChannelEntity> myList;

    int cn_rw = 0;
    int cn_wspk = 0;
    int cn_tsg = 0;

    public void autoLogin() {
        //token存在  添加公共header  自动登录
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        if (!token.equals("")) {
            HttpHeaders headers = new HttpHeaders();
            headers.put("GL-Token", token);
            OkGo.getInstance().addCommonHeaders(headers);

            HomeAPI.loginAuth(new JsonCallback<ApplyTokenBean>() {
                @Override
                public void onSuccess(Response<ApplyTokenBean> response) {
                    if (response.body().getResult() == 1000) {
                        String token = response.body().getData().getToken();
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
                        OkGo.getInstance().getCommonHeaders().put("GL-Token", token);
                        AppCommonData.loginBackground = true;

                        getUserChannelGrade();
                    } else {
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, "");
                        AppCommonData.loginBackground = false;
                        getView().getChannelGradeFail(response.body().getResult(), response.body().getMessage());
                    }
                }

                @Override
                public void onError(Response<ApplyTokenBean> response) {
                    super.onError(response);
                    ToastUtils.showShort("失败");
                    getView().getChannelGradeNetWorkError();
                }
            });
        }
    }

    //获取用户频道列表
    public void getUserChannelGrade() {

        HomeAPI.getUserChannelGrade(new JsonCallback<GetUserChannelGradeBean>() {
            @Override
            public void onSuccess(Response<GetUserChannelGradeBean> response) {
                if (response.body().getResult() == 1000) {
                    grade = response.body().getData().getGrade();
                    getView().getChannelGradeSuccess(grade, response.body().getData()
                            .getChannel_data());
                } else {
                    getView().getChannelGradeFail(response.body().getResult(), response.body().getMessage());
                }

            }

            @Override
            public void onError(Response<GetUserChannelGradeBean> response) {
                super.onError(response);
                getView().getChannelGradeNetWorkError();
            }
        });
    }

    //检测是否有新频道
    public void checkChannelVersion() {
        HomeAPI.getChannelFlag(new JsonCallback<GetChannelFlagBean>() {
            @Override
            public void onSuccess(Response<GetChannelFlagBean> response) {
                if (response.body().getResult() == 1000) {
                    GetChannelFlagBean bean = response.body();
                    cn_rw = bean.getData().getCn_rw();
                    cn_wspk = bean.getData().getCn_wspk();
                    cn_tsg = bean.getData().getCn_tsg();
                    LogUtils.d("cn_rw = "+cn_rw+"cn_wspk = "+cn_wspk+"cn_tsg = "+cn_tsg);
                    if (cn_rw == 0 || cn_wspk == 0 || cn_tsg == 0) {
                        LogUtils.d("准备自动新增频道");
                        //请求全部频道列表
                        HomeAPI.getAllChannel("302", new JsonCallback<GetAllChannelBean>() {
                            @Override
                            public void onSuccess(Response<GetAllChannelBean> response) {
                                if (response.body().getResult() == 1000) {
                                    allList = response.body().getData();
                                    //请求我的频道列表
                                    HomeAPI.getUserChannelGrade(new JsonCallback<GetUserChannelGradeBean>() {
                                        @Override
                                        public void onSuccess(Response<GetUserChannelGradeBean>
                                                                      response) {
                                            if (response.body().getResult() == 1000) {
                                                grade = response.body().getData().getGrade();
                                                myList = response.body().getData()
                                                        .getChannel_data();
                                                addNewChannel();
                                            }
                                        }

                                        @Override
                                        public void onError(Response<GetUserChannelGradeBean>
                                                                    response) {
                                            super.onError(response);
                                            response.body().getResult();
                                            getUserChannelGrade();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onError(Response<GetAllChannelBean> response) {
                                super.onError(response);
                                getUserChannelGrade();
                            }
                        });

                    } else {
                        //无新频道更新
                        getUserChannelGrade();
                    }
                }else {
                    getView().getChannelGradeFail(response.body().getResult(), response.body().getMessage());
                }
            }

            @Override
            public void onError(Response<GetChannelFlagBean> response) {
                super.onError(response);
                getView().getChannelGradeNetWorkError();
            }
        });
    }

    public void addNewChannel() {
        if (cn_rw == 0) {
            cn_rw = 1;
            for (int i = 0; i < allList.size(); i++) {
                if (allList.get(i).getId() == 5) {
                    boolean isAdd = true;
                    //检测是否已经添加阅读与写作
                    for (int j = 0; j < myList.size(); j++) {
                        if (myList.get(j).getId() == 5) {
                            LogUtils.d("已经添加阅读与写作");
                            isAdd = false;
                        }
                    }
                    if (isAdd) {
                        myList.add(allList.get(i));
                    }

                }
            }
        }

        if (cn_wspk == 0) {
            cn_wspk = 1;
            for (int i = 0; i < allList.size(); i++) {
                if (allList.get(i).getId() == 6) {
                    boolean isAdd = true;
                    //检测是否已经添加网上评课频道
                    for (int j = 0; j < myList.size(); j++) {
                        if (myList.get(j).getId() == 6) {
                            isAdd = false;
                            LogUtils.d("已经添加网上评课");
                        }
                    }
                    if (isAdd) {
                        myList.add(allList.get(i));
                    }
                }
            }
        }

        if (cn_tsg == 0) {
            cn_tsg = 1;
            for (int i = 0; i < allList.size(); i++) {
                if (allList.get(i).getId() == 7) {
                    boolean isAdd = true;
                    //检测是否已经添加传统文化
                    for (int j = 0; j < myList.size(); j++) {
                        if (myList.get(j).getId() == 7) {
                            LogUtils.d("已经添加传统文化");
                            isAdd = false;
                        }
                    }
                    if (isAdd) {
                        myList.add(allList.get(i));
                    }

                }
            }
        }


        Gson gson = new Gson();
        String json = gson.toJson(myList);
        String str = new String(EncodeUtils.base64Encode(json));
        //点击确定  设定用户的年级、频道
        HomeAPI.setUserChannelGrade(grade, str, new JsonCallback<SetUserChannelGradeBean>() {
            @Override
            public void onSuccess(Response<SetUserChannelGradeBean> response) {
                if (response.body().getResult() == 1000) {
                    getView().getChannelGradeSuccess(grade, myList);
                }
            }

            @Override
            public void onError(Response<SetUserChannelGradeBean> response) {
                super.onError(response);
                ToastUtils.showShort("网络连接失败，请检查网络");
            }
        });

        HomeAPI.setChannelFlag(cn_rw, cn_wspk, cn_tsg, new JsonCallback<setFlagBean>() {
            @Override
            public void onSuccess(Response<setFlagBean> response) {
            }
        });

    }


    public void checkVersion(Context context) {
        HomeAPI.getCRTVersion(new JsonCallback<GetVersionBean>() {
            @Override
            public void onSuccess(Response<GetVersionBean> response) {
                if (response.body().getResult() == 1000){
                    String code = response.body().getData().getVersion();
                    int cc = 0;
                    try {
                        cc = Integer.valueOf(code);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (AppCommonData.versionCode < cc) {
                        LogUtils.d("versionCode = " + cc);
                        getView().updateApp();
                    }
                }

            }
        });
    }
}
