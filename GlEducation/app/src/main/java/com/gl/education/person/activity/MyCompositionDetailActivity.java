package com.gl.education.person.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSgetCommentDataEvent;
import com.gl.education.home.event.JSopenCompositionAttachmentEvent;
import com.gl.education.person.data.MycompositionDataManager;
import com.gl.education.person.interactive.MyCompositionDetailInteractive;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCompositionDetailActivity extends BaseActivity {

    String clickUrl = "http://zuowen.hebeijiaoyu.com.cn/glwz/";

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    private int clickPosition;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_composition_detail;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        // 注册订阅者
        EventBus.getDefault().register(this);

        Intent intent = getIntent();

        clickPosition = intent.getIntExtra("position", -1);
        top_title.setText("我的作文");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_MINE + token;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new MyCompositionDetailInteractive(mAgentWeb,
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
        super.onBackPressedSupport();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCommentData(JSgetCommentDataEvent event) {

        String json = Convert.toJson(MycompositionDataManager.bean.getList().get(clickPosition));

        LogUtils.d(""+MycompositionDataManager.bean.getList().get(clickPosition));
        LogUtils.d(""+json);

        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setCommentData,  json);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openCompositionAttachment(JSopenCompositionAttachmentEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, MyCompositionNextActivity.class);
        intent.putExtra("url", ""+MycompositionDataManager.bean.getList().get(clickPosition).getComment_url());
        startActivity(intent);
    }


}
