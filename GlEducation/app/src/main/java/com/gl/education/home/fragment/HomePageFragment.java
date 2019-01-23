package com.gl.education.home.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.ChannelActivity;
import com.gl.education.home.activity.HomePageActivity;
import com.gl.education.home.activity.SearchActivity;
import com.gl.education.home.activity.WEShopActivity;
import com.gl.education.home.adapter.FragPagerAdapter;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.event.OpenJCChannelEvent;
import com.gl.education.home.event.OpenJFChannelEvent;
import com.gl.education.home.event.ReloadChannelEvent;
import com.gl.education.home.event.RequestNetWorkAgainEvent;
import com.gl.education.home.event.UpdateChannelEvent;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.TokenIdentifyBean;
import com.gl.education.home.presenter.HomePagePresenter;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.home.view.HomePageView;
import com.gl.education.login.LoginInfoActivity;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

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

    @BindView(R.id.home_notify)
    RelativeLayout home_notify;

    /**
     * 记录频道排序
     */
    public List<ChannelEntity> mFragShowIdList = new ArrayList<>();

    private FragPagerAdapter adapter;

    private Loading_view loading;

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
    public void initView(View rootView) {
        super.initView(rootView);
        loading = new Loading_view(getActivity(), com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //获取app的版本号
        getVersionCode();
        loading.show();
        //后台登陆
        //mPresenter.getUserChannelGrade();
        mPresenter.checkChannelVersion();

        adapter = new FragPagerAdapter(getChildFragmentManager(), mFragShowIdList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(viewPager);

        //MobclickAgent.onEvent(_mActivity, UM_EVENT.UM_click_channel_ctwh);

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);

        HomeAPI.tokenIdentifyUrl(token, new JsonCallback<TokenIdentifyBean>() {
            @Override
            public void onSuccess(Response<TokenIdentifyBean> response) {
                if (response.body().getResult() == 1000){
                    if (response.body().getData().getCode() == 3){
                        AppCommonData.isLogin = true;
                    }
                }
            }
        });

        //检测是否更新版本
        mPresenter.checkVersion(getActivity());


//        //更新服务器上的版本
//        HomeAPI.setCRTVersion(AppCommonData.versionCode, new JsonCallback<logoutBean>() {
//            @Override
//            public void onSuccess(Response<logoutBean> response) {
//                if (response.body().getResult() == 1000){
//                    ToastUtils.showShort(response.body().getMessage());
//                }
//
//            }
//        });

    }

    //获取当前版本号
    public void getVersionCode(){
        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getActivity().getPackageName(), 0);
            AppCommonData.versionCode = packageInfo.versionCode;
            AppCommonData.versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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

        if (ButtonUtils.isFastDoubleClick(R.id.home_add_channel)){
            return;
        }

        Intent intent = new Intent();
        intent.setClass(_mActivity, ChannelActivity.class);
        intent.putExtra("from", ChannelActivity.FROM_MAIN);
        intent.putExtra("position",viewPager.getCurrentItem());
        intent.putExtra("title","");
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_search)
    public void intoSearchView(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_search)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.home_notify)
    public void home_notify(){
        if (ButtonUtils.isFastDoubleClick(R.id.home_notify)){
            return;
        }

        //WeApplication.startWonderEnglish(_mActivity, "");
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), WEShopActivity.class);
        startActivity(intent);

    }

    //更新频道信息  //弃用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateChannelData(UpdateChannelEvent event) {
    }

    //拍照结果跳转   打开教辅频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openJF(OpenJFChannelEvent event) {

        mTab.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTab.getTabAt(2).select();
            }
        }, 100);
    }

    //打开教材频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openJC(OpenJCChannelEvent event) {

        mTab.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTab.getTabAt(1).select();
            }
        }, 100);
    }

    //频道数量顺序发生改变
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reloadChannel(ReloadChannelEvent event) {
        //从新获取我的频道的顺序
        mPresenter.getUserChannelGrade();
    }


    //断网再次访问
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestAagain(RequestNetWorkAgainEvent event) {
        loading.show();
        //后台登陆
        if (!AppCommonData.loginBackground){
            mPresenter.autoLogin();
        }else{
            mPresenter.getUserChannelGrade();
        }

    }

    //屏蔽阅读与写作频道
    public void removeCompositionChannel(){
         ChannelEntity entity = null;
         for (int i=0; i<mFragShowIdList.size(); i++){
             if (mFragShowIdList.get(i).getName().equals("阅读与写作")){
                 entity = mFragShowIdList.get(i);
             }
          }

          if (entity != null){
              mFragShowIdList.remove(entity);
              LogUtils.d("暂时屏蔽了阅读与写作栏目");
          }

    }

    @Override
    public void getChannelGradeSuccess(int grade, List<ChannelEntity> _mFragShowIdList) {
        if (loading!=null){
            loading.dismiss();
        }
        AppCommonData.userGrade = grade;
        mFragShowIdList.clear();
        mFragShowIdList.addAll(_mFragShowIdList);

        adapter.notifyDataSetChanged();
        ((HomePageActivity)_mActivity).hideNoConnect();
    }

    @Override
    public void getChannelGradeFail(int error_code, String msg) {
        if (getActivity() != null){
            loading.dismiss();
        }

        if (error_code == 2001 && msg.equals("HTTP头部缺少GL-Token字段")){
            mPresenter.autoLogin();
        }
        ((HomePageActivity)_mActivity).showNoConnect();
    }

    @Override
    public void getChannelGradeNetWorkError() {
        if (getActivity() != null){
            loading.dismiss();
        }

        ToastUtils.showShort("获取频道列表失败，请检查网络");
        ((HomePageActivity)_mActivity).showNoConnect();
    }


    @Override
    public void updateApp() {
        new AlertDialog.Builder(getActivity())
                .setTitle("发现新版本")
                .setMessage("检测有更新的版本，是否升级")
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DownloadManager manager = DownloadManager.getInstance(getActivity());
                        manager.setApkName("app-release.apk")
                                .setApkUrl("http://student.hebeijiaoyu.com.cn/glwz/glapp/app/app-release.apk")
                                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .download();
                    }
                }).create().show();
    }

}
