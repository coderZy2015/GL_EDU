package com.gl.education.app;

/**
 * Created by zy on 2018/8/18.
 */

public class AppCommonData {
    //判断用户是否登录鉴权
    public static boolean isLogin = false;
    //当前的页面tag
    public static String CURRENT_TAG = "";
    //判断用户是否获取token
    public static boolean loginBackground = false;
    //用户的年级  0一年级
    public static int userGrade = 1;
    //推荐更多内容的ID   确保事件只会被自己的类接收
    public static int moreRecommendcontentID = 0;

    //自动更新使用
    public static int versionCode = 1;
    public static String versionName = "";

    public static boolean isShowTelephone = true;

    public static boolean isShowWeSpeak = true;
}
