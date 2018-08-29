package com.gl.education.app;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;

/**
 * Created by zy on 2018/6/5.
 */

public class HomeAPI {

    //基础
    //public static final String BASE_URL = "http://a1guanlin.eugames.cn/";
    //beta版
    public static final String BASE_URL = "http://appserbeta.hebeijiaoyu.cn/";
    //正式
    //public static final String BASE_URL = "http://appser.hebeijiaoyu.cn/";

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
    //退出当前账号
    private static String LOGOUT_URL = BASE_URL + "iclient/clipublics/logout";
    //推荐页首页
    private static String GET_RECOMHOME_URL = BASE_URL + "iclient/clirecom/getRecomHome";
    //查看某篇文章
    private static String GET_ARTICLE_URL = BASE_URL + "iclient/clipublics/getArticle";
    //点赞/取消点赞某篇文章
    private static String GET_ARTICLE_LIKE_URL = BASE_URL + "iclient/clirecom/setArticleLikes";
    //浏览了某篇文章
    private static String GET_ARTICLE_VISITOR_URL = BASE_URL + "iclient/clirecom/setArticleVisitors";
    //收藏/取消收藏某篇文章
    private static String GET_ARTICLE_FAVORITE_URL = BASE_URL + "iclient/clirecom/setArticleFavorite";
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

    //删除我的用户
    private static String HAS_QUES_DETAIL_AUTH	= BASE_URL + "iclient/cliuser/hasQuesDetailAuth";


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
     * @param deviceid  设备号  取不到会随机  获取到token后会上传到header里
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
    public static <T> void loginAuth( JsonCallback<T> callback) {

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
    public static <T> void logout( JsonCallback<T> callback) {

        OkGo.<T>post(LOGOUT_URL)
                .execute(callback);
    }

    /**
     * 获取支付宝支付订单
     *  订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     *   String appid = "13146678";
     *   String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     *   content = "-1"
     * @param callback
     * @param <T>
     */
    public static <T> void getAlipayOrder( String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(ALIPAY_UNIFIEDORDER_URL)
                .params("orderPrice", orderPrice)
                .params("content", "content")
                .execute(callback);
    }


    /**
     *  获取微信支付订单
     *  订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     *   String appid = "13146678";
     *   String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     *   content = -1
     * @param callback
     */
    public static <T>void getWxOrder(String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(WX_UNIFIEDORDER_URL)
                .params("orderPrice", orderPrice)
                .params("content", "content")
                .execute(callback);
    }


    /**
     * 获得全频道信息
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getAllChannel( JsonCallback<T> callback) {

        OkGo.<T>get(GET_ALL_CHANNEL_URL)
                .execute(callback);
    }

    /**
     * 获得当前用户的年级频道信息
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getUserChannelGrade( JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_CHANNEL_GRADE_URL)
                .execute(callback);
    }


    /**
     * 修改用户的年级频道信息
     * 参数 grade 和 channel_data 都为可选参数，修改了哪个就传哪个，不需要修改的不要传。
     * @param callback
     * @param <T>
     */
    public static <T> void setUserChannelGrade(int grade, String channel_data, JsonCallback<T> callback ) {
        OkGo.<T>post(SET_USER_CHANNEL_GRADE_URL)
                .params("grade", grade)
                .params("channel_data", channel_data)
                .execute(callback);
    }

    /**
     *  获取个人中心主页信息
     *  如果为游客，则会返回2102的错误码。
     {
     "result": 2102,
     "message": "您还不是正式会员，请登录或注册"
     }
     会员返回所有信息：xb(性别): 0=男1=女，null=没有设置
     * @param callback
     */
    public static <T>void getUserCenterHome( JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_CENTER_HOME_URL)
                .execute(callback);
    }


