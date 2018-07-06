package com.gl.education.api;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gl.education.helper.JsonCallback;
import com.gl.education.helper.JsonConvert;
import com.gl.education.home.model.ResponseData;
import com.gl.education.home.model.TypeTagVO;
import com.gl.education.home.model.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zy on 2018/6/5.
 */

public class HomeAPI {

    //基础
    public static final String BASE_URL = "http://a1guanlin.eugames.cn/";

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
     * 获取支付宝支付订单
     *  订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     *   String appid = "13146678";
     *   String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     *
     * @param callback
     * @param <T>
     */
    public static <T> void getAlipayOrder(String uid, String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(ALIPAY_UNIFIEDORDER_URL)
                .params("uid", uid)
                .params("orderPrice", orderPrice)
                .params("content", "-1")
                .params("gradeid", "99")
                .execute(callback);
    }


    /**
     *  获取微信支付订单
     *  订单系统接口，详情参阅：http://apigl.gl.to3.cc/index.php/wiki
     *   String appid = "13146678";
     *   String sercret = "nksYgmzsDjxKquGqUtcXCSJTIpZYFwzK";
     *
     * @param callback
     */
    public static <T>void getWxOrder(String uid, String orderPrice, JsonCallback<T> callback) {

        OkGo.<T>post(WX_UNIFIEDORDER_URL)
                .params("uid", uid)
                .params("orderPrice", orderPrice)
                .params("content", "-1")
                .params("gradeid", "99")
                .execute(callback);
    }



    //---------rxjava2访问方式------------------

    /**
     * 登录
     *
     * @param username username
     * @param password password
     * @POST("/user/login")
     */
    public static Observable<ResponseData<UserBean>> login(String username, String password) {

        return OkGo.<ResponseData<UserBean>>post(LOGIN_URL)
                .params("username", username)
                .params("password", password)
                .converter(new JsonConvert<ResponseData<UserBean>>())
                .adapt(new ObservableBody<ResponseData<UserBean>>());
    }

    /**
     * 知识体系 (类别tag)
     * http://www.wanandroid.com/tree/json
     *
     * @GET("/tree/json")
     */
    public static Observable<ResponseData<List<TypeTagVO>>> getTypeTagData() {
        String url = BASE_URL + "tree/json";
        return OkGo.<ResponseData<List<TypeTagVO>>>get(url)
                .converter(new JsonConvert<ResponseData<List<TypeTagVO>>>() {
                })
                .adapt(new ObservableBody<ResponseData<List<TypeTagVO>>>());
    }

}
