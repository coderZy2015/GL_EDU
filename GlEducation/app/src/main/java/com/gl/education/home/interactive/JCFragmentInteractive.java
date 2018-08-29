package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJCDropDownEvent;
import com.gl.education.home.event.JSJCFragmentOpenWebViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 * 教材fragment的交互类
 */

public class JCFragmentInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JCFragmentInteractive(AgentWeb agent, Context context) {
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
        JSJCFragmentOpenWebViewEvent event = new JSJCFragmentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //下拉刷新
    @JavascriptInterface
    public void dropDownRefresh(){
        JSJCDropDownEvent event = new JSJCDropDownEvent();
        EventBus.getDefault().post(event);
    }


}
