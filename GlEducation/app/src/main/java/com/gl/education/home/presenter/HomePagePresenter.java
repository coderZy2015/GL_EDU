package com.gl.education.home.presenter;

import android.support.design.widget.TabLayout;

import com.gl.education.api.HomeAPI;
import com.gl.education.helper.rxjavahelper.RxObserver;
import com.gl.education.helper.rxjavahelper.RxResultHelper;
import com.gl.education.helper.rxjavahelper.RxSchedulersHelper;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.TypeTagVO;
import com.gl.education.home.view.HomePageView;

import java.util.List;

/**
 * Created by zy on 2018/6/6.
 */

public class HomePagePresenter extends BasePresenter<HomePageView>{

    private HomePageView mTypeView;
    private List<TypeTagVO> mTagDatas;

    //分类标签
    public void getTagData() {
        mTypeView = getView();
        HomeAPI.getTypeTagData()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<TypeTagVO>>() {
                    @Override
                    public void _onNext(List<TypeTagVO> typeTagVOS) {
                        mTagDatas = typeTagVOS;
                        setTabUI();
//                        mTabSelect = 0;
//                        mTagSelect = 0;
//                        getServerData(mTagDatas.get(0).getChildren().get(0).getId());
                    }

                    @Override
                    public void _onError(String errorMessage) {

                    }
                });
    }

    //一级Tab
    private void setTabUI() {
        TabLayout tabLayout = mTypeView.getTabLayout();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (TypeTagVO bean : mTagDatas) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()));
        }
    }

}
