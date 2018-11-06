package com.gl.education.app;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.List;

/**
 * Created by zy on 2018/6/5.
 */

public class HomeAPI {

    //基础
    //public static final String BASE_URL = "http://a1guanlin.eugames.cn/";
    //beta版
    //public static final String BASE_URL = "http://appserbeta.hebeijiaoyu.cn/";
    //正式
    public static final String BASE_URL = "http://appser.hebeijiaoyu.cn/";

    //header
    public static String HTTP_HEADER = "GL-Token";
    //token
    public static String HTTP_TOKEN = "";

    //申请token
    private static String APPLY_TOKEN_URL = BASE_URL + "iclient/clipublics/applyToken";
    //token申请
    private static String LOGIN_AUTH_URL = BASE_URL + "iclient/clipublics/loginAuth";
    //登录
    private static String LOGIN_URL = BASE_URL + "iclient/clipublics/login";
    //验证码
    private static String IDENTIFY_CODE_URL = BASE_URL + "iclient/clipublics/sendCode";
    //注册
    private static String REGISTER_URL = BASE_URL + "iclient/clipublics/regist";
    //重置密码
    private static String REPEAT_PASSWORD_URL = BASE_URL + "iclient/clipublics/resetPassword";
    //获取支付宝预订单
    private static String ALIPAY_UNIFIEDORDER_URL = BASE_URL + "iclient/cliuser/unifiedorderAlipay";
    //获取微信预订单
    private static String WX_UNIFIEDORDER_URL = BASE_URL + "iclient/cliuser/unifiedorderWechat";
    //代币数量
    private static String DB_AMOUNT_URL = BASE_URL + "iclient/cliuser/dbAmount";
    //获取全频道信息
    private static String GET_ALL_CHANNEL_URL = BASE_URL + "iclient/clicenter/getAllChannel";
    //当前用户的年级频道信息
    private static String GET_USER_CHANNEL_GRADE_URL = BASE_URL + "iclient/clicenter/getUserChannelGrade";
    //修改用户的年级频道信息
    private static String SET_USER_CHANNEL_GRADE_URL = BASE_URL + "iclient/clicenter/setUserChannelGrade";
    //我的频道标记列表
    private static String GET_CHANNEL_FLAG = BASE_URL + "iclient/clicenter/getChannelFlag";
    //设置我的频道标记位
    private static String SET_CHANNEL_FLAG = BASE_URL + "iclient/clicenter/setChannelFlag";

    //扣代币接口
    private static String DB_DEDUCT = BASE_URL + "iclient/cliuser/dbDeduct";

    //获取个人中心主页信息
    private static String GET_USER_CENTER_HOME_URL = BASE_URL + "iclient/clicenter/getUserCenterHome";
    //获取个人资料设置页信息
    private static String GET_USER_PROFILE_URL = BASE_URL + "iclient/clicenter/getUserProfile";
    //个人资料设置页保存信息
    private static String SET_USER_PROFILE_URL = BASE_URL + "iclient/clicenter/setUserProfile";
    //获取个人消息列表
    private static String GET_USER_MSG_URL = BASE_URL + "iclient/clicenter/getUserMsg";
    //设置个人消息已读
    private static String SET_USER_MSG_URL = BASE_URL + "iclient/clicenter/setUserMsg";
    //我的收藏
    private static String GET_USER_FAVORITE_URL = BASE_URL + "iclient/clicenter/getUserFavorite";
    //token身份识别
    private static String TOKEN_IDENTIFY_URL = BASE_URL + "iclient/clipublics/tokenIdentify";

    //退出当前账号
    private static String LOGOUT_URL = BASE_URL + "iclient/clipublics/logout";
    //推荐页首页(old)
    private static String GET_RECOMHOME_URL = BASE_URL + "iclient/clirecom/getRecomHome";
    //推荐页首页
    private static String GET_RECOM_BY_USER_TAG_URL = BASE_URL +
            "iclient/clirecom/getRecomByUserTag";
    //查看某篇文章
    private static String GET_ARTICLE_URL = BASE_URL + "iclient/clipublics/getArticle";
    //文章转发成功回调
    private static String ARTICLE_SHARE_CALLBACK_URL = BASE_URL +
            "iclient/Clirecom/articleShareCallback";
    //获取文章的推荐列表
    private static String GET_ARTICLE_RECOM = BASE_URL + "iclient/clirecom/getArticleRecom";


