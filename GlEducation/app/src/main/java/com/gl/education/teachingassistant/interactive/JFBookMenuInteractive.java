package com.gl.education.teachingassistant.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJFFragmentRefreshViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.teachingassistant.event.JSJFBookMenuLoginEvent;
import com.gl.education.teachingassistant.event.JSJFBookMenuOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class JFBookMenuInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JFBookMenuInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        JSJFBookMenuLoginEvent event = new JSJFBookMenuLoginEvent();
        EventBus.getDefault().post(event);
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJFBookMenuOpenWebViewEvent event = new JSJFBookMenuOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //刷新我的书架
    @JavascriptInterface
    public void refreshBookshelf(){
        JSJFFragmentRefreshViewEvent event = new JSJFFragmentRefreshViewEvent();
        EventBus.getDefault().post(event);
    }

}
