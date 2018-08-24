package com.gl.education.smallclass.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.smallclass.event.JSWKBookBetterClassOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/22.
 */

public class WKBookBetterClassInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WKBookBetterClassInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSWKBookBetterClassOpenWebViewEvent event = new JSWKBookBetterClassOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
