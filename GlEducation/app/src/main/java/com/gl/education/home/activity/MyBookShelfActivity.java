package com.gl.education.home.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenWebViewEvent;
import com.gl.education.home.interactive.AndroidInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的书架详情页面  废弃
 */
public class MyBookShelfActivity extends BaseActivity {

    public String bookTitle = "";

    protected AgentWeb mAgentWeb;

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.btn_angentWeb)
    Button btn_angentWeb;

    @BindView(R.id.top_title)
    TextView top_title;

    private String classType = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_book_shelf;
    }

    @Override
    protected String setIdentifier() {
        return "MyBookShelfActivity";
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        bookTitle = intent.getStringExtra("title");

        if (bookTitle != null)
        top_title.setText(bookTitle);

        // 注册订阅者
        EventBus.getDefault().register(this);

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInteractive(mAgentWeb,
                this, eventTAG, classType));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }


    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookMenuEvent(OpenWebViewEvent event) {
        Intent intent = new Intent();
        intent.putExtra("url", event.getBean().getUrl());
        intent.putExtra("title", event.getBean().getTitle());
        intent.setClass(this, BookMenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressedSupport();
        LogUtils.d("backPressed");
    }

    @Override
    public void onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();
        if (isBack == true)
            return;
        //返回刷新页面
        setResult(1);
        finish();
    }
}
