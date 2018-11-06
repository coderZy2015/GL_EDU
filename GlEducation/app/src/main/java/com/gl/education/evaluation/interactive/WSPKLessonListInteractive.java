package com.gl.education.evaluation.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.evaluation.event.JSGOLessonDeatailEvent;
import com.gl.education.evaluation.event.JSGetAllLessonListEvent;
import com.gl.education.evaluation.event.JSGetSelectedActivityInfoEvent;
import com.gl.education.evaluation.event.JSShowAnnounceEvent;
import com.gl.education.evaluation.model.JSGOLessonDetailBean;
import com.gl.education.evaluation.model.JSShowAnnounceBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/16.
 */

public class WSPKLessonListInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WSPKLessonListInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进到课程的具体信息页面	点击触发  G
    @JavascriptInterface
    public void goLessonDetail(String json){
        LogUtils.d("goLessonDetail  json="+json);
        JSGOLessonDetailBean bean = Convert.fromJson(json, JSGOLessonDetailBean.class);
        JSGOLessonDeatailEvent event = new JSGOLessonDeatailEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //获取当前Activity所有课程列表		加载触发 G
    @JavascriptInterface
    public void getAllLessonList(String json){
        LogUtils.d("getAllLessonList  json="+json);
        JSGetAllLessonListEvent event = new JSGetAllLessonListEvent();
        EventBus.getDefault().post(event);
    }

    //获取选中的Activity信息	加载触发 G
    @JavascriptInterface
    public void getSelectedActivityInfo(String json){
        LogUtils.d("getSelectedActivityInfo  json="+json);
        JSGetSelectedActivityInfoEvent event = new JSGetSelectedActivityInfoEvent();
        EventBus.getDefault().post(event);
    }

    //进到课程的具体信息页面	点击触发  G
    @JavascriptInterface
    public void showAnnounce(String json){
        LogUtils.d("showAnnounce  json="+json);
        JSShowAnnounceBean bean = Convert.fromJson(json, JSShowAnnounceBean.class);
        JSShowAnnounceEvent event = new JSShowAnnounceEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

}
