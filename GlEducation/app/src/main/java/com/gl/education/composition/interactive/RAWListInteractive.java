package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSgetListEvent;
import com.gl.education.composition.event.JSopenArticleEvent;
import com.gl.education.composition.model.OpenArticleBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/19.
 */

public class RAWListInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWListInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getList(String json){
        LogUtils.d("getList json = "+json);
        JSgetListEvent event = new JSgetListEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void openArticle(String json){
        LogUtils.d("openArticle json = "+json);
        OpenArticleBean bean = Convert.fromJson(json, OpenArticleBean.class);
        JSopenArticleEvent event = new JSopenArticleEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
