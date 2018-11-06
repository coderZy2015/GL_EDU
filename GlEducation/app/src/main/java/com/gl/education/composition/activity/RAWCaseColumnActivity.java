package com.gl.education.composition.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.event.JSCASEopenListeEvent;
import com.gl.education.composition.event.JSgetCaseColumnEvent;
import com.gl.education.composition.interactive.RAWCaseColumnInteractive;
import com.gl.education.composition.model.GetHotColumnBean;
import com.gl.education.evaluation.model.GetAllLessonListBean;
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

public class RAWCaseColumnActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    public GetAllLessonListBean allLessonBean;//当前活动里所有课程的数据
    public int lessonId = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rawcase_column;
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

        lessonId = intent.getIntExtra("id", 0);
        allLessonBean = (GetAllLessonListBean)intent.getSerializableExtra("data");

        top_title.setText("批改案例");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_RAW_CASECOLUMN + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RAWCaseColumnInteractive(mAgentWeb,
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

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCaseColumn(JSgetCaseColumnEvent event) {
        HomeAPI.getCatalog("4", new JsonCallback<GetHotColumnBean>() {
            @Override
            public void onSuccess(Response<GetHotColumnBean> response) {
                if (response.body().getResult() == 1000){
                    String json = Convert.toJson(response.body().getData());

                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                            .callJs_setCaseColumn,  json);
                }
            }
        });
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openList(JSCASEopenListeEvent event) {
        String first = event.getBean().getFirstname();
        String second = event.getBean().getSecondname();

        Intent intent = new Intent();
        intent.setClass(this, RAWListActivity.class);
        intent.putExtra("first", first);
        intent.putExtra("second", second);
        intent.putExtra("type", "4");
        startActivity(intent);
    }

}
