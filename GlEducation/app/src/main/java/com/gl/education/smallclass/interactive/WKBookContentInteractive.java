package com.gl.education.smallclass.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.smallclass.event.JSWKBookMenuLoginEvent;
import com.gl.education.smallclass.event.JSWKBookMenuOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class WKBookContentInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WKBookContentInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        JSWKBookMenuLoginEvent event = new JSWKBookMenuLoginEvent();
        EventBus.getDefault().post(event);
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSWKBookMenuOpenWebViewEvent event = new JSWKBookMenuOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
