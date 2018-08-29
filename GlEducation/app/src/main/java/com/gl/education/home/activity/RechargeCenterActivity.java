package com.gl.education.home.activity;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.PayFinishEvent;
import com.gl.education.home.model.AliPayOrderBean;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.home.model.JSPayOverBean;
import com.gl.education.home.model.WXPreOrderBean;
import com.gl.education.payInfo.MyALipayUtils;
import com.gl.education.payInfo.MyWXPayUtils;
import com.gl.education.teachingassistant.event.JSJFBookRechargeOpenWebViewEvent;
import com.gl.education.teachingassistant.event.JSJFBookWXPayEvent;
import com.gl.education.teachingassistant.event.JSJFBookZFBPayEvent;
import com.gl.education.teachingassistant.interactive.JFBookRechargeInteractive;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值中心
 */
public class RechargeCenterActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    protected AgentWeb mAgentWeb;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_recharge_center;
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

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(AppConstant.TH_RECHARGE_URL + token);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFBookRechargeInteractive(mAgentWeb,
                this));//参考教辅充值界面  复用一下
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
        if (mAgentWeb!=null){
            isBack = mAgentWeb.back();
            if (isBack == true)
                return;
        }
        super.onBackPressedSupport();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paySuccess(JSJFBookRechargeOpenWebViewEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        ToastUtils.showShort("充值成功");
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

            mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant.callJs_payInfo, json);

        }
    }


    //发起支付请求   获取预订单  调起微信
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWXPayMoneyEvent(JSJFBookWXPayEvent event) {
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
                                            (RechargeCenterActivity.this, appId);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ToastUtils.showShort("支付出现问题，请更换支付方式");
                                }
                            }
                        }
                    });
        }
    }

    //发起支付请求   获取预订单  调起支付宝
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZFBPayMoneyEvent(JSJFBookZFBPayEvent event) {
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
                                    builder.build(eventTAG, orderId).toALiPay(RechargeCenterActivity
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
    }

}
