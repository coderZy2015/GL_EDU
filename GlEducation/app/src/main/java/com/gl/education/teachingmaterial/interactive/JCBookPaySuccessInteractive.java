package com.gl.education.teachingmaterial.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.teachingmaterial.event.JSJCBookPaySuccessFinishEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/22.
 */

public class JCBookPaySuccessInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public JCBookPaySuccessInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void RechargeSuccess(){
        JSJCBookPaySuccessFinishEvent event = new JSJCBookPaySuccessFinishEvent();
        EventBus.getDefault().post(event);
    }

}
