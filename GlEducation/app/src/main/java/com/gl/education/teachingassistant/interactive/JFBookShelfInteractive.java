package com.gl.education.teachingassistant.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJFFragmentRefreshViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.teachingassistant.event.JSJFBookShelfOpenWebViewEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class JFBookShelfInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JFBookShelfInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJFBookShelfOpenWebViewEvent event = new JSJFBookShelfOpenWebViewEvent();
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
