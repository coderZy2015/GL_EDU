package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSgetHotColumnEvent;
import com.gl.education.composition.event.JSopenListEvent;
import com.gl.education.composition.model.OpenListBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/18.
 */

public class RAWHotColumnInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWHotColumnInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getHotColumn(String json){
        LogUtils.d("getHotColumn json = "+json);
        JSgetHotColumnEvent event = new JSgetHotColumnEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void openList(String json){
        LogUtils.d("openList json = "+json);
        OpenListBean bean = Convert.fromJson(json, OpenListBean.class);
        JSopenListEvent event = new JSopenListEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