    //获取文章的点赞数，访问量，评论数等
    private static String GET_ARTICLE_INFO_URL = BASE_URL + "iclient/Clirecom/getArticleInfo";

    //点赞/取消点赞某篇文章
    private static String GET_ARTICLE_LIKE_URL = BASE_URL + "iclient/clirecom/setArticleLikes";
    //浏览了某篇文章
    private static String GET_ARTICLE_VISITOR_URL = BASE_URL + "iclient/clirecom/setArticleVisitors";
    //收藏/取消收藏某篇文章
    private static String SET_ARTICLE_FAVORITE_URL = BASE_URL + "iclient/clirecom/setArticleFavorite";
    //获取评论
    private static String GET_ARTICLE_COMMENTS_URL = BASE_URL + "iclient/clirecom/getArticleComments";
    //发表评论
    private static String SET_ARTICLE_COMMENTS_URL = BASE_URL + "iclient/clirecom/setArticleComments";
    //发表评论getHotKey
    private static String GET_HOTKEY_URL = BASE_URL + "iclient/Clirecom/getHotKey";
    //根据关键字搜索文章
    private static String SEARCH_BY_KEYWORD_URL = BASE_URL + "iclient/Clirecom/searchByKeyword";
    //上传头像
    private static String SET_USER_AVATAR = BASE_URL + "iclient/clicenter/setUserAvatar";
    //微信注册绑定
    private static String REGIST_WECHAT = BASE_URL + "iclient/clipublics/registWechat";
    //微信unid检查
    private static String CHECK_WECHAT = BASE_URL + "iclient/clipublics/checkWechat";
    //我的订单流水
    private static String GET_ORDER_RECORD = BASE_URL + "iclient/clicenter/getOrderRecord";
    //删除我的用户
    private static String DELETE_USER = BASE_URL + "iclient/clipublics/delUser";


    //查看是否有权限看题目(暂时无用)
    private static String HAS_QUES_DETAIL_AUTH = BASE_URL + "iclient/cliuser/hasQuesDetailAuth";

    //获得我的作文列表
    private static String GET_COMPOSITION_LIST = BASE_URL + "iclient/clicenter/getCompositionList";
    //获得目录列表
    private static String GET_CATALOG = BASE_URL + "iclient/clirw/getCatalog";
    //获得教师列表
    private static String GET_TEACHER_LIST = BASE_URL + "iclient/clirw/getTeacherList";
    //获得素材列表
    private static String GET_LIST_BY_CATALOG = BASE_URL + "iclient/clirw/getListByCatalog";
    //获得素材详情
    private static String GET_DETAIL = BASE_URL + "iclient/clirw/getDetail";
    //获得作文批改预下单号
    private static String GET_PREORDER = BASE_URL + "iclient/clirw/preorder";
    //作文上传图片至OSS
    private static String UPLOAD_RW_OSS = BASE_URL + "iclient/clirw/uploadRWImage";
    //作文图片上传
    private static String COMPOSITION_FILE_UPLOAD = "http://zuowen.hebeijiaoyu.com.cn/glwz/zwpd/app/fileupload";
    //作文批改预下单
    private static String UPLOAD_CALLBACK = BASE_URL + "iclient/clirw/uploadCallback";
    //作文订单支付完成回调
    private static String AFTER_PAY_CALLBACK = BASE_URL + "iclient/clirw/afterPayCallback";





    //获取所有活动
    private static String GET_ACTIVITY = BASE_URL + "iclient/clilive/getActivity";
    //获取某个活动的lesson
    private static String GET_LESSON_BY_ID = BASE_URL + "iclient/clilive/getLessonByID";
    //获取某个活动的直播信息
    private static String GET_ACTIVITY_LIVING_BY_ID = BASE_URL + "iclient/clilive/getActivityLivingByID";
    //删除评课缓存
    private static String REMOVE_LIVE_CACHE = BASE_URL + "iclient/clilive/removeLiveCache";
    //记录某个活动的人次信息
    private static String ADD_COUNT_BY_ID = BASE_URL + "iclient/clilive/addCountByID";



    //获取当前版本号
    private static String GET_CRT_VERSION = BASE_URL + "iclient/clipublics/getCrtVersion";
    //修改当前版本号
    private static String UPDATE_CRT_VERSION = BASE_URL + "iclient/clipublics/updateCrtVersion";

