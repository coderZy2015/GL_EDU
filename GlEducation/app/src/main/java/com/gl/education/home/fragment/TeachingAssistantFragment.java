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
import com.gl.education.app.THJsParamsData;
import com.gl.education.app.UM_EVENT;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSJFDropDownEvent;
import com.gl.education.home.event.JSJFFragmentOpenWebViewEvent;
import com.gl.education.home.event.JSJFFragmentRefreshViewEvent;
import com.gl.education.home.event.UpdateChannelDataEvent;
import com.gl.education.home.interactive.JFFragInteractive;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.teachingassistant.activity.JFBookMenuActivity;
import com.gl.education.teachingassistant.activity.JFBookShelfActivity;
import com.gl.education.teachingassistant.interactive.JFBookBuySuccessInteractive;
import com.just.agentweb.AgentWeb;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 教辅fragment
 */

public class TeachingAssistantFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    protected AgentWeb mAgentWeb;

    String url = "";

    String token = "";

    private boolean isCanDropDown = false;

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

    public static TeachingAssistantFragment newInstance(ChannelEntity entity) {
        TeachingAssistantFragment frag = new TeachingAssistantFragment();
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (!AppCommonData.isClickJF){
            AppCommonData.isClickJF = true;
            MobclickAgent.onEvent(_mActivity, UM_EVENT.UM_click_channel_jf);
        }

        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token+"&grade="+ AppCommonData.userGrade;

        //url = "http://192.168.199.37:8080/#/wdjf";

        //url = "http://code.byepub.cn/abc.html";//H5调用相机测试

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .closeIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url+token);
                //.go(url);

        LogUtils.d("教辅");

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示


        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFFragInteractive(mAgentWeb,
                getActivity()));

        //refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAgentWeb.getWebCreator().getWebView().reload();    //刷新
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
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
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressedSupport() {
        boolean isBack = false;
        if (mAgentWeb!= null)
            isBack = mAgentWeb.back();

        return isBack;
    }

    //跳转到我的书架和详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookMenuEvent(JSJFFragmentOpenWebViewEvent event) {
       if (event.getBean().getParam().equals(THJsParamsData.jf_intoBookShelf)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(getActivity(), JFBookShelfActivity.class);
            startActivity(intent);
        }else if(event.getBean().getParam().equals(THJsParamsData.jf_intoBookMenu)){
           Intent intent = new Intent();
           intent.putExtra("url", event.getBean().getUrl());
           intent.putExtra("title", event.getBean().getTitle());
           intent.setClass(getActivity(), JFBookMenuActivity.class);
           startActivity(intent);
       }
    }

    //刷新我的书架
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshView(JSJFFragmentRefreshViewEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();
    }

    //购买成功刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JFBookBuySuccessInteractive event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }

    //更新频道信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateChannelData(UpdateChannelDataEvent event) {
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token+"&grade="+ AppCommonData.userGrade;
        if (mAgentWeb!=null){
            mAgentWeb.getWebCreator().getWebView().loadUrl(url+token);
            mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        }
    }

    //更新频道信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dropDownEvent(JSJFDropDownEvent event) {
        isCanDropDown = true;
    }

}
