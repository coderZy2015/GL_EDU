package com.gl.education.app;

import android.os.Environment;

/**
 * Created by zy on 2018/6/30.
 */

public class AppConstant {
    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";

    public static final String test_token = "gl_token_test_token_123456";

    //天恒的固定网址
    public static final String TH_BASE_URL = "http://appstuweb.hebeijiaoyu.cn/";

    //个人中心 - 我的教材
    public static final String TH_MY_JIAOCAI = "http://appstuweb.hebeijiaoyu.cn/#/jcwdsj";
    //个人中心 - 我的教辅
    public static final String TH_MY_JIAOFU = "http://appstuweb.hebeijiaoyu.cn/#/wdsj";
    //个人中心 - 我的微课
    public static final String TH_MY_WEIKE = "http://appstuweb.hebeijiaoyu.cn/#/tblist";

    //扫ISBN访问地址
    public static final String ISBN_URL = "http://appapi.hebeijiaoyu.cn/index.php/jiaofu/isbnToJiaofu/isbn/";
    //扫码访问地址
    public static final String SAOMA_URL = "http://appapi.hebeijiaoyu.cn/index.php/jiaofu/barcodeToJfVideo/barcode/";
    //充值中心地址
    public static final String TH_RECHARGE_URL = "http://appstuweb.hebeijiaoyu.cn/#/dborder";
    //进入详解地址
    public static final String CAMERA_EXPLAIN = "http://appstuweb.hebeijiaoyu.cn/#/quedetail";

    //搜索后打开的网址
    public static final String search_tuijian = "http://www.eugames.cn/appnew/serchpage.html";
    public static final String search_jiaocai = TH_BASE_URL+"#/searchJiaocai";
    public static final String search_jiaofu = TH_BASE_URL+"#/searchJiaofu";
    public static final String search_weike = TH_BASE_URL+"#/searchWeike";

    //个人中心头像图片地址
    public static final String downloadUrl = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";
    //错题本
    public static final String ctb_url = "http://ctb.hebeijiaoyu.com.cn/glwz-ctb/mainMobCtb/index/";
    //个人中心头像图片地址
    public static final String Camera_search_url = "http://jiaofu-image.oss-cn-qingdao.aliyuncs.com";


    //正式
    //public static final String YY_WEB_BASE_URL = "http://appweb.hebeijiaoyu.cn/";
    //测试192.168.1.71:9999/detail.html
    public static final String YY_WEB_BASE_URL = "http://192.168.1.71:9999/";
    //推荐首页
    public static final String YY_WEB_RECOMMEND = YY_WEB_BASE_URL+"recommond.html";
    //推荐详情
    public static final String YY_WEB_DETAIL = YY_WEB_BASE_URL+"detail.html";
    //推荐详情评论
    public static final String YY_WEB_COMMENT = YY_WEB_BASE_URL+"comment.html";
    //推荐详情评论
    public static final String YY_WEB_MY_FAVORITE = YY_WEB_BASE_URL+"myFavorite.html";


    public static final String SP_TOKEN = "token";  //存储的token
    public static final String SP_FIRST_ENTER = "firstEnter"; //第一次登陆
    public static final String SP_SEARCH_HISTORY = "searchHistory";  //搜索历史
    public static final String SP_SEARCH_HISTORY_NUM = "searchHistoryNum";  //搜索历史数量

    //天恒
    public static final String callJs_login = "toLogin";  //告诉js登录token
    public static final String callJs_payInfo = "payStatus";  //传递支付结果

    //易游
    public static final String callJs_setNewsData = "setNewsData";  //告诉js推荐详情数据
    public static final String callJs_loadData = "loadData";  //告诉js推荐首页数据
    public static final String callJs_setCommentsData = "setCommentsData";  //告诉js推荐评论数据
    public static final String callJs_CollectionData = "loadData";  //告诉js推荐评论数据


}
