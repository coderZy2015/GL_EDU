package com.gl.education.evaluation.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.evaluation.event.JSGetPlayingActivityEvent;
import com.gl.education.evaluation.event.JSLivingGetAllActivityListEvent;
import com.gl.education.evaluation.event.JSLivingGetLessonListByIDEvent;
import com.gl.education.evaluation.model.JSGetLessonListByIdBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/15.
 */

public class WSPKLivingInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WSPKLivingInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获取当前直播的Activity
    @JavascriptInterface
    public void getPlayingActivity(String json){
        LogUtils.d("getPlayingActivity  json="+json);
        JSGetPlayingActivityEvent event = new JSGetPlayingActivityEvent();
        EventBus.getDefault().post(event);
    }

    //进到Activity中，列出相关lesson页面  点击触发
    @JavascriptInterface
    public void getLessonListByID(String json){
        LogUtils.d("getLessonListByID  json="+json);
        JSGetLessonListByIdBean bean = Convert.fromJson(json, JSGetLessonListByIdBean.class);
        JSLivingGetLessonListByIDEvent event = new JSLivingGetLessonListByIDEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //获取所有的Activity列表  加载触发
    @JavascriptInterface
    public void getAllActivityList(String json){
        LogUtils.d("getAllActivityList  json="+json);
        JSLivingGetAllActivityListEvent event = new JSLivingGetAllActivityListEvent();
        EventBus.getDefault().post(event);
    }


}
