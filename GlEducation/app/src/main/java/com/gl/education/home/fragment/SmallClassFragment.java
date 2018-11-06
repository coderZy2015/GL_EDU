package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSWKDropDwonEvent;
import com.gl.education.home.event.JSWKFragmentLoginEvent;
import com.gl.education.home.event.JSWKFragmentOpenWebViewEvent;
import com.gl.education.home.event.UpdateChannelDataEvent;
import com.gl.education.home.interactive.WKFragmentInteractive;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.JSLoginSuccess;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.smallclass.activity.WKBookBetterClassActivity;
import com.gl.education.smallclass.activity.WKBookContentActivity;
import com.gl.education.smallclass.activity.WKBookMenuActivity;
import com.gl.education.smallclass.activity.WKBookOrderPaymentActivity;
import com.gl.education.smallclass.activity.WKBookShelfActivity;
import com.gl.education.smallclass.event.JSWKBookBuySuccessFinishEvent;
import com.just.agentweb.AgentWeb;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 微课fragment
 */

public class SmallClassFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private boolean isCanDropDown = false;

    protected AgentWeb mAgentWeb;

    String url = "";

    String token = "";

    private ChannelEntity channelEntity;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_webview;
    }

    @Override
    protected String setIdentifier() {
        return "HomePageFragment";
    }

    public static SmallClassFragment newInstance(ChannelEntity entity) {
        SmallClassFragment frag = new SmallClassFragment();
        Bundle b = new Bundle();

        b.putSerializable("ChannelEntity", entity);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void init() {
        super.init();
        // 注册订阅者
        EventBus.getDefault().register(this);

        Bundle args = getArguments();
        if (args != null) {
            channelEntity = (ChannelEntity) args.getSerializable("ChannelEntity");
        }

        url = channelEntity.getUrl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token+"&grade="+ AppCommonData.userGrade;

        //url = "http://appstuweb.hebeijiaoyu.cn/#/weike";

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url+token);


        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WKFragmentInteractive(mAgentWeb,
                getActivity()));

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAgentWeb.getWebCreator().getWebView().reload();    //刷新
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                isCanDropDown = false;
            }
        });

        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                if (isCanDropDown){
                    isCanDropDown = false;
                    return true;
                }
                return false;
            }

            @Override
            public boolean canLoadMore(View content) {
                return false;
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        boolean isBack = false;

        if (mAgentWeb!= null)
            isBack = mAgentWeb.back();

        return isBack;
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookMenuEvent(JSWKFragmentOpenWebViewEvent event) {
        if (event.getBean().getTitle().equals("同步课程目录")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), WKBookMenuActivity.class);
            startActivity(intent);
        }else if (event.getBean().getTitle().equals("同步课程内容")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), WKBookContentActivity.class);
            startActivity(intent);
        }else if (event.getBean().getTitle().equals("订单支付")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), WKBookOrderPaymentActivity.class);
            startActivity(intent);
        }else if (event.getBean().getTitle().equals("精品课程")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), WKBookBetterClassActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), WKBookShelfActivity.class);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payFinish(JSWKBookBuySuccessFinishEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }

    //跳转到登录页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toLogin(JSWKFragmentLoginEvent event) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginInfoActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            if (data != null) {
                String token = data.getStringExtra("token");
                JSLoginSuccess loginS = new JSLoginSuccess();
                loginS.setGuid(token);
                String json = Convert.toJson(loginS);

                LogUtils.d("onActivityResult");
                mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                        .callJs_login, json);
            }

        }
    }

    //更新频道信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateChannelData(UpdateChannelDataEvent event) {
        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token+"&grade="+ AppCommonData.userGrade;
        if (mAgentWeb!=null){
            mAgentWeb.getWebCreator().getWebView().loadUrl(url+token);
            mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        }

    }

    //下拉刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dropDownEvent(JSWKDropDwonEvent event) {
        isCanDropDown = true;
    }

}