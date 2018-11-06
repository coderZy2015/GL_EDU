package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSGetArticleContentEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/20.
 */

public class RAWDetailInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWDetailInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getArticleContent(String json){
        LogUtils.d("getArticleContent json = "+json);
        JSGetArticleContentEvent event = new JSGetArticleContentEvent();
        EventBus.getDefault().post(event);
    }

}
