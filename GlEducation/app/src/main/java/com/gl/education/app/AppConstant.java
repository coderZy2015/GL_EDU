package com.gl.education.app;

import android.os.Environment;

/**
 * Created by zy on 2018/6/30.
 */

public class AppConstant {
    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";

    //搜索后打开的网址
    public static final String search_tuijian = "http://www.eugames.cn/appnew/serchpage.html";
    public static final String search_jiaocai = "http://guanlin.gl.to3.cc/dist/#/searchJiaocai";
    public static final String search_jiaofu = "http://guanlin.gl.to3.cc/dist/#/searchJiaofu";
    public static final String search_weike = "http://guanlin.gl.to3.cc/dist/#/searchWeike";

    public static final String SP_TOKEN = "token";  //存储的token
    public static final String SP_FIRST_ENTER = "firstEnter"; //第一次登陆
    public static final String SP_SEARCH_HISTORY = "searchHistory";  //搜索历史
    public static final String SP_SEARCH_HISTORY_NUM = "searchHistoryNum";  //搜索历史数量

    public static final String callJs_login = "toLogin";  //告诉js登录token
    public static final String callJs_payInfo = "payStatus";  //传递支付结果


}
