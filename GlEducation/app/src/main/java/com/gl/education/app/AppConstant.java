package com.gl.education.app;

import android.os.Environment;

/**
 * Created by zy on 2018/6/30.
 */

public class AppConstant {
    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";

    //上传作文
    public static final String UPLOAD_COMPOSITION = "http://zuowen.hebeijiaoyu.com.cn/glwz/zwpd/app/fileupload";

    public static final String test_token = "gl_token_test_token_123456";

    //天恒的固定网址
    public static final String TH_BASE_URL = "http://appstuweb.hebeijiaoyu.cn/";

    //正式
    public static final String YY_WEB_BASE_URL = "http://appweb.hebeijiaoyu.cn/";
    //测试
    //public static final String YY_WEB_BASE_URL = "http://192.168.31.199:9999/";

    //个人中心 - 我的教材
    public static final String TH_MY_JIAOCAI = "http://appstuweb.hebeijiaoyu.cn/#/jcwdsj";
    //个人中心 - 我的教辅
    public static final String TH_MY_JIAOFU = "http://appstuweb.hebeijiaoyu.cn/#/wdsj";
    //个人中心 - 我的微课
    public static final String TH_MY_WEIKE = "http://appstuweb.hebeijiaoyu.cn/#/tblist";

    //拍封面
    public static final String YY_WEB_CAMERA_COVER = YY_WEB_BASE_URL+"searchCoverpage.html";
    //扫ISBN访问地址
    public static final String ISBN_URL = "http://appapi.hebeijiaoyu.cn/index.php/jiaofu/isbnToJiaofu/isbn/";
    //扫码访问地址
    public static final String SAOMA_URL = "http://appapi.hebeijiaoyu.cn/index.php/jiaofu/barcodeToJfVideo/barcode/";
    //充值中心地址
    public static final String TH_RECHARGE_URL = "http://appstuweb.hebeijiaoyu.cn/#/dborder";
    //进入详解地址
    public static final String CAMERA_EXPLAIN = "http://appstuweb.hebeijiaoyu.cn/#/quedetail";

    //搜索后打开的网址
    public static final String search_tuijian = "http://appweb.hebeijiaoyu.cn/serchpage.html";
    public static final String search_jiaocai = TH_BASE_URL+"#/searchJiaocai";
    public static final String search_jiaofu = TH_BASE_URL+"#/searchJiaofu";
    public static final String search_weike = TH_BASE_URL+"#/searchWeike";

    //个人中心头像图片地址
    public static final String downloadUrl = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";
    //错题本
    public static final String ctb_url = "http://ctb.hebeijiaoyu.com.cn/glwz-ctb/mainMobCtb/index/";
    //个人中心头像图片地址
    public static final String Camera_search_url = "http://jiaofu-image.oss-cn-qingdao.aliyuncs.com";

    //推荐首页
    public static final String YY_WEB_RECOMMEND = YY_WEB_BASE_URL+"recommond.html";
    //推荐详情
    public static final String YY_WEB_DETAIL = YY_WEB_BASE_URL+"detail.html";
    //分享的详情
    public static final String YY_WEB_SHARE = YY_WEB_BASE_URL+"share.html";
    //推荐详情评论
    public static final String YY_WEB_COMMENT = YY_WEB_BASE_URL+"comment.html";
    //推荐我的收藏
    public static final String YY_WEB_MY_FAVORITE = YY_WEB_BASE_URL+"myFavorite.html";

    //作文首页
    public static final String YY_WEB_COMPOSITION = YY_WEB_BASE_URL+"RAW_Main.html";
    //教师履历
    public static final String YY_WEB_COMPOSITION_TEACHER_DETAIL = YY_WEB_BASE_URL+"RAW_TeacherDetail.html";
    //热点素材
    public static final String YY_WEB_COMPOSITION_RAW_HOTCOLUMN = YY_WEB_BASE_URL+"RAW_HotColumn.html";
    //满分作文
    public static final String YY_WEB_COMPOSITION_RAW_FULLSCORECOLUMN = YY_WEB_BASE_URL+"RAW_FullScoreColumn.html";
    //满分作文2
    public static final String YY_WEB_COMPOSITION_RAW_CASECOLUMN = YY_WEB_BASE_URL+"RAW_CaseColumn.html";
    //文章列表
    public static final String YY_WEB_COMPOSITION_RAW_LIST = YY_WEB_BASE_URL+"RAW_List.html";
    //文章详情
    public static final String YY_WEB_COMPOSITION_RAW_DETAIL = YY_WEB_BASE_URL+"RAW_Detail.html";
    //教师列表
    public static final String YY_WEB_COMPOSITION_RAW_TEACHERLIST = YY_WEB_BASE_URL+"RAW_TeacherList.html";
    //上传作文
    public static final String YY_WEB_COMPOSITION_RAW_UPLOAD = YY_WEB_BASE_URL+"RAW_Upload.html";
    //支付界面
    public static final String YY_WEB_COMPOSITION_RAW_PAY = YY_WEB_BASE_URL+"RAW_Pay.html";

