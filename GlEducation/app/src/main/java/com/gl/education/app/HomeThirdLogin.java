package com.gl.education.app;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by zy on 2018/8/27.
 */

public class HomeThirdLogin {

    public static void loginWX(Activity activity, Context context, UMAuthListener umAuthListener){

        UMShareAPI.get(context).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, umAuthListener);
    }
}
