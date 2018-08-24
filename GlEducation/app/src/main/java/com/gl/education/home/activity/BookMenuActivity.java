package com.gl.education.home.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.MessageEvent;
import com.gl.education.home.event.OpenWebViewEvent;
import com.gl.education.home.event.PayFinishEvent;
import com.gl.education.home.event.PayMoneyEvent;
import com.gl.education.home.model.AliPayOrderBean;
import com.gl.education.home.model.JSLoginSuccess;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.home.model.JSPayOverBean;
import com.gl.education.home.model.WXPreOrderBean;
import com.gl.education.home.interactive.AndroidInteractive;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.payInfo.MyALipayUtils;
import com.gl.education.payInfo.MyWXPayUtils;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 书籍目录全屏页面  废弃
 */
public class BookMenuActivity extends BaseActivity {

    public String bookTitle = "";
    //支付宝字段
    private String AliPayEvenyStr;
    //微信字段
    private String WxPayEventStr;

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;

    private String classType = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_book_menu;
    }

    @Override
    protected String setIdentifier() {
        return "BookMenuActivity";
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        bookTitle = intent.getStringExtra("title");
        AliPayEvenyStr = eventTAG + AndroidInteractive.callApp_toAliPayMoney;
        WxPayEventStr = eventTAG + AndroidInteractive.callApp_toWXPayMoney;
        if (bookTitle!=null)
            top_title.setText(""+bookTitle);

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
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInteractive(mAgentWeb,
                this, eventTAG, classType));

    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressedSupport();
    }


    //跳转到登录页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toLogin(MessageEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, LoginInfoActivity.class);
        startActivityForResult(intent, 100);
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(OpenWebViewEvent event) {
        if (event.getMessage().equals(eventTAG)){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, BookDetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
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

                mAgentWeb.getJsAccessEntrace().quickCallJs(AndroidInteractive
                        .callJs_login, json);
            }

        }
    }

    @Override
    public void onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();
        if (isBack == true)
            return;

        super.onBackPressedSupport();
    }

    //支付结束  通知js
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payOver(PayFinishEvent event) {
        if (event.getMessage().equals(eventTAG)) {

            JSPayOverBean bean = new JSPayOverBean();
            bean.setOrderId(event.getOrderId());
            bean.setStatus("" + event.getResult());
            String json = Convert.toJson(bean);

            LogUtils.d(json);

            mAgentWeb.getJsAccessEntrace().quickCallJs(AndroidInteractive.callJs_payInfo, json);

        }
    }

    //发起支付请求   获取预订单  调起支付宝 微信
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayMoneyEvent(PayMoneyEvent event) {
        if (event.getMessage().equals(AliPayEvenyStr)) {
            JSPayInfoBean bean = event.getBean();
            if (bean != null) {
                HomeAPI.getAlipayOrder(bean.getPrice(), new
                        JsonCallback<AliPayOrderBean>() {
                            @Override
                            public void onSuccess(Response<AliPayOrderBean> response) {
                                AliPayOrderBean responseData = response.body();

                                try {
                                    if (responseData.getResult() == 1000) {
                                        MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils
                                                .ALiPayBuilder();
                                        String orderId = responseData.getData().getOrderid();
                                        builder.build(eventTAG, orderId).toALiPay(BookMenuActivity
                                                .this, responseData.getData().getOrderStr());
                                    } else {
                                        ToastUtils.showShort("创建订单出现问题");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ToastUtils.showShort("支付出现问题，请更换支付方式");
                                }


                            }
                        });
            }

        } else if (event.getMessage().equals(WxPayEventStr)) {
            JSPayInfoBean bean = event.getBean();
            if (bean != null) {
                HomeAPI.getWxOrder(bean.getPrice(), new
                        JsonCallback<WXPreOrderBean>() {

                            @Override
                            public void onSuccess(Response<WXPreOrderBean> response) {
                                WXPreOrderBean wxPreOrderBean = response.body();
                                if (wxPreOrderBean != null) {
                                    try {
                                        String orderId = wxPreOrderBean.getData().getOrderid();
                                        String appId = wxPreOrderBean.getData().getOrderStr()
                                                .getAppid();
                                        String partnerId = wxPreOrderBean.getData().getOrderStr()
                                                .getPartnerid();
                                        String prepayId = wxPreOrderBean.getData().getOrderStr()
                                                .getPrepayid();
                                        String nonceStr = wxPreOrderBean.getData().getOrderStr()
                                                .getNoncestr();
                                        String timestamp = "" + wxPreOrderBean.getData()
                                                .getOrderStr()
                                                .getTimestamp();
                                        String sgin = wxPreOrderBean.getData().getOrderStr()
                                                .getSign();

                                        MyWXPayUtils.WXPayBuilder builder = new MyWXPayUtils
                                                .WXPayBuilder();
                                        builder.setAppId(appId)
                                                .setPartnerId(partnerId)
                                                .setPrepayId(prepayId)
                                                .setPackageValue("Sign=WXPay")
                                                .setNonceStr(nonceStr)
                                                .setTimeStamp(timestamp)
                                                .setSign(sgin)
                                                .build(eventTAG, orderId).toWXPayNotSign
                                                (BookMenuActivity.this, appId);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        ToastUtils.showShort("支付出现问题，请更换支付方式");
                                    }
                                }
                            }
                        });

            }

        }
    }
}
