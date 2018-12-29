package com.gl.education.teachingassistant.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.THJsParamsData;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.teachingassistant.event.JSJFBookAnalysisOpenWebViewEvent;
import com.gl.education.teachingassistant.interactive.JFBookAnalysisInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 习题解析详情页
 */
public class JFBookAnalysisActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    @BindView(R.id.content_share)
    RelativeLayout content_share;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    private String url;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_jfbook_analysis;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        // 注册订阅者
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        bookTitle = intent.getStringExtra("title");
        if (bookTitle!=null)
            top_title.setText(""+bookTitle);

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFBookAnalysisInteractive(mAgentWeb,
                this));

        if(AppCommonData.th_isShare == false){
            content_share.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        mAgentWeb.getWebLifeCycle().onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressedSupport();
    }

    @Override
    public void onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();
        if (isBack == true)
            return;

        super.onBackPressedSupport();
    }

    @OnClick(R.id.content_share)
    public void shareContent(){
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_getShareData, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSJFBookAnalysisOpenWebViewEvent event) {
        if (event.getBean().getParam().equals(THJsParamsData.jf_intoBookWK)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookWKActivity.class);
            startActivity(intent);
        }else if (event.getBean().getParam().equals(THJsParamsData.jf_intoOtherMore)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookOtherMoreActivity.class);
            startActivity(intent);
        }

    }
}
