package com.gl.education.evaluation.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.evaluation.event.JSGetPlayingActivityEvent;
import com.gl.education.evaluation.event.JSLivingGetAllActivityListEvent;
import com.gl.education.evaluation.event.JSLivingGetLessonListByIDEvent;
import com.gl.education.evaluation.interactive.WSPKLivingInteractive;
import com.gl.education.evaluation.model.GetPlayingActivityBean;
import com.gl.education.evaluation.util.WSPKDataMananger;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WSPKLivingActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    public int activity_id = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wspkliving;
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
        activity_id = intent.getIntExtra("id", 0);

        top_title.setText("直播");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_WSPK_LIVING + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WSPKLivingInteractive(mAgentWeb,
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

    //获取当前直播的Activity
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPlayingActivity(JSGetPlayingActivityEvent event) {
        HomeAPI.getActivityLivingById(activity_id, new JsonCallback<GetPlayingActivityBean>() {
            @Override
            public void onSuccess(Response<GetPlayingActivityBean> response) {
                GetPlayingActivityBean bean = response.body();

                if (response.body().getResult() == 1000){
                    for (int i=0; i< WSPKDataMananger.allActivityDataBean.getData().getList().size(); i++){
                        if (activity_id == WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getId()){
                            String title = WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getTitle();

                            if (bean.getData().size() == 1){
                                bean.getData().get(0).setTitle(title);

                                String json = Convert.toJson(bean.getData().get(0));

                                mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                        .callJs_setPlayingActivity,  json);
                            }
                            return;
                        }
                    }
                }
            }
        });
    }

    //进到Activity中，列出相关lesson页面	点击触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLessonListByID(JSLivingGetLessonListByIDEvent event) {

        int id = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(this, WSPKLessonListActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //获取所有的Activity列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAllActivityList(JSLivingGetAllActivityListEvent event) {
        String json = Convert.toJson(WSPKDataMananger.allActivityDataBean.getData());

        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setActivityList,  json);
    }
}