    /**
     * 申请token
     *
     * @param callback
     * @param <T>
     */
    public static <T> void test(JsonCallback<T> callback) {

        OkGo.<T>get("http://apigl.gl.to3.cc/index.php/api/5b78192ea1143/quesid/184498")
                .execute(callback);
    }

    /**
     * 申请token
     *
     * @param deviceid 设备号  取不到会随机  获取到token后会上传到header里
     * @param callback
     * @param <T>
     */
    public static <T> void applyToken(String deviceid, JsonCallback<T> callback) {

        OkGo.<T>post(APPLY_TOKEN_URL)
                .params("deviceid", deviceid)
                .execute(callback);
    }

    /**
     * token登录
     *
     * @param callback
     * @param <T>
     */
    public static <T> void loginAuth(JsonCallback<T> callback) {

        OkGo.<T>get(LOGIN_AUTH_URL)
                .execute(callback);
    }


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback
     * @param <T>
     */
    public static <T> void login(String username, String password, JsonCallback<T> callback) {
        password = EncryptUtils.encryptMD5ToString(password);
        OkGo.<T>post(LOGIN_URL)
                .params("username", username)
                .params("password", password)
                .execute(callback);
    }

    /**
     * 发送验证码
     *
     * @param username 用户名
     * @param func     func的值为 'regist' 或者 'resetpassword'
     * @param callback
     * @param <T>
     */
    public static <T> void sendIdentifyCode(String username, String func, JsonCallback<T>
            callback) {
        OkGo.<T>post(IDENTIFY_CODE_URL)
                .params("username", username)
                .params("func", func)
                .execute(callback);
    }

    /**
     * 注册
     *
     * @param username  用户名
     * @param password  密码
     * @param checkcode 验证码
     * @param callback
     * @param <T>
     */
    public static <T> void register(String username, String password, String checkcode,
                                    JsonCallback<T> callback) {

        password = EncryptUtils.encryptMD5ToString(password);
        LogUtils.d("password = " + password);
        OkGo.<T>post(REGISTER_URL)
                .params("username", username)
                .params("password", password)
                .params("checkcode", checkcode)
                .execute(callback);
    }

    /**
     * 重置密码
     *
     * @param username  用户名
     * @param checkcode 验证码
     * @param password1 密码1
     * @param password2 密码2
     * @param callback
     * @param <T>
     */
    public static <T> void repeatPassword(String username, String checkcode, String password1,
                                          String password2, JsonCallback<T> callback) {

        password1 = EncryptUtils.encryptMD5ToString(password1);
        password2 = EncryptUtils.encryptMD5ToString(password2);
        OkGo.<T>post(REPEAT_PASSWORD_URL)
                .params("username", username)
                .params("checkcode", checkcode)
                .params("password1", password1)
                .params("password2", password2)
                .execute(callback);
    }

    /**
     * 退出当前账号
     *
     * @param callback
     * @param <T>
     */
    public static <T> void logout(JsonCallback<T> callback) {

        OkGo.<T>post(LOGOUT_URL)
                .execute(callback);
    }

    /**
     * 获取支付宝支付订单
     * 订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     * String appid = "13146678";
     * String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     * content = "-1"
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getAlipayOrder(String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(ALIPAY_UNIFIEDORDER_URL)
                .params("orderPrice", orderPrice)
                .params("content", "支付宝支付")
                .execute(callback);
    }


    /**
     * 获取微信支付订单
     * 订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     * String appid = "13146678";
     * String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     * content = -1
     *
     * @param callback
     */
    public static <T> void getWxOrder(String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(WX_UNIFIEDORDER_URL)
                .params("orderPrice", orderPrice)
                .params("content", "微信支付")
                .execute(callback);
    }


    /**
     * 获得全频道信息
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getAllChannel(String version, JsonCallback<T> callback) {

        OkGo.<T>get(GET_ALL_CHANNEL_URL)
                .params("version", version)
                .execute(callback);
    }

    /**
     * 获得当前用户的年级频道信息
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getUserChannelGrade(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_CHANNEL_GRADE_URL)
                .execute(callback);
    }


    /**
     * 修改用户的年级频道信息
     * 参数 grade 和 channel_data 都为可选参数，修改了哪个就传哪个，不需要修改的不要传。
     *
     * @param callback
     * @param <T>
     */
    public static <T> void setUserChannelGrade(int grade, String channel_data, JsonCallback<T>
            callback) {
        OkGo.<T>post(SET_USER_CHANNEL_GRADE_URL)
                .params("grade", grade)
                .params("channel_data", channel_data)
                .execute(callback);
    }

