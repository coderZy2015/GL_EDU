package com.gl.education.teachingmaterial.interactive;

import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.app.HomeShare;
import com.gl.education.helper.Convert;
import com.gl.education.home.event.JSJCFragmentRefreshViewEvent;
import com.gl.education.home.model.JSOpenWebViewBean;
import com.gl.education.home.model.JSShareWebViewBean;
import com.gl.education.teachingmaterial.activity.JCBookMenuActivity;
import com.gl.education.teachingmaterial.event.JSJCBookMenuLoginEvent;
import com.gl.education.teachingmaterial.event.JSJCBookMenuOpenWebViewEvent;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2018/8/20.
 */

public class JCBookMenuInteractive {
    private AgentWeb mAgentWeb;
    private JCBookMenuActivity activity;

    public JCBookMenuInteractive(AgentWeb agent, JCBookMenuActivity context) {
        this.mAgentWeb = agent;
        this.activity = context;
    }

    //需要登录
    @JavascriptInterface
    public void toLogin(){
        JSJCBookMenuLoginEvent event = new JSJCBookMenuLoginEvent();
        EventBus.getDefault().post(event);
    }

    //进入详情页面
    @JavascriptInterface
    public void openWebview(String json){
        JSOpenWebViewBean bean = Convert.fromJson(json, JSOpenWebViewBean.class);
        JSJCBookMenuOpenWebViewEvent event = new JSJCBookMenuOpenWebViewEvent();
        event.setBean(bean);
        EventBus.getDefault().post(event);
    }

    //刷新我的书架
    @JavascriptInterface
    public void refreshBookshelf(){
        JSJCFragmentRefreshViewEvent event = new JSJCFragmentRefreshViewEvent();
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
