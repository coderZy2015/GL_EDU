package com.gl.education.teachingmaterial.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.teachingmaterial.event.JSJCBookContentLoginEvent;
import com.gl.education.teachingmaterial.event.JSJCBookContentOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class JCBookContentInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JCBookContentInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        JSJCBookContentLoginEvent event = new JSJCBookContentLoginEvent();
        EventBus.getDefault().post(event);
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJCBookContentOpenWebViewEvent event = new JSJCBookContentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
