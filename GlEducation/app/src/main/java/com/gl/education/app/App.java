package com.gl.education.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;

/**
 * Application
 * Created by zy on 2018/6/5.
 */

public class App extends Application {

    public static List<Activity> activities = new LinkedList<>();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        Utils.init(this);
        initOkGo();
        initFragmention();
        initZXing_lib();
        initScreenTools();
    }

    /**
     * 初始化okgo
     */
    private void initOkGo() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //        builder.addInterceptor(new TokenInterceptor());

        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(builder.build()) //设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(1);

//        OkHttpClient client = OkGo.getInstance().getOkHttpClient();
//        Log.i("zy_code", "write超时时间 == " +  client.writeTimeoutMillis());
//        Log.i("zy_code", "read超时时间 == " +  client.readTimeoutMillis());
//        Log.i("zy_code", "connect超时时间 == " +  client.connectTimeoutMillis());

    }
    /**
     * Fragment管理初始化
     */
    public void initFragmention(){

        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                .install();

    }
    /**
     * ZXing初始化
     */
    public void initZXing_lib(){
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 屏幕适配初始化
     */
    public void initScreenTools(){
        ScreenAdapterTools.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ScreenAdapterTools.getInstance().reset(this);
    }

    /**
     * 重置屏幕密度
     */
    private void resetDensity() {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 750f;
        final float DESIGN_HEIGHT = 1336f;
        final float DESTGN_INCH = 5.0f;
        //大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度
        final float BIG_SCREEN_FACTOR = 0.2f;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //确定参照屏幕密度
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / 160;
        //确定最终屏幕密度
        float relativeDensity = referenceDensity * rate;
        if (relativeDensity > dm.density) {
            relativeDensity = relativeDensity - (relativeDensity - dm.density) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
    }


    public static Context getmContext() {
        return mContext;
    }

    /**
     * 退出程序
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}