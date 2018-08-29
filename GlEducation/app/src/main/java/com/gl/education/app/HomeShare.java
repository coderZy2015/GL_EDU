package com.gl.education.app;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by zy on 2018/8/27.
 */

public class HomeShare {

    public static void shareTest(Activity context, UMShareListener listener){
//        new ShareAction(context)
//                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                .withText("hello")//分享内容
//                .setCallback(listener)//回调监听器
//                .share();
        new ShareAction(context).withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(listener).open();
    }
}
