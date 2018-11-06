package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.composition.event.JSComGetTeacherListData;
import com.gl.education.composition.event.JSComGetTeacherNewsListData;
import com.gl.education.composition.event.JSComOpenTeacherDetail;
import com.gl.education.composition.event.JSComToUploadComposise;
import com.gl.education.composition.event.JSCompositionDropDownEvent;
import com.gl.education.composition.event.JSCompositionLoadMoreEvent;
import com.gl.education.composition.event.JSCompositionOpenNewsViewEvent;
import com.gl.education.composition.event.JSEnterSubColumnEvent;
import com.gl.education.composition.model.EnterSubColumnBean;
import com.gl.education.composition.model.OpenTeacherDetailBean;
import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSRecommentBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/12.
 */

public class CompositionInterative {
    private AgentWeb mAgentWeb;
    private Context context;

    public CompositionInterative(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获取教师列表
    @JavascriptInterface
    public void getTeacherListData(String json){
        LogUtils.d("getTeacherListData "+json);
        JSComGetTeacherListData event = new JSComGetTeacherListData();
        EventBus.getDefault().post(event);
    }

    //获取教师最新消息列表
    @JavascriptInterface
    public void requestListData(String json){
        LogUtils.d("requestListData "+json);
        JSComGetTeacherNewsListData event = new JSComGetTeacherNewsListData();
        EventBus.getDefault().post(event);
    }

    //打开教师内容
    @JavascriptInterface
    public void openTeacherDetail(String json){
        LogUtils.d("openTeacherDetail "+json);
        OpenTeacherDetailBean bean = Convert.fromJson(json, OpenTeacherDetailBean.class);
        JSComOpenTeacherDetail event = new JSComOpenTeacherDetail();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //上传
    @JavascriptInterface
    public void toUploadComposise(String json){
        LogUtils.d("toUploadComposise "+json);
        JSComToUploadComposise event = new JSComToUploadComposise();
        EventBus.getDefault().post(event);
    }

    //跳转
    @JavascriptInterface
    public void enterSubColumn(String json){
        LogUtils.d("enterSubColumn "+json);
        EnterSubColumnBean bean = Convert.fromJson(json, EnterSubColumnBean.class);
        JSEnterSubColumnEvent event = new JSEnterSubColumnEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    @JavascriptInterface
    public void openNewsView(String json){
        JSRecommentBean bean = Convert.fromJson(json, JSRecommentBean.class);
        JSCompositionOpenNewsViewEvent event = new JSCompositionOpenNewsViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //下拉刷新
    @JavascriptInterface
    public void dropDownRefresh(){
        //LogUtils.d("Composition dropDownRefresh");
        JSCompositionDropDownEvent event = new JSCompositionDropDownEvent();
        EventBus.getDefault().post(event);
    }

    //上拉加载
    @JavascriptInterface
    public void pullUpLoading(){
       // LogUtils.d("Composition pullUpLoading");
        JSCompositionLoadMoreEvent event = new JSCompositionLoadMoreEvent();
        EventBus.getDefault().post(event);
    }

    //处理滑动冲突问题
    @JavascriptInterface
    public void tapStart(){
        mAgentWeb.getWebCreator().getWebView()
                .requestDisallowInterceptTouchEvent(true);
    }

    //处理滑动冲突问题
    @JavascriptInterface
    public void tapEnd(){
        mAgentWeb.getWebCreator().getWebView()
                .requestDisallowInterceptTouchEvent(false);
    }

}
