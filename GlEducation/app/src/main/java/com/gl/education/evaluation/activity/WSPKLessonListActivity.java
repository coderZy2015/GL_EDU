package com.gl.education.evaluation.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.evaluation.event.JSGOLessonDeatailEvent;
import com.gl.education.evaluation.event.JSGetAllLessonListEvent;
import com.gl.education.evaluation.event.JSGetSelectedActivityInfoEvent;
import com.gl.education.evaluation.event.JSShowAnnounceEvent;
import com.gl.education.evaluation.interactive.WSPKLessonListInteractive;
import com.gl.education.evaluation.model.GetActivityBean;
import com.gl.education.evaluation.model.GetAllLessonListBean;
import com.gl.education.evaluation.util.WSPKDataMananger;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.LoginInfoActivity;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class WSPKLessonListActivity extends BaseActivity {

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
        return R.layout.activity_wspklesson_list;
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
        top_title.setText("课程列表");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_WSPK_LESSONLIST + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WSPKLessonListInteractive(mAgentWeb,
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

    //进到课程的具体信息页面	点击触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goLessonDetail(JSGOLessonDeatailEvent event) {
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(this, LoginInfoActivity.class);
            startActivity(intent);
            return;
        }

        int id = event.getBean().getId();

        if (WSPKDataMananger.allLessonBean != null){
            for (int i=0; i<WSPKDataMananger.allLessonBean.getData().size(); i++){
                if (WSPKDataMananger.allLessonBean.getData().get(i).getId() == id){
                    GetAllLessonListBean.DataBean bean = WSPKDataMananger.allLessonBean.getData().get(i);

                    if (bean.getTime_status() == 1){
                            ToastUtils.showShort("暂未开始");
                            return;
                    }
                    if (bean.getIs_open() == 0){
                        ToastUtils.showShort("视频回放未公开，请耐心等待");
                        return;
                    }
                }
            }
        }else{
            ToastUtils.showShort("");
            return;
        }

        Intent intent = new Intent();
        intent.setClass(this, WSPKLessonActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //获取选中的Activity信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSelectedActivityInfo(JSGetSelectedActivityInfoEvent event) {
        GetActivityBean.DataBean.ListBean bean = null;
         for (int i=0; i< WSPKDataMananger.allActivityDataBean.getData().getList().size(); i++){
             if (WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getId() == activity_id){
                 bean = WSPKDataMananger.allActivityDataBean.getData().getList().get(i);
             }
          }

       if (bean != null){
           String json = Convert.toJson(bean);
           //对数据进行重新排列
           mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                   .callJs_setActivity,  json);
       }
    }

    ///获取当前Activity所有课程列表		加载触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAllLessonList(JSGetAllLessonListEvent event) {
        HomeAPI.getLessonById(activity_id, new JsonCallback<GetAllLessonListBean>() {
            @Override
            public void onSuccess(Response<GetAllLessonListBean> response) {
                if (response.body().getResult() == 1000){
                    WSPKDataMananger.allLessonBean = response.body();

                    if (WSPKDataMananger.allLessonBean.getData() != null){
                        //对数据进行重新排列
                        int len = WSPKDataMananger.allLessonBean.getData().size();
//                        for (int i=0; i<len; i++){
//                            for (int j=0; j<len-i-1; j++){
//                                if (WSPKDataMananger.allLessonBean.getData().get(j).getLive_start_time() > WSPKDataMananger.allLessonBean.getData().get(j+1).getLive_start_time()){
//                                    Collections.swap(WSPKDataMananger.allLessonBean.getData(),j,j+1);
//                                }
//                            }
//                        }

                        for (int i=0; i<len; i++){

                            int status = WSPKDataMananger.allLessonBean.getData().get(i).getStatus();
                            if (status == 1){
                                WSPKDataMananger.allLessonBean.getData().get(i).setTime_status(0);
                            }
                            else if (status == 0){
                                WSPKDataMananger.allLessonBean.getData().get(i).setTime_status(1);
                            }
                            else if (status == 2){
                                WSPKDataMananger.allLessonBean.getData().get(i).setTime_status(2);
                            }
                        }

                        String json = Convert.toJson(WSPKDataMananger.allLessonBean.getData());

                        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                .callJs_setLessonList,  json);
                    }else{
                        ToastUtils.showShort("加载失败，请重试");
                    }


                }


            }
        });
    }

    ///进到课程的具体信息页面	点击触发
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAnnounce(JSShowAnnounceEvent event) {
        int id = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(this, WSPKAnnounceActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    /*
   * 将时间戳转换为时间
   */
    public String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

}
