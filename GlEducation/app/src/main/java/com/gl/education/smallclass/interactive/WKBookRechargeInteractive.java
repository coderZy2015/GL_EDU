package com.gl.education.smallclass.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.smallclass.event.JSWKBookRechargeOpenWebViewEvent;
import com.gl.education.smallclass.event.JSWKBookWXPayEvent;
import com.gl.education.smallclass.event.JSWKBookZFBPayEvent;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class WKBookRechargeInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WKBookRechargeInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSWKBookRechargeOpenWebViewEvent event = new JSWKBookRechargeOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //点击购买
    @JavascriptInterface
    public void ClickToBuy(String _json) {
        String json = _json;
        LogUtils.d(""+json);
        if (json != null){
            try {
                JSPayInfoBean bean = Convert.fromJson(json, JSPayInfoBean.class);

                if (bean.getOrderType().equals("2")) //支付宝
                {
                    JSWKBookZFBPayEvent event = new JSWKBookZFBPayEvent();
                    event.setBean(bean);
                    EventBus.getDefault().post(event);
                }
                else if(bean.getOrderType().equals("3"))//微信
                {
                    JSWKBookWXPayEvent event = new JSWKBookWXPayEvent();
                    event.setBean(bean);
                    EventBus.getDefault().post(event);
                }

            } catch (JsonIOException e) {
                e.printStackTrace();
                ToastUtils.showShort("订单创建失败，请联系客服");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                ToastUtils.showShort("订单创建失败，请联系客服");
            }
        }
    }
}
