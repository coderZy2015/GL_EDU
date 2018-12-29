package com.gl.education.teachingassistant.interactive;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.HomeShare;
import com.gl.education.helper.Convert;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.home.model.JSShareWebViewBean;
import com.gl.education.teachingassistant.activity.JFBookContentActivity;
import com.gl.education.teachingassistant.event.JSJFBookContentOpenWebViewEvent;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/21.
 */

public class JFBookContentInteractive {

    private AgentWeb mAgentWeb;
    private JFBookContentActivity activity;

    public JFBookContentInteractive(AgentWeb agent, Context context) {
        this.mAgentWeb = agent;
    }
    public JFBookContentInteractive(AgentWeb agent, JFBookContentActivity context) {
        this.mAgentWeb = agent;
        this.activity = context;
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJFBookContentOpenWebViewEvent event = new JSJFBookContentOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //分享页面
    @JavascriptInterface
    public void setShareData(String json){
        JSShareWebViewBean bean = Convert.fromJson(json, JSShareWebViewBean.class);
        if (bean == null){
            ToastUtils.showShort("分享失败");
            return;
        }
        HomeShare.shareWeb(activity, bean.getUrl(), bean.getTitle(), "河北教育资源云平台", new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {}

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtils.showShort("分享失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                ToastUtils.showShort("分享取消");
            }
        });
    }
}