    /**
     *  获取个人资料设置页信息
     *  如果为游客，则会返回2102的错误码。
     * @param callback
     */
    public static <T>void getUserProfile( JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_PROFILE_URL)
                .execute(callback);
    }


    /**
     *  个人资料设置页保存信息
     * @param callback
     */
    public static <T>void setUserProfile(String nick_name, String xb, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_PROFILE_URL)
                .params("nick_name", nick_name)
                .params("xb", xb)
                .execute(callback);
    }

    /**
     *  获取个人消息列表
     * @param callback
     */
    public static <T>void getUserMsg(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_MSG_URL)
                .execute(callback);
    }


    /**
     *  设置个人消息已读
     *  'msgid' => base64_encode(json([1,2])),
     * @param callback
     */
    public static <T>void setUserMsg(String msgid, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_MSG_URL)
                .params("msgid", msgid)
                .execute(callback);
    }


    /**
     *  我的收藏
     * @param callback
     */
    public static <T>void getUserFavorite(JsonCallback<T> callback) {

        OkGo.<T>get(GET_USER_FAVORITE_URL)
                .execute(callback);
    }

    /**
     *  推荐页首页
     * @param callback
     */
    public static <T>void getRecomHome(JsonCallback<T> callback) {
//        'channel_id' =>'频道id',
//                'pageNum'=>'页码',
//                'pageSize'=>'每页显示数量',
        OkGo.<T>get(GET_RECOMHOME_URL)
                .execute(callback);
    }

    /**
     *  查看某篇文章
     * @param callback
     */
    public static <T>void getArticlUrl(String channel_itemid, JsonCallback<T> callback) {

        OkGo.<T>get(GET_ARTICLE_URL)
                .params("channel_itemid", channel_itemid)
                .execute(callback);
    }

    /**
     *  获取评论
     返回值里的total_amount：评论总数
     参数里channel_itemType：0=文章，1=教材，2=教辅，3=视频
     */
    public static <T>void getArticleComment(String channel_itemid, String channel_itemType, String start, String end, JsonCallback<T> callback) {

        OkGo.<T>post(GET_ARTICLE_COMMENTS_URL)
                .params("channel_itemid", channel_itemid)
                .params("channel_itemType", channel_itemType)
                .params("start", start)
                .params("end", end)
                .execute(callback);
    }


    /**
     *  获取热搜词
     * @param callback
     */
    public static <T>void getHotKey(JsonCallback<T> callback) {

        OkGo.<T>get(GET_HOTKEY_URL)
                .execute(callback);
    }

    /**
     * 根据关键字搜索文章  app不用直接调用
     */
    public static <T>void searchByKeyWord(String skeyword, JsonCallback<T> callback) {

        OkGo.<T>post(SEARCH_BY_KEYWORD_URL)
                .params("skeyword", skeyword)
                .execute(callback);
    }

    /**
     * 上传头像
     */
    public static <T>void setUserAvatar(File file, JsonCallback<T> callback) {

        OkGo.<T>post(SET_USER_AVATAR)
                .params("a1_avatar",  file)
                .execute(callback);

    }


    /**
     * 微信unid检查
     */
    public static <T>void checkWechat(String unid, JsonCallback<T> callback) {

        OkGo.<T>post(CHECK_WECHAT)
                .params("unid",  unid)
                .execute(callback);

    }


    /**
     * 微信注册绑定
     */
    public static <T>void registWechat(String username, String password, String checkcode, String unid, JsonCallback<T> callback) {
        password = EncryptUtils.encryptMD5ToString(password);
        //LogUtils.d("password = " + password);
        OkGo.<T>post(REGIST_WECHAT)
                .params("username",  username)
                .params("password",  password)
                .params("checkcode",  checkcode)
                .params("unid", unid)
                .execute(callback);

    }


    /**
     *  我的订单流水
     * @param callback
     */
    public static <T>void getOrderRecord(JsonCallback<T> callback) {

        OkGo.<T>get(GET_ORDER_RECORD)
                .execute(callback);
    }

    /**
     *  删除用户
     * @param callback
     */
    public static void deleteUser(StringCallback callback) {

        OkGo.<String>get(DELETE_USER)
                .execute(callback);
    }

    /**
     * 查看是否有权限看题目
     */
    public static <T>void hasQuesDetailAuth(String catalog_id, JsonCallback<T> callback) {

        OkGo.<T>post(HAS_QUES_DETAIL_AUTH)
                .params("catalog_id",  catalog_id)
                .execute(callback);

    }


    /**
     *  代币数量
     *  鉴权之后调用天恒接口，若为游客，返回值为0
     * @param callback
     */
    public static <T>void dbAmount(JsonCallback<T> callback) {

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
//    public static Observable<ResponseData<LoginBean>> login(String username, String password) {
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
