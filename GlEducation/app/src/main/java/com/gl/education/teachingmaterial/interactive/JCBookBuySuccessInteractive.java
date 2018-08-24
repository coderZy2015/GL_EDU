package com.gl.education.teachingmaterial.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.teachingmaterial.event.JSJCBookBuySuccessFinishEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class JCBookBuySuccessInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JCBookBuySuccessInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void BuySuccess(){
        JSJCBookBuySuccessFinishEvent event = new JSJCBookBuySuccessFinishEvent();
        EventBus.getDefault().post(event);
    }
}
