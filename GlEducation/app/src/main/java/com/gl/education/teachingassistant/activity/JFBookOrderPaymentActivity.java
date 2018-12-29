package com.gl.education.teachingassistant.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.R;
import com.gl.education.app.THJsParamsData;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.teachingassistant.event.JSJFBookBuySuccessFinishEvent;
import com.gl.education.teachingassistant.event.JSJFBookOrderPaymentOpenWebViewEvent;
import com.gl.education.teachingassistant.event.JSJFBookPaySuccessFinishEvent;
import com.gl.education.teachingassistant.interactive.JFBookOrderPaymentInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 教辅订单支付界面
 */
public class JFBookOrderPaymentActivity extends BaseActivity {

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
        return R.layout.activity_jfbook_order_payment;
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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFBookOrderPaymentInteractive(mAgentWeb,
                this));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSJFBookOrderPaymentOpenWebViewEvent event) {
        if (event.getBean().getParam().equals(THJsParamsData.intoRecharge)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookRechargeAactivity.class);
            startActivity(intent);
        }else if (event.getBean().getParam().equals(THJsParamsData.jf_intoPackageBuy)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookPackageBuyActivity.class);
            startActivity(intent);
        }else if (event.getBean().getParam().equals(THJsParamsData.intoBuySuccess)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookBuySuccessActivity.class);
            startActivity(intent);
        }

    }

    //充值成功刷新代币数量
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payFinish(JSJFBookPaySuccessFinishEvent event) {
        LogUtils.d("充值成功刷新代币数量");
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }

    //购买成功关闭
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSJFBookBuySuccessFinishEvent event) {
        finish();
    }
}