    //我的作文详情
    public static final String YY_WEB_COMPOSITION_MINE = YY_WEB_BASE_URL+"RAW_Result.html";

    //评课首页
    public static final String YY_WEB_WSPK = YY_WEB_BASE_URL+"wspk/WSPK_Main.html";
    //直播
    public static final String YY_WEB_WSPK_LIVING = YY_WEB_BASE_URL+"wspk/WSPK_Living.html";
    //课程列表
    public static final String YY_WEB_WSPK_LESSONLIST = YY_WEB_BASE_URL+"wspk/WSPK_LessonList.html";
    //课程详情
    public static final String YY_WEB_WSPK_LESSON = YY_WEB_BASE_URL+"wspk/WSPK_Lesson.html";
    //活动通知
    public static final String YY_WEB_WSPK_ANNOUNCE = YY_WEB_BASE_URL+"wspk/WSPK_Announce.html";


    //传统文化首页
    public static final String YY_WEB_CTWH_MAIN = YY_WEB_BASE_URL+"tsg/TSG_Main.html";




    //SP指本地存储
    public static final String SP_TOKEN = "token";  //存储的token
    public static final String SP_FIRST_ENTER = "firstEnter"; //第一次登陆
    public static final String SP_SEARCH_HISTORY = "searchHistory";  //搜索历史
    public static final String SP_SEARCH_HISTORY_NUM = "searchHistoryNum";  //搜索历史数量
    public static final String SP_RECOMMEND_ISLIKE = "recommend_channel_isLike_id";  //该频道是否点赞   1点赞0未点赞
    public static final String SP_RECOMMEND_COLLECTION = "recommend_channel_isCollection_id";  //该频道是否收藏   1收藏0未收藏

    //天恒
    public static final String callJs_login = "toLogin";  //告诉js登录token
    public static final String callJs_payInfo = "payStatus";  //传递支付结果
    public static final String callJs_getShareData = "getShareData";  //获取分享数据

    //易游
    public static final String callJs_setNewsData = "setNewsData";  //告诉js推荐详情数据
    public static final String callJs_loadData = "loadData";  //告诉js推荐首页数据
    public static final String callJs_setCommentsData = "setCommentsData";  //告诉js推荐评论数据
    public static final String callJs_CollectionData = "loadData";  //告诉js推荐评论数据
    public static final String callJs_moreCommentsData = "setNewsRecommondData";  //告诉js五条相关数据


    public static final String callJs_setTeacherListData = "setTeacherListData";  //阅读与写作 告诉js教师信息列表
    public static final String callJs_setTeacherNewsListData = "loadData";  //阅读与写作 告诉js教师频道新闻列表
    public static final String callJs_setTeacherDetailData = "setTeacherDetailData";  //阅读与写作 告诉js教师数据
    public static final String callJs_setHotColumn = "setHotColumn";  //阅读与写作 告诉js热点素材数据
    public static final String callJs_setFullScoreColumn = "setFullScoreColumn";  //阅读与写作 告诉js满分作文数据
    public static final String callJs_setCaseColumn = "setCaseColumn";  //阅读与写作 告诉js批改案例数据
    public static final String callJs_setList = "setList";  //阅读与写作 告诉js文章列表数据
    public static final String callJs_setArticleContent = "setArticleContent";  //阅读与写作 告诉js文章列表数据
    public static final String callJs_setPayParam = "setPayParam";  //阅读与写作 告诉js支付数据

    public static final String callJs_setCommentData = "setCommentData";  //阅读与写作 个人中心

    public static final String callJs_setCoverListData = "setCoverListData";  //拍封面的数据


    public static final String callJs_setCompositionData = "setCompositionData";  //阅读与写作 作文数据
    public static final String callJs_saddComImage = "addComImage";  //阅读与写作 添加图片


    public static final String callJs_setActivityList = "setList";//返回内容为所有Activity列表 按照正在直播 将要直播 已经直播的顺序排序后返回给页面
    public static final String callJs_setLessonList = "setList";//返回内容为所有Lesson列表 按照正在直播 将要直播 已经直播的顺序排序后返回给页面
    public static final String callJs_currentActivity = "setActivity";//返回当前直播的Activity  服务器数据
    public static final String callJs_setPlayingActivity = "setPlayingActivity";//返回当前直播的Activity
    public static final String callJs_setLessonData = "setLessonData";//置lesson数据服务器数据url:string
    public static final String callJs_setActivity = "setActivity";//设置Activity服务器数据


}
