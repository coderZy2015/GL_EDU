package com.gl.education.home.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenWebViewEvent;
import com.gl.education.home.interactive.AndroidInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心收藏跳转web页
 */
public class MyClassCollectionActivity extends BaseActivity {

    protected AgentWeb mAgentWeb;
    private String classType;
    public String bookTitle = "";

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_class_collection;
    }

    @Override
    protected String setIdentifier() {
        return null;
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
        //返回刷新页面
        setResult(1);
        finish();
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("webUrl");
        classType = intent.getStringExtra("fromStr");

        bookTitle = intent.getStringExtra("bookTitle");

        if (bookTitle != null)
            top_title.setText(bookTitle);

        // 注册订阅者
        EventBus.getDefault().register(this);

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url);

        //设置默认背景色
        //      FrameLayout frameLayout = mAgentWeb.getWebCreator().getWebParentLayout();
        //      frameLayout.setBackgroundColor(Color.BLACK);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示

        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInteractive(mAgentWeb,
                this, eventTAG, classType));
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(OpenWebViewEvent event) {

        if (event.getMessage().equals(eventTAG)){
            Intent intent = new Intent();
            intent.putExtra("bookTitle", "微课目录");
            intent.putExtra("fromStr", "weike");
            intent.setClass(this, BookMenuActivity.class);
            startActivity(intent);
        }
    }
}
