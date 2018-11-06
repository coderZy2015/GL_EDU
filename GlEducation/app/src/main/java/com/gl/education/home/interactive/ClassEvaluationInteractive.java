package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.evaluation.event.JSGetAllActivityListEvent;
import com.gl.education.evaluation.event.JSGetLessonListByIDEvent;
import com.gl.education.evaluation.event.JSGoLivingActivityEvent;
import com.gl.education.evaluation.model.JSGetLessonListByIdBean;
import com.gl.education.evaluation.model.JSGoLivingAactivityBean;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/12.
 * 评课主页面
 */

public class ClassEvaluationInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public ClassEvaluationInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //到Activity中，列出相关lesson页面	点击触发
    @JavascriptInterface
    public void getLessonListByID(String json){
        LogUtils.d("getLessonListByID  json="+json);
        JSGetLessonListByIdBean bean = Convert.fromJson(json, JSGetLessonListByIdBean.class);
        JSGetLessonListByIDEvent event = new JSGetLessonListByIDEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //进入正在直播的Activity
    @JavascriptInterface
    public void goLivingActivity(String json){
        LogUtils.d("goLivingActivity  json="+json);
        JSGoLivingAactivityBean bean = Convert.fromJson(json, JSGoLivingAactivityBean.class);
        JSGoLivingActivityEvent event = new JSGoLivingActivityEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //获取所有的Activity列表	加载触发
    @JavascriptInterface
    public void getAllActivityList(String json){
        LogUtils.d("getAllActivityList  json="+json);
        JSGetAllActivityListEvent event = new JSGetAllActivityListEvent();
        EventBus.getDefault().post(event);
    }

}
