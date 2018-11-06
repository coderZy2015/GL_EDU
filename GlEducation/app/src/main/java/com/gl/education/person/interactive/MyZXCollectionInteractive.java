package com.gl.education.person.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSAPPGetZXCollectionEvent;
import com.gl.education.home.event.JSAPPOpenZxWebViewEvent;
import com.gl.education.home.model.JSRecommentBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/30.
 */

public class MyZXCollectionInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public MyZXCollectionInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //app获取数据
    @JavascriptInterface
    public void requestFavoriteListData(String json){
        JSAPPGetZXCollectionEvent event = new JSAPPGetZXCollectionEvent();
        EventBus.getDefault().post(event);
    }


    @JavascriptInterface
    public void openNewsView(String json){
        //LogUtils.d("json = "+json);
        JSRecommentBean bean = Convert.fromJson(json, JSRecommentBean.class);
        JSAPPOpenZxWebViewEvent event = new JSAPPOpenZxWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

}
