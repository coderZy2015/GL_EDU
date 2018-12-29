package com.gl.education.camera;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.CameraCoverOpenBookguidEvent;
import com.gl.education.home.event.GetCoverListDataEvent;
import com.gl.education.home.event.OpenJCChannelEvent;
import com.gl.education.home.event.OpenJFChannelEvent;
import com.gl.education.home.model.CameraCoverOpenBookBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/12/24.
 */

public class CameraCoverInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public CameraCoverInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //获得列表数据  发送给js
    @JavascriptInterface
    public void getCoverListData(String json){
        GetCoverListDataEvent event = new GetCoverListDataEvent();
        EventBus.getDefault().post(event);
    }

    //进入教材
    @JavascriptInterface
    public void trans2jc(String json){
        LogUtils.d("trans2jc");
        OpenJCChannelEvent event = new OpenJCChannelEvent();
        EventBus.getDefault().post(event);
    }

    //进入教辅
    @JavascriptInterface
    public void trans2jf(String json){
        LogUtils.d("trans2jf");
        OpenJFChannelEvent event = new OpenJFChannelEvent();
        EventBus.getDefault().post(event);
    }


    @JavascriptInterface
    public void openBook(String json){
        LogUtils.d("openBookguid json = "+json);
        CameraCoverOpenBookBean bean = Convert.fromJson(json, CameraCoverOpenBookBean.class);
        CameraCoverOpenBookguidEvent event = new CameraCoverOpenBookguidEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }
}
