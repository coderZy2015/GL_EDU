package com.gl.education.smallclass.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.THJsParamsData;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.smallclass.event.JSWKBookBuySuccessFinishEvent;
import com.gl.education.smallclass.event.JSWKBookOrderPaymentOpenWebViewEvent;
import com.gl.education.smallclass.event.JSWKBookPaySuccessFinishEvent;
import com.gl.education.smallclass.interactive.WKBookOrderPaymentInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单支付
 */
public class WKBookOrderPaymentActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wkbook_order_payment;
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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WKBookOrderPaymentInteractive(mAgentWeb,
                this));
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
    public void toBookDetailEvent(JSWKBookOrderPaymentOpenWebViewEvent event) {
        if (event.getBean().getParam().equals(THJsParamsData.intoRecharge)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, WKBookRechargeAactivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, WKBookBuySuccessActivity.class);
            startActivity(intent);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payFinish(JSWKBookBuySuccessFinishEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSWKBookPaySuccessFinishEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }
}
