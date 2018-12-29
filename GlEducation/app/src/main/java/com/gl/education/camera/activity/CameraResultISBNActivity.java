package com.gl.education.camera.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.JSLoginSuccess;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.teachingassistant.activity.JFBookContentActivity;
import com.gl.education.teachingassistant.activity.JFBookOrderPaymentActivity;
import com.gl.education.teachingassistant.activity.JFBookOtherMoreActivity;
import com.gl.education.teachingassistant.activity.JFBookPackageBuyActivity;
import com.gl.education.teachingassistant.event.JSJFBookBuySuccessFinishEvent;
import com.gl.education.teachingassistant.event.JSJFBookMenuLoginEvent;
import com.gl.education.teachingassistant.event.JSJFBookMenuOpenWebViewEvent;
import com.gl.education.teachingassistant.interactive.JFBookMenuInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 扫码 结果展示
 */
public class CameraResultISBNActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_camera_result;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        String id = getIntent().getStringExtra("isbn");

        String url = AppConstant.ISBN_URL + id;
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        token = "?token="+token;
        // 注册订阅者
        EventBus.getDefault().register(this);

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url+token);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new JFBookMenuInteractive(mAgentWeb,
                this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        mAgentWeb.getWebLifeCycle().onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressedSupport();
    }

    @Override
    public void onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();
        if (isBack == true)
            return;

        super.onBackPressedSupport();
    }

    //购买成功刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSJFBookBuySuccessFinishEvent event) {
        mAgentWeb.getWebCreator().getWebView().reload();    //刷新
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toLogin(JSJFBookMenuLoginEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, LoginInfoActivity.class);
        startActivityForResult(intent, 100);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(JSJFBookMenuOpenWebViewEvent event) {
        if (event.getBean().getTitle().equals("教辅内容")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookContentActivity.class);
            startActivity(intent);
        }else if(event.getBean().getTitle().equals("订单支付")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookOrderPaymentActivity.class);
            startActivity(intent);
        }else if(event.getBean().getTitle().equals("打包购买")){
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookPackageBuyActivity.class);
            startActivity(intent);
        } else{//举一反三
            Intent intent = new Intent();
            intent.putExtra("url", event.getBean().getUrl());
            intent.putExtra("title", event.getBean().getTitle());
            intent.setClass(this, JFBookOtherMoreActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            if (data != null) {
                String token = data.getStringExtra("token");
                JSLoginSuccess loginS = new JSLoginSuccess();
                loginS.setGuid(token);
                String json = Convert.toJson(loginS);

                mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                        .callJs_login, json);
            }

        }
    }

}
