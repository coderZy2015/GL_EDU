package com.gl.education.teachingmaterial.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJCFragmentRefreshViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.teachingmaterial.event.JSJCBookMenuLoginEvent;
import com.gl.education.teachingmaterial.event.JSJCBookMenuOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class JCBookMenuInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public JCBookMenuInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        JSJCBookMenuLoginEvent event = new JSJCBookMenuLoginEvent();
        EventBus.getDefault().post(event);
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJCBookMenuOpenWebViewEvent event = new JSJCBookMenuOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //刷新我的书架
    @JavascriptInterface
    public void refreshBookshelf(){
        JSJCFragmentRefreshViewEvent event = new JSJCFragmentRefreshViewEvent();
        EventBus.getDefault().post(event);
    }
}
