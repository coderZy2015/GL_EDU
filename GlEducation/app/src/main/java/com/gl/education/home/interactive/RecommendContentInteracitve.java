package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.app.AppCommonData;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSGotoCommentEvent;
import com.gl.education.home.event.JSOpenOtherNewsViewEvent;
import com.gl.education.home.event.JSRecommentContentGetDataEvent;
import com.gl.education.home.event.JSRequestCommentListDataEvent;
import com.gl.education.home.event.JSRequestNewsRecommondDataEvent;
import com.gl.education.home.model.JSAppGetRecommendData;
import com.gl.education.home.model.JSRecommentBean;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/25.
 */

public class RecommendContentInteracitve {
    private AgentWeb mAgentWeb;
    private Context context;
    private int type;

    public RecommendContentInteracitve(AgentWeb agent, Context context, int _type) {
        this.mAgentWeb = agent;
        this.context = context;
        type = _type;
        //LogUtils.d("type = "+type);
    }

    //app获取数据
    @JavascriptInterface
    public void getNewsData(String json){
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        //LogUtils.d("getNewsData type = "+type+"   json = "+json);
        JSAppGetRecommendData bean = Convert.fromJson(json, JSAppGetRecommendData.class);
        JSRecommentContentGetDataEvent event = new JSRecommentContentGetDataEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }


    //app获取数据
    @JavascriptInterface
    public void requestNewsRecommondData(String json){
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        //LogUtils.d("requestNewsRecommondData type = "+type+"   json = "+json);
        JSAppGetRecommendData bean = Convert.fromJson(json, JSAppGetRecommendData.class);
        JSRequestNewsRecommondDataEvent event = new JSRequestNewsRecommondDataEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //app获取数据
    @JavascriptInterface
    public void gotoComment(String json){
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        JSGotoCommentEvent event = new JSGotoCommentEvent();
        EventBus.getDefault().post(event);
    }


    //app获取数据
    @JavascriptInterface
    public void requestCommentListData(String json){
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        LogUtils.d("requestCommentListData "+"   json = "+json);
        JSAppGetRecommendData bean = Convert.fromJson(json, JSAppGetRecommendData.class);
        JSRequestCommentListDataEvent event = new JSRequestCommentListDataEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    @JavascriptInterface
    public void openNewsView(String json){
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        //LogUtils.d("openNewsView type = "+type+"   json = "+json);
        JSRecommentBean bean = Convert.fromJson(json, JSRecommentBean.class);
        JSOpenOtherNewsViewEvent event = new JSOpenOtherNewsViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

}
