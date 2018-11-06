package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSgetPayParamEvent;
import com.gl.education.composition.event.JSrequestPayEvent;
import com.gl.education.composition.event.JSrequestPayMoneyEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/22.
 */

public class RAWPayInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWPayInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getPayParam(String json){
        LogUtils.d("getPayParam json = "+json);
        JSgetPayParamEvent event = new JSgetPayParamEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void requestPay(String json){
        LogUtils.d("requestPay json = "+json);

        JSrequestPayEvent event = new JSrequestPayEvent();
        EventBus.getDefault().post(event);
    }

    @JavascriptInterface
    public void requestMoney(String json){
        LogUtils.d("requestPayMoney json = "+json);

        JSrequestPayMoneyEvent event = new JSrequestPayMoneyEvent();
        EventBus.getDefault().post(event);
    }
}
