package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSGetTeacherDetailDataEvent;
import com.gl.education.composition.event.JSToUploadComposiseEvent;
import com.gl.education.composition.model.ToUploadComposiseBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/18.
 */

public class TeacherDetailInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public TeacherDetailInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获取教师信息
    @JavascriptInterface
    public void getTeacherDetailData(String json){
        LogUtils.d("getTeacherDetailData json = "+json);
        JSGetTeacherDetailDataEvent event = new JSGetTeacherDetailDataEvent();
        EventBus.getDefault().post(event);
    }

    //评语 作文 按钮
    @JavascriptInterface
    public void toUploadComposise(String json){
        LogUtils.d("toUploadComposise json = "+json);
        ToUploadComposiseBean bean = Convert.fromJson(json, ToUploadComposiseBean.class);
        JSToUploadComposiseEvent event = new JSToUploadComposiseEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
