package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.home.activity.BookMenuActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenWebViewEvent;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.interactive.AndroidInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 作文fragment
 */

public class CompositionFragment extends BaseFragment {

    //传递消息的相关字段
    private final String eventStr = "CompositionFragment";

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_angentWeb)
    Button btn_angentWeb;

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
        return null;
    }

    public static TeachingMaterialFragment newInstance(ChannelEntity entity) {
        TeachingMaterialFragment frag = new TeachingMaterialFragment();
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
        token = "?token="+token;

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
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新


        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInteractive(mAgentWeb,
                getActivity(), eventStr, ""));

    }

    @Override
    public boolean onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();

        return isBack;
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(OpenWebViewEvent event) {
        if (event.getMessage().equals(eventStr+"_toBookDetailPage")){
            Intent intent = new Intent();
            intent.putExtra("bookTitle", "作文目录");
            intent.putExtra("fromStr", "zuowen");
            intent.setClass(getActivity(), BookMenuActivity.class);
            startActivity(intent);
        }

    }

}