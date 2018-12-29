package com.gl.education.smallclass.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.THJsParamsData;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.JSLoginSuccess;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.smallclass.event.JSWKBookBuySuccessFinishEvent;
import com.gl.education.smallclass.event.JSWKBookMenuLoginEvent;
import com.gl.education.smallclass.event.JSWKBookMenuOpenWebViewEvent;
import com.gl.education.smallclass.interactive.WKBookContentInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WKBookContentActivity extends BaseActivity {

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
        return R.layout.activity_wkbook_content;
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
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WKBookContentInteractive(mAgentWeb,
                this));

        if(AppCommonData.th_isShare == false){
            content_share.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toLogin(JSWKBookMenuLoginEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, LoginInfoActivity.class);
        startActivityForResult(intent, 100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openWebView(JSWKBookMenuOpenWebViewEvent event) {
        if (event.getBean().getParam().equals(THJsParamsData.wk_intoOrderPayment)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, WKBookOrderPaymentActivity.class);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payFinish(JSWKBookBuySuccessFinishEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
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

                mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                        .callJs_login, json);
            }
        }
    }

    @OnClick(R.id.content_share)
    public void shareContent(){
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_getShareData, "");
    }

}
