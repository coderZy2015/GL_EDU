package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.evaluation.activity.WSPKLessonListActivity;
import com.gl.education.evaluation.activity.WSPKLivingActivity;
import com.gl.education.evaluation.event.JSGetAllActivityListEvent;
import com.gl.education.evaluation.event.JSGetLessonListByIDEvent;
import com.gl.education.evaluation.event.JSGoLivingActivityEvent;
import com.gl.education.evaluation.model.GetActivityBean;
import com.gl.education.evaluation.util.WSPKDataMananger;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.interactive.ClassEvaluationInteractive;
import com.gl.education.home.model.ChannelEntity;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by zy on 2018/10/12.
 * 评课频道
 */

public class ClassEvaluationFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    protected AgentWeb mAgentWeb;

    String url = "";

    String token = "";

    private ChannelEntity channelEntity;

    private String teacherNum = "1";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_webview;
    }

    @Override
    protected String setIdentifier() {
        return "HomePageFragment";
    }


    public static ClassEvaluationFragment newInstance(ChannelEntity entity) {
        ClassEvaluationFragment frag = new ClassEvaluationFragment();
        Bundle b = new Bundle();

        b.putSerializable("ChannelEntity", entity);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void init() {
        super.init();
        // 注册订阅者
        EventBus.getDefault().register(this);

        initFragTag("RecommendFragment");

        Bundle args = getArguments();
        if (args != null) {
            channelEntity = (ChannelEntity) args.getSerializable("ChannelEntity");
        }
        url = channelEntity.getUrl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;

        url = AppConstant.YY_WEB_WSPK;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url+token);

        LogUtils.d(""+url+token);

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new ClassEvaluationInteractive(mAgentWeb,
                getActivity()));

    }

    //到Activity中，列出相关lesson页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLessonListByID(JSGetLessonListByIDEvent event) {
        int id = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(_mActivity, WSPKLessonListActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        LogUtils.d("getLessonListByID  startActivity");
    }

    //进入正在直播的Activity
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goLivingActivity(JSGoLivingActivityEvent event) {
        int id = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(getActivity(), WSPKLivingActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //获取所有活动
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAllActivityList(JSGetAllActivityListEvent event) {
        HomeAPI.getActivity(new JsonCallback<GetActivityBean>() {
            @Override
            public void onSuccess(Response<GetActivityBean> response) {
                if (response.body().getResult() == 1000){

                    WSPKDataMananger.allActivityDataBean = response.body();
                    //排序
                    int len = WSPKDataMananger.allActivityDataBean.getData().getList().size();
                    for (int i=0; i<len; i++){
                         for (int j=0; j<len-i-1; j++){
                             if (WSPKDataMananger.allActivityDataBean.getData().getList().get(j).getStart_time() < WSPKDataMananger.allActivityDataBean.getData().getList().get(j+1).getStart_time()){
                                 Collections.swap(WSPKDataMananger.allActivityDataBean.getData().getList(),j,j+1);
                             }
                         }
                    }

                    long timeStamp = System.currentTimeMillis();
                    LogUtils.d(""+stampToDate(timeStamp));
                    LogUtils.d(""+timeStamp);
                    timeStamp = timeStamp/1000;

                    for (int i=0; i<len; i++){
                         int startTime = WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getStart_time();
                         int endTime = WSPKDataMananger.allActivityDataBean.getData().getList().get(i).getEnd_time();
                         if (timeStamp > endTime){
                             WSPKDataMananger.allActivityDataBean.getData().getList().get(i).setTime_status(2);
                         }
                         else if(timeStamp > startTime && timeStamp < endTime){
                             WSPKDataMananger.allActivityDataBean.getData().getList().get(i).setTime_status(0);
                             GetActivityBean.DataBean.ListBean bean = WSPKDataMananger.allActivityDataBean.getData().getList().get(i);
                             WSPKDataMananger.allActivityDataBean.getData().getList().remove(i);
                             WSPKDataMananger.allActivityDataBean.getData().getList().add(0, bean);
                             LogUtils.d("有正在直播的频道  "+endTime);
                         }
                         else if(timeStamp < startTime){
                             WSPKDataMananger.allActivityDataBean.getData().getList().get(i).setTime_status(1);
                         }
                     }

                    String json = Convert.toJson(WSPKDataMananger.allActivityDataBean.getData());

                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                            .callJs_setActivityList,  json);
                }

            }
        });
    }


    /*
 * 将时间转换为时间戳
 */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
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

