package com.gl.education.teachingassistant.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.teachingassistant.event.JSJFBookBuySuccessFinishEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class JFBookBuySuccessInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JFBookBuySuccessInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void BuySuccess(){
        JSJFBookBuySuccessFinishEvent event = new JSJFBookBuySuccessFinishEvent();
        EventBus.getDefault().post(event);
    }

}
