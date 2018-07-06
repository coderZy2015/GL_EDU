package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.api.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.BookDetailActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.PayMoneyEvent;
import com.gl.education.home.event.ToTeachingAssistantDetailPageEvent;
import com.gl.education.home.model.AliPayOrderBean;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.home.model.WXPreOrderBean;
import com.gl.education.home.utlis.AndroidInterface;
import com.gl.education.payInfo.MyALipayUtils;
import com.gl.education.payInfo.MyWXPayUtils;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 教辅fragment
 */

public class TeachingAssistantFragment extends BaseFragment {

    //传递消息的相关字段
    private final String eventStr = "TeachingAssistantFragment";
    private final String AliPayEvenyStr = eventStr+"_AliPayMoney";
    private final String WxPayEventStr = eventStr+"_WXPayMoney";

    @BindView(R.id.web_container)
    LinearLayout web_container;

    protected AgentWeb mAgentWeb;
    public boolean fragIsHide;

    String url = "http://guanlin.gl.to3.cc/paytest.html";
    String ass_url = "http://guanlin.gl.to3.cc/dist/index.html#/wdjf";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_webview;
    }

    public static TeachingAssistantFragment newInstance() {
        Bundle args = new Bundle();

        TeachingAssistantFragment fragment = new TeachingAssistantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        super.init();
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            fragIsHide = true;
        } else {
            fragIsHide = false;
        }
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LogUtils.d("onLazyInitView");
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(ass_url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新

        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb,
                getActivity(), eventStr));


//        /**
//         * webView与ViewPager所带来的滑动冲突问题解决方法
//         */
//        mAgentWeb.getWebCreator().getWebView().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mAgentWeb.getWebCreator().getWebView().getParent()
//                        .requestDisallowInterceptTouchEvent(true);
//                int x = (int) event.getRawX();
//                int y = (int) event.getRawY();
//                int lastX = 0;
//                int lastY = 0;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int deltaY = y - lastY;
//                        int deltaX = x - lastX;
//                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
//                            mAgentWeb.getWebCreator().getWebView().getParent()
//                                    .requestDisallowInterceptTouchEvent(false);
//                        } else {
//                            mAgentWeb.getWebCreator().getWebView().getParent()
//                                    .requestDisallowInterceptTouchEvent(true);
//                        }
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
    }


    @Override
    public boolean onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();

        return isBack;
    }

    //跳转到教辅详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(ToTeachingAssistantDetailPageEvent event) {

        Intent intent = new Intent();
        intent.putExtra("bookDetailUrl", event.getUrl());
        intent.setClass(getActivity(), BookDetailActivity.class);
        startActivity(intent);
    }


    //发起支付请求   获取预订单  调起支付宝
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayMoneyEvent(PayMoneyEvent event) {

        if (event.getMessage().equals(AliPayEvenyStr)) {
            JSPayInfoBean bean = event.getBean();
            if (bean != null) {
                HomeAPI.getAlipayOrder(bean.getUid(), bean.getPrice(), new JsonCallback<AliPayOrderBean>() {
                    @Override
                    public void onSuccess(Response<AliPayOrderBean> response) {
                        AliPayOrderBean responseData = response.body();

                        try {
                            if (responseData.getResult() == 1000){
                                MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
                                String orderId = responseData.getData().getOrderid();
                                builder.build(eventStr, orderId).toALiPay(getActivity(), responseData.getData().getOrderStr());
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
                HomeAPI.getWxOrder(bean.getUid(), bean.getPrice(), new JsonCallback<WXPreOrderBean>() {

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
                                        .build(eventStr, orderId).toWXPayNotSign(getActivity(), appId);

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
