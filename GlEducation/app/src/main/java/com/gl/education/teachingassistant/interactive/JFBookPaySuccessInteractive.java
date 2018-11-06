package com.gl.education.teachingassistant.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.teachingassistant.event.JSJFBookPaySuccessFinishEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class JFBookPaySuccessInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JFBookPaySuccessInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    @JavascriptInterface
    public void RechargeSuccess(){
        JSJFBookPaySuccessFinishEvent event = new JSJFBookPaySuccessFinishEvent();
        EventBus.getDefault().post(event);
    }
}
