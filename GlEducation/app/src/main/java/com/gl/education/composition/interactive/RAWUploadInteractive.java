package com.gl.education.composition.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.AppConstant;
import com.gl.education.composition.event.JSgetCompositionDataEvent;
import com.gl.education.composition.event.JSrequestComImageEvent;
import com.gl.education.composition.event.JSrequestTeacherParamEvent;
import com.gl.education.composition.event.JSsetCompositionTitleEvent;
import com.gl.education.composition.event.JSuploadCompositionEvent;
import com.gl.education.composition.model.RequestComImageBean;
import com.gl.education.composition.model.SetCompositionTitleBean;
import com.gl.education.composition.model.ShowToastBean;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.helper.Convert;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/10/19.
 */

public class RAWUploadInteractive {
    private AgentWeb mAgentWeb;
    private Context context;

    public RAWUploadInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
        this.context = context;
    }

    //app存储一个作文数据 上传成功后删除
    @JavascriptInterface
    public void getCompositionData(String json){
        LogUtils.d("getCompositionData json = "+json);
        JSgetCompositionDataEvent event = new JSgetCompositionDataEvent();
        EventBus.getDefault().post(event);
    }

    //请求一个拍照或者相册选择的图片
    @JavascriptInterface
    public void requestComImage(String json){
        LogUtils.d("requestComImage json = "+json);
        RequestComImageBean bean = Convert.fromJson(json, RequestComImageBean.class);
        JSrequestComImageEvent event = new JSrequestComImageEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //请求老师参数 打开老师列表选择
    @JavascriptInterface
    public void requestTeacherParam(String json){
        LogUtils.d("requestTeacherParam json = "+json);
        JSrequestTeacherParamEvent event = new JSrequestTeacherParamEvent();

        EventBus.getDefault().post(event);
    }

    //传参下个页面  支付
    @JavascriptInterface
    public void uploadComposition(String json){
        LogUtils.d("uploadComposition json = "+json);

        JSuploadCompositionEvent event = new JSuploadCompositionEvent();
        EventBus.getDefault().post(event);
    }

    //提示消息
    @JavascriptInterface
    public void showToast(String json){
        ShowToastBean bean = Convert.fromJson(json, ShowToastBean.class);
        ToastUtils.showShort(""+bean.getMessage());
    }

    //作文题目
    @JavascriptInterface
    public void setCompositionTitle(String json){
        LogUtils.d("setCompositionTitle json = "+json);
        SetCompositionTitleBean bean = Convert.fromJson(json, SetCompositionTitleBean.class);
        JSsetCompositionTitleEvent event = new JSsetCompositionTitleEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //作文题目
    @JavascriptInterface
    public void resetComposition(String json){
        CompositionDataManager.data.clear();
        String data = Convert.toJson(CompositionDataManager.data);
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setCompositionData,  data);
    }

}
