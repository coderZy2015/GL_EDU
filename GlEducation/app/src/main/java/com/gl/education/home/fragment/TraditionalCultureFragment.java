package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.UM_EVENT;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.RecommendContentActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSRecommendDropDownEvent;
import com.gl.education.home.event.JSRecommendLoadMoreEvent;
import com.gl.education.home.event.JSRecommentOpenWebViewEvent;
import com.gl.education.home.event.JSRecommentRequest;
import com.gl.education.home.interactive.RecommendInteractive;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.RecommendRequestBean;
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
 * Created by zy on 2018/12/10.
 * 传统文化 频道
 */

public class TraditionalCultureFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    protected AgentWeb mAgentWeb;

    String url = "";

    String token = "";

    private ChannelEntity channelEntity;

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


    public static TraditionalCultureFragment newInstance(ChannelEntity entity) {
        TraditionalCultureFragment frag = new TraditionalCultureFragment();
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

        if (!AppCommonData.isClickCTWH){
            AppCommonData.isClickCTWH = true;
            MobclickAgent.onEvent(_mActivity, UM_EVENT.UM_click_channel_ctwh);
        }

        token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;

        //url = AppConstant.YY_WEB_RECOMMEND;
        LogUtils.d("url = "+url+token);

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

        //LogUtils.d(url+token);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RecommendInteractive(mAgentWeb,
                getActivity()));

        //refreshLayout.setEnableRefresh(false);
        //refreshLayout.setEnableLoadMore(false);
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
                    pageNum++;
                    JSRecommentRequest event = new JSRecommentRequest();
                    EventBus.getDefault().post(event);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onBackPressedSupport() {
        //        boolean isBack = mAgentWeb.back();
        //        return isBack;
        return false;
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openWebView(JSRecommentOpenWebViewEvent event) {
        LogUtils.d("首页面跳转");
        Intent intent = new Intent();
        intent.putExtra("channel_itemid", ""+event.getBean().getChannel_itemid());
        intent.setClass(getActivity(), RecommendContentActivity.class);
        startActivity(intent);
    }

    //获取数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(JSRecommentRequest event) {
        HomeAPI.getRecomHomeByUserTag("7",""+pageNum, new JsonCallback<RecommendRequestBean>() {
            @Override
            public void onSuccess(Response<RecommendRequestBean> response) {
                if (response.body().getResult() == 1000){
                    RecommendRequestBean.DataBean bean = response.body().getData();
                    if (bean.getList().size() == 0){
                        refreshLayout.setEnableLoadMore(false);
                        ToastUtils.showShort("没有更多数据");
                        return;
                    }

                    String json = Convert.toJson(bean);

                    if (mAgentWeb != null){
                        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                .callJs_loadData,  json);
                    }else{
                        ToastUtils.showShort("加载失败，刷新重试");
                    }
                }
            }

            @Override
            public void onError(Response<RecommendRequestBean> response) {
                super.onError(response);
            }
        });
    }

    //下拉刷新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dropDown(JSRecommendDropDownEvent event) {
        isCanDropDown = true;
    }

    //加载更多
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadMore(JSRecommendLoadMoreEvent event) {
        isCanLoadMore = true;
    }

}
