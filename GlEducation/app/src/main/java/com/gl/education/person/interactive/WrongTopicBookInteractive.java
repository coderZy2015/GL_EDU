package com.gl.education.person.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;

/**
 * Created by zy on 2019/1/4.
 */
public class WrongTopicBookInteractive {

    private AgentWeb mAgentWeb;
    private Context context;

    public WrongTopicBookInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void openCamera(){
       LogUtils.d("js  openCamera");
    }

}
