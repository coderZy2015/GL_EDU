package com.gl.education.teachingassistant.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.teachingassistant.interactive.JFBookWKInteractive;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;

public class JFBookWKActivity extends BaseActivity {

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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_jfbook_wk;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFBookWKInteractive(mAgentWeb,
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
}
