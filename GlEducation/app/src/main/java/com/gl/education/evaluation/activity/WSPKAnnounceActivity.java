package com.gl.education.evaluation.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.evaluation.event.JSGetSelectedAactivityInfoEvent;
import com.gl.education.evaluation.interactive.WSPKAnnounceInteractive;
import com.gl.education.evaluation.model.GetActivityBean;
import com.gl.education.evaluation.util.WSPKDataMananger;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WSPKAnnounceActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";
    public int activityId = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wspkannounce;
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

        activityId = intent.getIntExtra("id", 0);

        top_title.setText("活动通知");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_WSPK_ANNOUNCE + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WSPKAnnounceInteractive(mAgentWeb,
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
        //        boolean isBack = false;
        //        isBack = mAgentWeb.back();
        //        if (isBack == true)
        //            return;

        super.onBackPressedSupport();
    }

    //获取课程数据 列出lesson的内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSelectedActivityInfo(JSGetSelectedAactivityInfoEvent event) {
        GetActivityBean.DataBean.ListBean bean = null;
        for (int i = 0; i< WSPKDataMananger.allActivityDataBean.getData().getList().size(); i++){
            if (WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getId() == activityId){
                bean = WSPKDataMananger.allActivityDataBean.getData().getList().get(i);
            }
        }

        if (bean != null){
            String json = Convert.toJson(bean);
            //对数据进行重新排列
            mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                    .callJs_currentActivity,  json);
        }

    }
}
