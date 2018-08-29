package com.gl.education.home.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSWKDropDwonEvent;
import com.gl.education.home.event.JSWKFragmentLoginEvent;
import com.gl.education.home.event.JSWKFragmentOpenWebViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.home.model.JSPayInfoBean;
import com.gl.education.teachingassistant.event.JSJFBookWXPayEvent;
import com.gl.education.teachingassistant.event.JSJFBookZFBPayEvent;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
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

    //下拉刷新
    @JavascriptInterface
    public void dropDownRefresh(){
        JSWKDropDwonEvent event = new JSWKDropDwonEvent();
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

                if (bean.getOrderType().equals("2")) //支付宝
                {
                    JSJFBookZFBPayEvent event = new JSJFBookZFBPayEvent();
                    event.setBean(bean);
                    EventBus.getDefault().post(event);
                }
                else if(bean.getOrderType().equals("3"))//微信
                {
                    JSJFBookWXPayEvent event = new JSJFBookWXPayEvent();
                    event.setBean(bean);
                    EventBus.getDefault().post(event);
                }

            } catch (JsonIOException e) {
                e.printStackTrace();
                ToastUtils.showShort("订单创建失败，请联系客服");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                ToastUtils.showShort("订单创建失败，请联系客服");
            }
        }
    }

}
