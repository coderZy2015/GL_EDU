package com.gl.education.composition.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.composition.event.JSGetTeacherDetailDataEvent;
import com.gl.education.composition.event.JSToUploadComposiseEvent;
import com.gl.education.composition.interactive.TeacherDetailInteractive;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.helper.Convert;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class TeacherDetailActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    public String teacherId = "";
    public String teacherName = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_teacher_detail;
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

        teacherId = intent.getStringExtra("id");

        top_title.setText("教师履历");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_TEACHER_DETAIL + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new TeacherDetailInteractive(mAgentWeb,
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

    //获取教师信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTeacherDetailData(JSGetTeacherDetailDataEvent event) {
        //对比ID 找的对应老师的数据
         for (int i=0; i< CompositionDataManager.teacherListBean.getData().getList().size(); i++){
             if (teacherId.equals(CompositionDataManager.teacherListBean.getData().getList().get(i).getRs_guid())){
                 String json = Convert.toJson(CompositionDataManager.teacherListBean.getData().getList().get(i));
                 teacherName = CompositionDataManager.teacherListBean.getData().getList().get(i).getName();
                 mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                         .callJs_setTeacherDetailData,  json);
             }
          }

    }

    //获取教师信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toUploadComposise(JSToUploadComposiseEvent event) {

        Intent intent = new Intent();
        intent.setClass(this, RAWUploadActivity.class);
        intent.putExtra("id", event.getBean().getId());
        intent.putExtra("type", event.getBean().getType()+"");
        intent.putExtra("name", teacherName);
        intent.putExtra("intent", true);
        startActivity(intent);
    }



}
