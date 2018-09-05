package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSRecommendDropDownEvent;
import com.gl.education.home.event.JSRecommendLoadMoreEvent;
import com.gl.education.home.event.JSRecommentOpenWebViewEvent;
import com.gl.education.home.event.JSRecommentRequest;
import com.gl.education.home.model.JSRecommentBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/25.
 */

public class RecommendInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public RecommendInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }


    @JavascriptInterface
    public void openNewsView(String json){
        //LogUtils.d("json = "+json);
        JSRecommentBean bean = Convert.fromJson(json, JSRecommentBean.class);
        JSRecommentOpenWebViewEvent event = new JSRecommentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }


    @JavascriptInterface
    public void requestRecommondListData(String json){
        //LogUtils.d("requestRecommondListData");

        JSRecommentRequest event = new JSRecommentRequest();
        EventBus.getDefault().post(event);
    }

    //下拉刷新
    @JavascriptInterface
    public void dropDownRefresh(){
        LogUtils.d("dropDownRefresh");
        JSRecommendDropDownEvent event = new JSRecommendDropDownEvent();
        EventBus.getDefault().post(event);
    }

    //上拉加载
    @JavascriptInterface
    public void pullUpLoading(){
        LogUtils.d("pullUpLoading");
        JSRecommendLoadMoreEvent event = new JSRecommendLoadMoreEvent();
        EventBus.getDefault().post(event);
    }

}
