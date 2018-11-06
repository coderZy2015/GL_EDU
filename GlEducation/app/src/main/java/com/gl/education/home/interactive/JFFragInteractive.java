package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJFDropDownEvent;
import com.gl.education.home.event.JSJFFragmentOpenWebViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 * 教辅fragment的交互类
 */

public class JFFragInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public JFFragInteractive(AgentWeb agent, Context context) {
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
        JSJFFragmentOpenWebViewEvent event = new JSJFFragmentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
    //下拉刷新
    @JavascriptInterface
    public void dropDownRefresh(){
        JSJFDropDownEvent event = new JSJFDropDownEvent();
        EventBus.getDefault().post(event);
    }

}
