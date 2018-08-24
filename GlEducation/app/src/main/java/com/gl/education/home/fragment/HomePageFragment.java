package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.home.activity.ChannelActivity;
import com.gl.education.home.activity.SearchActivity;
import com.gl.education.home.adapter.FragPagerAdapter;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.event.UpdateChannelEvent;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.presenter.HomePagePresenter;
import com.gl.education.home.view.HomePageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/6.
 */

public class HomePageFragment extends BaseFragment<HomePageView, HomePagePresenter> implements
        HomePageView{

    @BindView(R.id.tabLayout)
    TabLayout mTab;

    @BindView(R.id.vp_pager)
    ViewPager viewPager;

    @BindView(R.id.home_add_channel)
    RelativeLayout btn_add;

    @BindView(R.id.btn_search)
    TextView btn_search;

    /**
     * 记录频道排序
     */
    public List<ChannelEntity> mFragShowIdList = new ArrayList<>();

    private FragPagerAdapter adapter;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    protected HomePagePresenter createPresenter() {
        return new HomePagePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_home_page;
    }

    @Override
    protected String setIdentifier() {
        return "HomePageFragment";
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        //后台登陆
        if (!AppCommonData.loginBackground){
            //mPresenter.autoLogin();
            mPresenter.getUserChannelGrade();
        }else{
            mPresenter.getUserChannelGrade();
        }

        adapter = new FragPagerAdapter(getChildFragmentManager(), mFragShowIdList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void init() {
        super.init();
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @OnClick(R.id.home_add_channel)
    public void onClickAdd(){
        Intent intent = new Intent();
        intent.setClass(_mActivity, ChannelActivity.class);
        intent.putExtra("from", ChannelActivity.FROM_MAIN);
        intent.putExtra("position",viewPager.getCurrentItem());
        intent.putExtra("title","");
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_search)
    public void intoSearchView(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), SearchActivity.class);
        startActivity(intent);
    }


    //更新频道信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateChannelData(UpdateChannelEvent event) {

        mFragShowIdList.clear();
        mFragShowIdList.addAll(event.getChannelList());
        adapter.notifyDataSetChanged();

        if (event.getSelectChannel() != -1){
            mTab.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTab.getTabAt(event.getSelectChannel()).select();
                }
            }, 100);
        }

    }

    @Override
    public void getChannelGradeSuccess(int grade, List<ChannelEntity> _mFragShowIdList) {
        AppCommonData.userGrade = grade;
        mFragShowIdList.clear();
        mFragShowIdList.addAll(_mFragShowIdList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            int postion = data.getIntExtra("tabId", -1);
        }
        LogUtils.d("requestCode = "+requestCode+"  resultCode = "+resultCode);
    }

}
