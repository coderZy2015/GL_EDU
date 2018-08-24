package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSWKFragmentLoginEvent;
import com.gl.education.home.event.JSWKFragmentOpenWebViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class WKFragmentInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public WKFragmentInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //处理滑动冲突问题
    @JavascriptInterface
    public void requestEvent(boolean request){
        mAgentWeb.getWebCreator().getWebView()
                .requestDisallowInterceptTouchEvent(request);
    }

    //进入目录或者我的书架
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSWKFragmentOpenWebViewEvent event = new JSWKFragmentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){

        JSWKFragmentLoginEvent event = new JSWKFragmentLoginEvent();
        EventBus.getDefault().post(event);
    }

}
