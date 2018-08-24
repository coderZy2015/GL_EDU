package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.MyJCCollectionOpenWebViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/24.
 */

public class MyJCCollectionInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public MyJCCollectionInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入目录或者我的书架
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);

        MyJCCollectionOpenWebViewEvent event = new MyJCCollectionOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
