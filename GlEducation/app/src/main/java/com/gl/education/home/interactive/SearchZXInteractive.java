package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSSearchZXOpenWebViewEvent;
import com.gl.education.home.model.JSRecommentBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/23.
 */

public class SearchZXInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public SearchZXInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }


    @JavascriptInterface
    public void openNewsView(String json){
        //LogUtils.d("json = "+json);
        JSRecommentBean bean = Convert.fromJson(json, JSRecommentBean.class);
        JSSearchZXOpenWebViewEvent event = new JSSearchZXOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

}
