package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSCASEopenListeEvent;
import com.gl.education.composition.event.JSgetCaseColumnEvent;
import com.gl.education.composition.model.OpenListBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/19.
 */

public class RAWCaseColumnInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWCaseColumnInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getCaseColumn(String json){
        LogUtils.d("getCaseColumn json = "+json);
        JSgetCaseColumnEvent event = new JSgetCaseColumnEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void openList(String json){
        LogUtils.d("openList json = "+json);
        OpenListBean bean = Convert.fromJson(json, OpenListBean.class);
        JSCASEopenListeEvent event = new JSCASEopenListeEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
