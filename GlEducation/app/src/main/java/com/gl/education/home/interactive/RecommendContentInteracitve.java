package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSRecommentContentGetDataEvent;
import com.gl.education.home.model.JSAppGetRecommendData;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/25.
 */

public class RecommendContentInteracitve {
    private AgentWeb mAgentWeb;
    private Context context;

    public RecommendContentInteracitve(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //app获取数据
    @JavascriptInterface
    public void getNewsData(String json){
        LogUtils.d("json = "+json);
        JSAppGetRecommendData bean = Convert.fromJson(json, JSAppGetRecommendData.class);
        JSRecommentContentGetDataEvent event = new JSRecommentContentGetDataEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

}
