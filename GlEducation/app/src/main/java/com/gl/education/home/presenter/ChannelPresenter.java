package com.gl.education.home.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.GetAllChannelBean;
import com.gl.education.home.model.GetUserChannelGradeBean;
import com.gl.education.home.view.ChannelView;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/7/26.
 */

public class ChannelPresenter extends BasePresenter<ChannelView> {

    public List<ChannelEntity> allChannelList = new ArrayList<>();
    public List<ChannelEntity> myChannelList = new ArrayList<>();
    public List<ChannelEntity> otherChannelList = new ArrayList<>();

    private int from;

    public void getAllChannel(int _from) {
        from = _from;
        //请求全部频道列表
        HomeAPI.getAllChannel("25", new JsonCallback<GetAllChannelBean>() {
            @Override
            public void onSuccess(Response<GetAllChannelBean> response) {
                if (response.body() == null) {
                    LogUtils.d("请求出错!");
                    return;
                }
                if (response.body().getResult() == 1000) {
                    allChannelList = response.body().getData();

//                    //暂时移除作文频道
//                    if (!AppCommonData.isShowComposition){
//                        hideCompositionChannel();
//                    }

                    //请求我的频道列表
                    HomeAPI.getUserChannelGrade(callback);

                } else {
                    otherChannelList = allChannelList;
                    getView().getChannelDataErroer(otherChannelList);
                }
            }

            @Override
            public void onError(Response<GetAllChannelBean> response) {
                super.onError(response);
                otherChannelList = allChannelList;
                getView().getChannelDataErroer(otherChannelList);
            }
        });
    }

    JsonCallback<GetUserChannelGradeBean> callback = new JsonCallback<GetUserChannelGradeBean>() {
        @Override
        public void onSuccess(Response<GetUserChannelGradeBean> response) {
            if (response.body().getResult() == 1000) {
                AppCommonData.userGrade = response.body().getData().getGrade();
                myChannelList = response.body().getData().getChannel_data();

                boolean firstEnter = SPUtils.getInstance().getBoolean(AppConstant.SP_FIRST_ENTER, true);
                if (firstEnter){
                    initData();
                }

                removeRepeatView();//从全部频道中去除我的频道
                getView().getChannelDataSuccess(myChannelList, otherChannelList);
            }else{
                otherChannelList = allChannelList;
                getView().getChannelDataErroer(otherChannelList);
                ToastUtils.showShort("获取频道失败");
            }
        }

        @Override
        public void onError(Response<GetUserChannelGradeBean> response) {
            super.onError(response);
            otherChannelList = allChannelList;
            getView().getChannelDataErroer(otherChannelList);
        }
    };

    //第一次进入默认添加全部频道
    public void initData(){
        myChannelList.clear();
         for (int i=0; i<allChannelList.size(); i++){
             if (allChannelList.get(i).getId() <= 6){
                 myChannelList.add( allChannelList.get(i));
             }
          }
        SPUtils.getInstance().put(AppConstant.SP_FIRST_ENTER, false);
    }


    public void removeRepeatView() {

        for (ChannelEntity entity : allChannelList) {
            boolean isHave = false;
            for (ChannelEntity oEntity : myChannelList) {
                if (oEntity.getName().equals(entity.getName())) {
                    isHave = true;
                }
            }
            if (!isHave){
                otherChannelList.add(entity);
            }
        }
    }

    public void hideCompositionChannel(){
        ChannelEntity entity = null;
        for (int i=0; i<allChannelList.size(); i++){
            if (allChannelList.get(i).getName().equals("阅读与写作")){
                entity = allChannelList.get(i);
            }
        }

        if (entity != null){
            allChannelList.remove(entity);
            LogUtils.d("已经移除作文频道");
        }
    }

}