    /**
     * 扣代币
     *
     */
    public static <T> void db_deduct(String db, String guid, String content, String grade_id, String expiredtime, JsonCallback<T> callback) {

        OkGo.<T>post(DB_DEDUCT)
                .params("db", db)
                .params("guid", guid)
                .params("content", content)
                .params("grade_id", grade_id)
                .params("expiredtime", expiredtime)
                .execute(callback);
    }

    /**
     * 我的频道标记列表
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getChannelFlag(JsonCallback<T> callback) {

        OkGo.<T>get(GET_CHANNEL_FLAG)
                .execute(callback);
    }

    /**
     * 设置我的频道标记位
     *
     * @param callback
     * @param <T>
     * cn_rw 指的作文与写作  'cn_rw' (作文与写作频道)=> 0(数字)
     */
    public static <T> void setChannelFlag(int cn_rw, int cn_wspk, JsonCallback<T> callback) {

        OkGo.<T>post(SET_CHANNEL_FLAG)
                .params("cn_rw", cn_rw)
                .params("cn_wspk", cn_wspk)
                .execute(callback);
    }


    /**
     * 获取个人中心主页信息
     * 如果为游客，则会返回2102的错误码。
     * {
     * "result": 2102,
     * "message": "您还不是正式会员，请登录或注册"
     * }
     * 会员返回所有信息：xb(性别): 0=男1=女，null=没有设置
     *
     * @param callback
     */
    public static <T> void getUserCenterHome(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_CENTER_HOME_URL)
                .execute(callback);
    }


    /**
     * 获取个人资料设置页信息
     * 如果为游客，则会返回2102的错误码。
     *
     * @param callback
     */
    public static <T> void getUserProfile(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_PROFILE_URL)
                .execute(callback);
    }


    /**
     * 个人资料设置页保存信息
     *
     * @param callback
     */
    public static <T> void setUserProfile(String nick_name, String xb, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_PROFILE_URL)
                .params("nick_name", nick_name)
                .params("xb", xb)
                .execute(callback);
    }

    /**
     * 获取个人消息列表
     *
     * @param callback
     */
    public static <T> void getUserMsg(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_MSG_URL)
                .execute(callback);
    }


    /**
     * 设置个人消息已读
     * 'msgid' => base64_encode(json([1,2])),
     *
     * @param callback
     */
    public static <T> void setUserMsg(String msgid, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_MSG_URL)
                .params("msgid", msgid)
                .execute(callback);
    }


    /**
     * 我的收藏
     *
     * @param callback
     */
    public static <T> void getUserFavorite(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_FAVORITE_URL)
                .execute(callback);
    }

    /**
     * 我的收藏
     *
     * @param callback
     */
    public static <T> void tokenIdentifyUrl(String token, JsonCallback<T> callback) {

        OkGo.<T>post(TOKEN_IDENTIFY_URL)
                .params("token", token)
                .execute(callback);
    }


    /**
     * 推荐页首页(old)
     *
     * @param callback
     */
    public static <T> void getRecomHome(String channel_id, String pageNum, JsonCallback<T> callback) {
        //        'channel_id' =>'频道id',
        //                'pageNum'=>'页码',
        //                'pageSize'=>'每页显示数量',
        OkGo.<T>get(GET_RECOMHOME_URL)
                .params("channel_id", channel_id)
                .params("pageNum", pageNum)
                .execute(callback);
    }

    /**
     * 推荐页首页
     *
     * @param callback
     */
    public static <T> void getRecomHomeByUserTag(String pageNum, JsonCallback<T> callback) {
        //                'pageNum'=>'页码',
        //                'pageSize'=>'每页显示数量',
        OkGo.<T>get(GET_RECOM_BY_USER_TAG_URL)
                .params("pageNum", pageNum)
                .params("pageSize", 15)
                .execute(callback);
    }


    /**
     * 获取文章的推荐列表
     *
     * @param callback
     */
    public static <T> void getArticleRecom(String channel_itemid, JsonCallback<T> callback) {

        OkGo.<T>get(GET_ARTICLE_RECOM)
                .params("channel_itemid", channel_itemid)
                .execute(callback);
    }

    /**
     * 文章转发成功回调
     *
     * @param callback
     */
    public static <T> void articleShareCallback(String channel_itemid, JsonCallback<T> callback) {

        OkGo.<T>get(ARTICLE_SHARE_CALLBACK_URL)
                .params("channel_itemid", channel_itemid)
                .execute(callback);
    }

    /**
     * 查看某篇文章
     *
     * @param callback
     */
    public static <T> void getArticlUrl(String channel_itemid, JsonCallback<T> callback) {

        OkGo.<T>get(GET_ARTICLE_URL)
                .params("channel_itemid", channel_itemid)
                .execute(callback);
    }

    /**
     * 获取文章的点赞数，访问量，评论数等
     *
     * @param callback 'channel_itemid'= 频道元素 ID;
     *                 'channel_itemType' = 频道元素类型;
     */
    public static <T> void getArticlInfoUrl(String channel_itemid, String channel_itemType,
                                            JsonCallback<T> callback) {

        OkGo.<T>get(GET_ARTICLE_INFO_URL)
                .params("channel_itemid", channel_itemid)
                .params("channel_itemType", channel_itemType)
                .execute(callback);
    }


    /**
     * 点赞/取消点赞某篇文章
     * isLike：0=取消点赞，1=点赞
     *
     * @param callback
     */
    public static <T> void setArticleLike(String channel_itemid, int isLike, JsonCallback<T>
            callback) {

        OkGo.<T>post(GET_ARTICLE_LIKE_URL)
                .params("channel_itemid", channel_itemid)
                .params("isLike", isLike)
                .execute(callback);
    }


    /**
     * 收藏/取消收藏某篇文章
     * isFavor：0=取消收藏，1=收藏
     *
     * @param callback
     */
    public static <T> void setArticleFavorite(String channel_itemid, int isFavor, JsonCallback<T>
            callback) {

        OkGo.<T>post(SET_ARTICLE_FAVORITE_URL)
                .params("channel_itemid", channel_itemid)
                .params("isFavor", isFavor)
                .execute(callback);
    }



    /**
     * 获取评论
     * 返回值里的total_amount：评论总数
     * 参数里channel_itemType：0=文章，1=教材，2=教辅，3=视频
     */
    public static <T> void getArticleComment(String channel_itemid, String channel_itemType,
                                             String start, String end, JsonCallback<T> callback) {

        OkGo.<T>post(GET_ARTICLE_COMMENTS_URL)
                .params("channel_itemid", channel_itemid)
                .params("channel_itemType", channel_itemType)
                .params("start", start)
                .params("end", end)
                .execute(callback);
    }

    /**
     * 发表评论
     */
    public static <T> void setArticleComment(String channel_itemid, String channel_itemType,
                                             String cm_content, JsonCallback<T> callback) {

        OkGo.<T>post(SET_ARTICLE_COMMENTS_URL)
                .params("channel_itemid", channel_itemid)
                .params("channel_itemType", channel_itemType)
                .params("cm_content", cm_content)
                .execute(callback);
    }


    /**
     * 获取热搜词
     *
     * @param callback
     */
    public static <T> void getHotKey(JsonCallback<T> callback) {

        OkGo.<T>get(GET_HOTKEY_URL)
                .execute(callback);
    }

    /**
     * 根据关键字搜索文章  app不用直接调用
     */
    public static <T> void searchByKeyWord(String skeyword, JsonCallback<T> callback) {

        OkGo.<T>post(SEARCH_BY_KEYWORD_URL)
                .params("skeyword", skeyword)
                .execute(callback);
    }

    /**
     * 上传头像
     */
    public static <T> void setUserAvatar(File file, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_AVATAR)
                .params("a1_avatar", file)
                .execute(callback);

    }


    /**
     * 微信unid检查
     */
    public static <T> void checkWechat(String unid, JsonCallback<T> callback) {

        OkGo.<T>post(CHECK_WECHAT)
                .params("unid", unid)
                .execute(callback);

    }


    /**
     * 微信注册绑定
     */
    public static <T> void registWechat(String username, String password, String checkcode,
                                        String unid, JsonCallback<T> callback) {
        password = EncryptUtils.encryptMD5ToString(password);
        //LogUtils.d("password = " + password);
        OkGo.<T>post(REGIST_WECHAT)
                .params("username", username)
                .params("password", password)
                .params("checkcode", checkcode)
                .params("unid", unid)
                .execute(callback);

    }


    /**
     * 我的订单流水
     *
     * @param callback
     */
    public static <T> void getOrderRecord(JsonCallback<T> callback) {

        OkGo.<T>get(GET_ORDER_RECORD)
                .execute(callback);
    }

    /**
     * 删除用户
     *
     * @param callback
     */
    public static void deleteUser(StringCallback callback) {

        OkGo.<String>get(DELETE_USER)
                .execute(callback);
    }

    /**
     * 获取当前版本号
     */
    public static <T> void getCRTVersion(JsonCallback<T> callback) {

        OkGo.<T>get(GET_CRT_VERSION)
                .execute(callback);

    }

    /**
     * 设置当前版本号
     */
    public static <T> void setCRTVersion(int newVersion, JsonCallback<T> callback) {

        OkGo.<T>post(UPDATE_CRT_VERSION)
                .params("newVersion", newVersion)
                .execute(callback);

    }

    /**
     * 查看是否有权限看题目
     */
    public static <T> void hasQuesDetailAuth(String catalog_id, JsonCallback<T> callback) {

        OkGo.<T>post(HAS_QUES_DETAIL_AUTH)
                .params("catalog_id", catalog_id)
                .execute(callback);
    }

    /**
     * 获得我的作文列表
     */
    public static <T> void getCompositionList( JsonCallback<T> callback) {

        OkGo.<T>get(GET_COMPOSITION_LIST)
                .execute(callback);
    }

    /**
     * 获取目录列表
     * type=2热点素材
     type=3满分作文
     type=4批改案例
     满分作文和批改案例只有一级菜单
     */
    public static <T> void getCatalog(String type, JsonCallback<T> callback) {

        OkGo.<T>get(GET_CATALOG)
                .params("type", type)
                .execute(callback);

    }

    /**
     * 获取教师列表
     * pageNum'=>可选，从1开始，默认1,
     'pageSize'=>可选，默认25条
     返回值里：picture_bigurl 大图 picture_littleurl 小图
     */
    public static <T> void getTeacherList(String pageNum, String pageSize, JsonCallback<T>
            callback) {

        OkGo.<T>get(GET_TEACHER_LIST)
                .params("pageNum", pageNum)
                .params("pageSize", pageSize)
                .execute(callback);

    }

    /**
     * 获取素材列表
     * type=2热点素材,first_name,seconde_name都要传
     type=3满分作文，只有first_name,是年份
     type=4批改案例，只有first_name
     'pageNum'=>可选，从1开始，默认1,
     'pageSize'=>可选，默认25条
     */
    public static <T> void getListByCatalog(String type, String first_name, String second_name,
                                            String pageNum, String pageSize, JsonCallback<T>
                                                    callback) {

        OkGo.<T>get(GET_LIST_BY_CATALOG)
                .params("type", type)
                .params("first_name", first_name)
                .params("second_name", second_name)
                .params("pageNum", pageNum)
                .params("pageSize", pageSize)
                .execute(callback);

    }

    /**
     * 素材详情页
     */
    public static <T> void getDetail(int xh, JsonCallback<T>
            callback) {

        OkGo.<T>get(GET_DETAIL)
                .params("xh", xh)
                .execute(callback);

    }

    /**
     * 作文批改预下单
     * 'title' => 作文标题,
     'teacher_guid' => 教师guid,
     'check_type' => 批改类型：1评语2详批,
     'pay_amount' => 冠林币金额,
     * 返回作文订单号，客户端记录，后续操作都需要此订单号
     */
    public static <T> void getPreorder(String title, String teacher_guid, String check_type, String pay_amount, JsonCallback<T>
            callback) {

        OkGo.<T>post(GET_PREORDER)
                .params("title", title)
                .params("teacher_guid", teacher_guid)
                .params("check_type", check_type)
                .params("pay_amount", pay_amount)
                .execute(callback);

    }


    /**
     * 作文图片上传
     * 表单文件上传，可传多个
     */
    public static void compositionUpload(List<File> list, StringCallback
            callback) {

        OkGo.<String>post(COMPOSITION_FILE_UPLOAD)
                .isMultipart(true)
                .addFileParams("files", list)
                .execute(callback);

    }

    /**
     * 作文图片上传 oss
     */
    public static <T> void uploadPicture(File file, JsonCallback<T>
            callback) {

        OkGo.<T>post(UPLOAD_RW_OSS)
                .params("a1_rw", file)
                .execute(callback);

    }


    /**
     * 作文图片上传回调
     */
    public static <T> void uploadCallback(String rw_orderid, String img_url, JsonCallback<T>
            callback) {

        OkGo.<T>post(UPLOAD_CALLBACK)
                .params("rw_orderid", rw_orderid)
                .params("img_url", img_url)
                .execute(callback);

    }

    /**
     * 作文订单支付完成回调
     * 'rw_orderid' => '作文订单号',
     'isPay' => '1',
     * 回调服务器告知完成支付，服务器通知作文及时批平台
     */
    public static <T> void afterPayCallback(String rw_orderid, JsonCallback<T>
            callback) {

        OkGo.<T>post(AFTER_PAY_CALLBACK)
                .params("rw_orderid", rw_orderid)
                .params("isPay", 1)
                .execute(callback);

    }


    /**
     * 获取所有活动
     */
    public static <T> void getActivity( JsonCallback<T>
                                                callback) {
        OkGo.<T>get(GET_ACTIVITY)
                .params("pageNum", 1)
                .params("pageSize", 25)
                .execute(callback);
    }


    /**
     * 获取某个活动的lesson
     */
    public static <T> void getLessonById(int activity_id, JsonCallback<T>
            callback) {
        OkGo.<T>get(GET_LESSON_BY_ID)
                .params("activity_id", activity_id)
                .execute(callback);
    }


    /**
     * 获取某个活动的直播信息
     */
    public static <T> void getActivityLivingById(int activity_id, JsonCallback<T>
                                                callback) {
        OkGo.<T>get(GET_ACTIVITY_LIVING_BY_ID)
                .params("activity_id", activity_id)
                .execute(callback);
    }

    /**
     * 评课默认缓存2小时，如果需要手动删除缓存，调此接口。
     * 例如：浏览器输入网址：http://appser.hebeijiaoyu.cn/iclient/clilive/removeLiveCache 即可。
     */
    public static <T> void removeLiveCache( JsonCallback<T>
            callback) {
        OkGo.<T>get(REMOVE_LIVE_CACHE)
                .execute(callback);
    }


    /**
     * 记录某个活动的人次信息
     */
    public static <T> void addCountByID(String activity_id, JsonCallback<T>
                                                    callback) {
        OkGo.<T>get(ADD_COUNT_BY_ID)
                .params("activity_id", activity_id)
                .execute(callback);
    }


    /**
     * 代币数量
     * 鉴权之后调用天恒接口，若为游客，返回值为0
     *
     * @param callback
     */
    public static <T> void dbAmount(JsonCallback<T> callback) {

        OkGo.<T>get(DB_AMOUNT_URL)
                .execute(callback);
    }


    //---------rxjava2访问方式------------------

    //    /**
    //     * 登录
    //     *
    //     * @param username username
    //     * @param password password
    //     * @POST("/user/login")
    //     */
    //    public static Observable<ResponseData<LoginBean>> login(String username, String
    // password) {
    //
    //        return OkGo.<ResponseData<UserBean>>post(LOGIN_URL)
    //                .params("username", username)
    //                .params("password", password)
    //                .converter(new JsonConvert<ResponseData<LoginBean>>())
    //                .adapt(new ObservableBody<ResponseData<LoginBean>>());
    //    }

    //    /**
    //     * 知识体系 (类别tag)
    //     * http://www.wanandroid.com/tree/json
    //     *
    //     * @GET("/tree/json")
    //     */
    //    public static Observable<ResponseData<List<loginBean>>> getTypeTagData() {
    //        String url = BASE_URL + "tree/json";
    //        return OkGo.<ResponseData<List<loginBean>>>get(url)
    //                .converter(new JsonConvert<ResponseData<List<loginBean>>>() {
    //                })
    //                .adapt(new ObservableBody<ResponseData<List<loginBean>>>());
    //    }

}
