package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.UM_EVENT;
import com.gl.education.composition.activity.RAWCaseColumnActivity;
import com.gl.education.composition.activity.RAWFullScoreColumnActivity;
import com.gl.education.composition.activity.RAWHotColumnActivity;
import com.gl.education.composition.activity.RAWUploadActivity;
import com.gl.education.composition.activity.TeacherDetailActivity;
import com.gl.education.composition.event.JSComGetTeacherListData;
import com.gl.education.composition.event.JSComGetTeacherNewsListData;
import com.gl.education.composition.event.JSComOpenTeacherDetail;
import com.gl.education.composition.event.JSComToUploadComposise;
import com.gl.education.composition.event.JSCompositionDropDownEvent;
import com.gl.education.composition.event.JSCompositionLoadMoreEvent;
import com.gl.education.composition.event.JSCompositionOpenNewsViewEvent;
import com.gl.education.composition.event.JSEnterSubColumnEvent;
import com.gl.education.composition.model.GetTeacherListBean;
import com.gl.education.composition.model.GetTeacherNewsListBean;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.RecommendContentActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.interactive.CompositionInterative;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.login.LoginInfoActivity;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 作文fragment
 */

public class CompositionFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    protected AgentWeb mAgentWeb;

    String url = "";

    String token = "";

    private ChannelEntity channelEntity;

    private String teacherNum = "1";

    private boolean isCanDropDown = false;
    private boolean isCanLoadMore = false;

    private int pageNum = 1;

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


    public static CompositionFragment newInstance(ChannelEntity entity) {
        CompositionFragment frag = new CompositionFragment();
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

        if (!AppCommonData.isClickZW){
            AppCommonData.isClickZW = true;
            MobclickAgent.onEvent(_mActivity, UM_EVENT.UM_click_channel_zw);
        }

        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;

        url = AppConstant.YY_WEB_COMPOSITION;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .closeIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url+token);

        //LogUtils.d(""+url+token);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new CompositionInterative(mAgentWeb,
                getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAgentWeb.clearWebCache();
                mAgentWeb.getWebCreator().getWebView().reload();    //刷新
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                pageNum = 1;
                isCanDropDown = false;
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(500);
                pageNum++;
                JSComGetTeacherNewsListData event = new JSComGetTeacherNewsListData();
                EventBus.getDefault().post(event);
                isCanLoadMore = false;
            }
        });

        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                if (isCanDropDown){
                    isCanDropDown = false;
                    return true;
                }
                return false;
            }

            @Override
            public boolean canLoadMore(View content) {
                if (isCanLoadMore){
                    isCanLoadMore = false;
                    return true;
                }
                return false;
            }
        });
    }

    //获取教师列表数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTeacherListData(JSComGetTeacherListData event) {
        HomeAPI.getTeacherList(teacherNum, "25", new JsonCallback<GetTeacherListBean>() {
            @Override
            public void onSuccess(Response<GetTeacherListBean> response) {
                if (response.body().getResult() == 1000){

                    CompositionDataManager.teacherListBean = response.body();

                    String json = Convert.toJson(CompositionDataManager.teacherListBean.getData());

                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                            .callJs_setTeacherListData,  json);
                }
            }
        });
    }


    //获取教师最新消息列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestListData(JSComGetTeacherNewsListData event) {
        HomeAPI.getRecomHomeByUserTag("5", ""+pageNum, new JsonCallback<GetTeacherNewsListBean>() {
            @Override
            public void onSuccess(Response<GetTeacherNewsListBean> response) {
                if (response.body().getResult() == 1000){

                    if (response.body().getData().getList().size() == 0){
                        refreshLayout.setEnableLoadMore(false);
                        ToastUtils.showShort("没有更多数据");
                        return;
                    }

                    String json = Convert.toJson(response.body().getData());

                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                            .callJs_setTeacherNewsListData,  json);
                }else{
                    ToastUtils.showShort("加载失败，刷新重试");
                }

            }
        });
    }


    //打开教师内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openTeacherDetail(JSComOpenTeacherDetail event) {
        String rs_guid = event.getBean().getId();
        Intent intent = new Intent();
        intent.setClass(getActivity(), TeacherDetailActivity.class);
        intent.putExtra("id", rs_guid);
        startActivity(intent);
    }

    //跳转到资讯详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openWebView(JSCompositionOpenNewsViewEvent event) {
        Intent intent = new Intent();
        intent.putExtra("url",event.getBean().getUrl());
        intent.putExtra("title", event.getBean().getTitle());
        intent.putExtra("channel_itemid", ""+event.getBean().getChannel_itemid());
        intent.setClass(getActivity(), RecommendContentActivity.class);
        startActivity(intent);
    }


    //上传
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toUploadComposise(JSComToUploadComposise event) {
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent();
        intent.setClass(getActivity(), RAWUploadActivity.class);
        intent.putExtra("intent",false);
        startActivity(intent);

    }


    //跳转
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void enterSubColumn(JSEnterSubColumnEvent event) {
        int type = event.getBean().getType();
        if (type == 2){
            Intent intent = new Intent();
            intent.setClass(getActivity(), RAWHotColumnActivity.class);
            startActivity(intent);
        }else if(type == 3){
            Intent intent = new Intent();
            intent.setClass(getActivity(), RAWFullScoreColumnActivity.class);
            startActivity(intent);
        }else if(type == 4){
            Intent intent = new Intent();
            intent.setClass(getActivity(), RAWCaseColumnActivity.class);
            startActivity(intent);
        }
    }

    //下拉刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dropDownEvent(JSCompositionDropDownEvent event) {
        isCanDropDown = true;
    }

    //上拉加载
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadMoreEvent(JSCompositionLoadMoreEvent event) {
        isCanLoadMore = true;
    }

}
