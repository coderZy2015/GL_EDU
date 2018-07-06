package com.gl.education.home.utlis;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.MessageEvent;
import com.gl.education.home.event.PayMoneyEvent;
import com.gl.education.home.event.ToTeachingAssistantDetailPageEvent;
import com.gl.education.home.model.JSPayInfoBean;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/6/30.
 * webview 调用 android 方法
 */

public class AndroidInterface {

    public static final String callJs_login = "toLogin";  //告诉js登录uid guid
    public static final String callJs_payInfo = "payStatus";  //传递支付结果

    private AgentWeb mAgentWeb;
    private Context context;
    private String eventTag;

    public AndroidInterface(AgentWeb agent, Context context, String tag) {
        this.mAgentWeb = agent;
        this.context = context;
        this.eventTag = tag;
    }

    //处理滑动冲突问题
    @JavascriptInterface
    public void requestEvent(boolean request){
        LogUtils.d("requestEvent");
        mAgentWeb.getWebCreator().getWebView()
                .requestDisallowInterceptTouchEvent(request);
    }

    //调到教辅目录页面
    @JavascriptInterface
    public void toTABookDetailPage(String url){
        ToTeachingAssistantDetailPageEvent event = new ToTeachingAssistantDetailPageEvent();
        event.setUrl(url);
        EventBus.getDefault().post(event);
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        LogUtils.d("toLogin");
        MessageEvent event = new MessageEvent();
        event.setMsg("login");
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
                LogUtils.d("eventTag = "+eventTag);

                if (bean.getOrderType().equals("2")){ //支付宝
                    EventBus.getDefault().post(new PayMoneyEvent(eventTag+"_AliPayMoney", bean));
                }else if(bean.getOrderType().equals("3")){//微信
                    EventBus.getDefault().post(new PayMoneyEvent(eventTag+"_WXPayMoney", bean));
                }

            } catch (JsonIOException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

    }
}



