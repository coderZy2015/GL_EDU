package com.gl.education.app;

import android.app.Activity;

import com.gl.education.R;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by zy on 2018/8/27.
 */

public class HomeShare {

    public static void shareWeb(Activity context, String url, String title, String description, UMShareListener listener){
//        new ShareAction(context)
//                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                .withText("hello")//分享内容
//                .setCallback(listener)//回调监听器
//                .share();

        UMImage thumb = new UMImage(context, R.mipmap.ic_launcher);

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(description);//描述

        new ShareAction(context).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(listener).open();
    }

    public static void shareWXWeb(Activity context, String url){
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
    }

}
