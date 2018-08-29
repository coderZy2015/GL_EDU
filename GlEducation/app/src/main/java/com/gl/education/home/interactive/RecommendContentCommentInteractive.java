package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSAPPGetAriticleCommentEvent;
import com.gl.education.home.model.JSAppGetRecommendData;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/29.
 */

public class RecommendContentCommentInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public RecommendContentCommentInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //app获取数据
    @JavascriptInterface
    public void getArticleComments(String json){
        LogUtils.d("json = "+json);
        JSAppGetRecommendData bean = Convert.fromJson(json, JSAppGetRecommendData.class);
        JSAPPGetAriticleCommentEvent event = new JSAPPGetAriticleCommentEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
