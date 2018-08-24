package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSWKFragmentLoginEvent;
import com.gl.education.home.event.JSWKFragmentOpenWebViewEvent;
import com.gl.education.home.event.PayFinishEvent;
import com.gl.education.home.event.PayMoneyEvent;
import com.gl.education.home.event.UpdateChannelEvent;
import com.gl.education.home.interactive.AndroidInteractive;
import com.gl.education.home.interactive.WKFragmentInteractive;
import com.gl.education.home.model.AliPayOrderBean;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.JSLoginSuccess;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.home.model.JSPayOverBean;
import com.gl.education.home.model.WXPreOrderBean;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.payInfo.MyALipayUtils;
import com.gl.education.payInfo.MyWXPayUtils;
import com.gl.education.smallclass.activity.WKBookBetterClassActivity;
import com.gl.education.smallclass.activity.WKBookContentActivity;
import com.gl.education.smallclass.activity.WKBookMenuActivity;
import com.gl.education.smallclass.activity.WKBookOrderPaymentActivity;
import com.gl.education.smallclass.activity.WKBookShelfActivity;
import com.gl.education.smallclass.event.JSWKBookBuySuccessFinishEvent;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 微课fragment
 */

public class SmallClassFragment extends BaseFragment {

    //支付宝字段
    private String AliPayEvenyStr;
    //微信字段
    private String WxPayEventStr;

    @BindView(R.id.web_container)
    LinearLayout web_container;

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
        LogUtils.d("token");
        AliPayEvenyStr = eventTAG + AndroidInteractive.callApp_toAliPayMoney;
        WxPayEventStr = eventTAG + AndroidInteractive.callApp_toWXPayMoney;

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
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WKFragmentInteractive(mAgentWeb,
                getActivity()));

    }

    @Override
    public boolean onBackPressedSupport() {
        boolean isBack = false;
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
        startActivityForResult(intent, 103);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 103) {

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


    //发起支付请求   获取预订单  调起支付宝
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayMoneyEvent(PayMoneyEvent event) {

        if (event.getMessage().equals(AliPayEvenyStr)) {
            JSPayInfoBean bean = event.getBean();
            if (bean != null) {
                HomeAPI.getAlipayOrder(bean.getPrice(), new JsonCallback<AliPayOrderBean>() {
                    @Override
                    public void onSuccess(Response<AliPayOrderBean> response) {
                        AliPayOrderBean responseData = response.body();

                        try {
                            if (responseData.getResult() == 1000){
                                MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
                                String orderId = responseData.getData().getOrderid();
                                builder.build(eventTAG, orderId).toALiPay(getActivity(), responseData.getData().getOrderStr());
                            }else{

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
                HomeAPI.getWxOrder(bean.getPrice(), new JsonCallback<WXPreOrderBean>() {

                    @Override
                    public void onSuccess(Response<WXPreOrderBean> response) {
                        WXPreOrderBean wxPreOrderBean = response.body();
                        if (wxPreOrderBean != null){
                            try {
                                String orderId = wxPreOrderBean.getData().getOrderid();
                                String appId = wxPreOrderBean.getData().getOrderStr().getAppid();
                                String partnerId = wxPreOrderBean.getData().getOrderStr()
                                        .getPartnerid();
                                String prepayId = wxPreOrderBean.getData().getOrderStr().getPrepayid();
                                String nonceStr = wxPreOrderBean.getData().getOrderStr().getNoncestr();
                                String timestamp = "" + wxPreOrderBean.getData().getOrderStr()
                                        .getTimestamp();
                                String sgin = wxPreOrderBean.getData().getOrderStr().getSign();

                                MyWXPayUtils.WXPayBuilder builder = new MyWXPayUtils
                                        .WXPayBuilder();
                                builder.setAppId(appId)
                                        .setPartnerId(partnerId)
                                        .setPrepayId(prepayId)
                                        .setPackageValue("Sign=WXPay")
                                        .setNonceStr(nonceStr)
                                        .setTimeStamp(timestamp)
                                        .setSign(sgin)
                                        .build(eventTAG, orderId).toWXPayNotSign(getActivity(), appId);

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

    //更新频道信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateChannelData(UpdateChannelEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }
}