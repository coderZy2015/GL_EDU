package com.gl.education.home.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.app.AppCommonData;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * user：lqm
 * desc：BaseFragment
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends SupportFragment
{

    protected T mPresenter;
    public String eventTAG = "";//当前类 event的标识 用于区分event发送的类
    public String fragType = "";//当前类 event的标识 用于区分event发送的类
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(provideContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        //拿到布局填充器返回的view后
        ScreenAdapterTools.getInstance().loadView(rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initData();
    }

    @Override
    public void onLazyInitView(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setEventTAG();
    }

    @Override
    public void onResume() {
        super.onResume();
        setEventTAG();
    }

    public void initFragTag(String tag){
        fragType = tag;
    }

    public void setEventTAG(){
        if (setIdentifier() != null){
            eventTAG = setIdentifier();
            AppCommonData.CURRENT_TAG = eventTAG;
            LogUtils.d("CURRENT_TAG = "+eventTAG);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void init() {

    }

    public void initView(View rootView) {
    }

    public void clickBackBtn(){
        _mActivity.onBackPressed();
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //设置当前类event事件的标识  不需要传null即可
    protected abstract String setIdentifier();
}
