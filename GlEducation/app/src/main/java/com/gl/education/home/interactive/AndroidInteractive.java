package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.app.AppCommonData;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.MessageEvent;
import com.gl.education.home.event.OpenWebViewEvent;
import com.gl.education.home.event.PayMoneyEvent;
import com.gl.education.home.event.RefreshViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.home.model.JSPayInfoBean;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/6/30.
 * webview 调用 android 方法
 */

public class AndroidInteractive {

    public static final String callJs_login = "toLogin";  //告诉js登录token
    public static final String callJs_payInfo = "payStatus";  //传递支付结果

    public static final String callApp_toAliPayMoney = "_AliPayMoney";  //发起支付宝
    public static final String callApp_toWXPayMoney = "_WXPayMoney";  //发起微信


    private AgentWeb mAgentWeb;
    private Context context;
    private String eventTag;    //传入当前类的tag，来注册接收事件
    private String fragType;

    public AndroidInteractive(AgentWeb agent, Context context, String tag, String _type) {
        this.mAgentWeb = agent;
        this.context = context;
        this.eventTag = tag;
        this.fragType = _type;
    }

    //处理滑动冲突问题
    @JavascriptInterface
    public void requestEvent(boolean request){
        mAgentWeb.getWebCreator().getWebView()
                .requestDisallowInterceptTouchEvent(request);
    }

    //打开新的view来展示
    @JavascriptInterface
    public void openWebview(String json){
        if (!AppCommonData.CURRENT_TAG.equals(eventTag)){
            return;
        }
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        OpenWebViewEvent event = new OpenWebViewEvent();
        event.setMessage(eventTag);
        event.setFragType(fragType);
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        if (!AppCommonData.CURRENT_TAG.equals(eventTag)){
            return;
        }
        MessageEvent event = new MessageEvent();
        event.setMsg(eventTag);
        event.setType(fragType);
        EventBus.getDefault().post(event);
    }

    //刷新页面
    @JavascriptInterface
    public void refreshView(){
        if (!AppCommonData.CURRENT_TAG.equals(eventTag)){
            return;
        }
        RefreshViewEvent event = new RefreshViewEvent();
        event.setMessage(eventTag);
        EventBus.getDefault().post(event);
    }

    //点击购买
    @JavascriptInterface
    public void ClickToBuy(String _json) {
        String json = _json;
        LogUtils.d(""+json);
        if (json != null){

            try {
                JSPayInfoBean bean = Convert.fromJson(json, JSPayInfoBean.class);
                PayMoneyEvent event = new PayMoneyEvent();
                event.setBean(bean);
                if (bean.getOrderType().equals("2")) //支付宝
                {
                    event.setMessage(eventTag + callApp_toAliPayMoney);
                }
                else if(bean.getOrderType().equals("3"))//微信
                {
                    event.setMessage(eventTag + callApp_toWXPayMoney);
                }
                EventBus.getDefault().post(event);

            } catch (JsonIOException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

    }
}



