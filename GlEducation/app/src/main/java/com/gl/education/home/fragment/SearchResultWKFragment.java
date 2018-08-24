package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSSearchWKOpenWebViewEvent;
import com.gl.education.home.interactive.SearchWKInteractive;
import com.gl.education.smallclass.activity.WKBookContentActivity;
import com.just.agentweb.AgentWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/8/23.
 */

public class SearchResultWKFragment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    protected AgentWeb mAgentWeb;

    private String url = "";
    private String searchStr = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_search_result;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    public static SearchResultWKFragment newInstance(String url, String searchStr) {
        SearchResultWKFragment frag = new SearchResultWKFragment();
        Bundle b = new Bundle();

        b.putString("url", url);
        b.putString("searchStr", searchStr);

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
            url = args.getString("url");
            searchStr = args.getString("searchStr");
        }
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

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token+"&grade="+ AppCommonData.userGrade+ "&name="+searchStr;

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

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new SearchWKInteractive(mAgentWeb,
                getActivity()));

    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookMenuEvent(JSSearchWKOpenWebViewEvent event) {
        Intent intent = new Intent();
        intent.putExtra("url", event.getBean().getUrl());
        intent.putExtra("title", event.getBean().getTitle());
        intent.setClass(getActivity(), WKBookContentActivity.class);
        startActivity(intent);

    }
}
