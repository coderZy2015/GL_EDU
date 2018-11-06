package com.gl.education.person.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.home.event.JSgetCommentDataEvent;
import com.gl.education.home.event.JSopenCompositionAttachmentEvent;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/22.
 */

public class MyCompositionDetailInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public MyCompositionDetailInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    @JavascriptInterface
    public void getCommentData(String json){
        LogUtils.d("getCommentData json = "+json);
        JSgetCommentDataEvent event = new JSgetCommentDataEvent();
        EventBus.getDefault().post(event);
    }

    @JavascriptInterface
    public void openCompositionAttachment(String json){
        LogUtils.d("openCompositionAttachment json = "+json);
        JSopenCompositionAttachmentEvent event = new JSopenCompositionAttachmentEvent();
        EventBus.getDefault().post(event);
    }
}
