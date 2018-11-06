package com.gl.education.smallclass.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.smallclass.event.JSWKBookPaySuccessFinishEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/22.
 */

public class WKBookPaySuccessInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public WKBookPaySuccessInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void RechargeSuccess(){
        JSWKBookPaySuccessFinishEvent event = new JSWKBookPaySuccessFinishEvent();
        EventBus.getDefault().post(event);
    }
}
