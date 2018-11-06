package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSToUploadComposiseEvent2;
import com.gl.education.composition.event.JSgetTeacherListEvent;
import com.gl.education.composition.event.JSopenTeacherDetailEvent;
import com.gl.education.composition.model.OpenTeacherDetailBean;
import com.gl.education.composition.model.ToUploadComposiseBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/19.
 */

public class RAWTeacherListInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWTeacherListInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //
    @JavascriptInterface
    public void getTeacherListData(String json){
        LogUtils.d("getTeacherList json = "+json);
        JSgetTeacherListEvent event = new JSgetTeacherListEvent();
        EventBus.getDefault().post(event);
    }

    //
    @JavascriptInterface
    public void toUploadComposise(String json){
        LogUtils.d("toUploadComposise json = "+json);
        ToUploadComposiseBean bean = Convert.fromJson(json, ToUploadComposiseBean.class);
        JSToUploadComposiseEvent2 event = new JSToUploadComposiseEvent2();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    @JavascriptInterface
    public void openTeacherDetail(String json){
        LogUtils.d("openTeacherDetail json = "+json);
        OpenTeacherDetailBean bean = Convert.fromJson(json, OpenTeacherDetailBean.class);
        JSopenTeacherDetailEvent event = new JSopenTeacherDetailEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
