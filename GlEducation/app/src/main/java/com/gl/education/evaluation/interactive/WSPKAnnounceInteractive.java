package com.gl.education.evaluation.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.evaluation.event.JSGetSelectedAactivityInfoEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/16.
 */

public class WSPKAnnounceInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WSPKAnnounceInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获取选中活动信息
    @JavascriptInterface
    public void getSelectedActivityInfo(String json){
        LogUtils.d("getSelectedActivityInfo json = "+json);
        JSGetSelectedAactivityInfoEvent event = new JSGetSelectedAactivityInfoEvent();
        EventBus.getDefault().post(event);
    }
}
