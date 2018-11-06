package com.gl.education.evaluation.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.evaluation.event.JSGetLessonDataEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/16.
 */

public class WSPKLessonInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WSPKLessonInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获取课程数据 列出lesson的内容  G
    @JavascriptInterface
    public void getLessonData(String json){
        LogUtils.d("getLessonData  json="+json);
        JSGetLessonDataEvent event = new JSGetLessonDataEvent();
        EventBus.getDefault().post(event);
    }
}
