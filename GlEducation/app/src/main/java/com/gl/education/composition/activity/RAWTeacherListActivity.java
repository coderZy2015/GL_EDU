package com.gl.education.composition.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.composition.event.JSToUploadComposiseEvent2;
import com.gl.education.composition.event.JSgetTeacherListEvent;
import com.gl.education.composition.event.JSopenTeacherDetailEvent;
import com.gl.education.composition.event.RefreshViewEvent;
import com.gl.education.composition.interactive.RAWTeacherListInteractive;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.evaluation.model.GetAllLessonListBean;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RAWTeacherListActivity extends BaseActivity {

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
        return R.layout.activity_rawlist;
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

        top_title.setText("教师列表");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_RAW_TEACHERLIST + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RAWTeacherListInteractive(mAgentWeb,
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
    public void getTeacherList(JSgetTeacherListEvent event) {

        String json = Convert.toJson(CompositionDataManager.teacherListBean.getData());
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setTeacherListData,  json);
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toUploadComposise(JSToUploadComposiseEvent2 event) {

        CompositionDataManager.data.rawType = event.getBean().getType()+"";
        CompositionDataManager.data.teacherID = event.getBean().getId();

        //对比ID 找的对应老师的数据
        for (int i=0; i< CompositionDataManager.teacherListBean.getData().getList().size(); i++){
            if (event.getBean().getId().equals(CompositionDataManager.teacherListBean.getData().getList().get(i).getRs_guid())){
                CompositionDataManager.data.teacherName = CompositionDataManager.teacherListBean.getData().getList().get(i).getName();;
            }
        }
        RefreshViewEvent event1 = new RefreshViewEvent();
        EventBus.getDefault().post(event1);
        finish();
    }

    //废弃
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openTeacherDetail(JSopenTeacherDetailEvent event) {
        String rs_guid = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(this, TeacherDetailActivity.class);
        intent.putExtra("id", rs_guid);
        startActivity(intent);
    }
}
