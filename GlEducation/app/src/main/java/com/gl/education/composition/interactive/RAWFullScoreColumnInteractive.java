package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSFULLopenListeEvent;
import com.gl.education.composition.event.JSgetFullScoreColumnEvent;
import com.gl.education.composition.model.OpenListBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/18.
 */

public class RAWFullScoreColumnInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWFullScoreColumnInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getFullScoreColumn(String json){
        LogUtils.d("getFullScoreColumn json = "+json);
        JSgetFullScoreColumnEvent event = new JSgetFullScoreColumnEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void openList(String json){
        LogUtils.d("openList json = "+json);
        OpenListBean bean = Convert.fromJson(json, OpenListBean.class);
        JSFULLopenListeEvent event = new JSFULLopenListeEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
