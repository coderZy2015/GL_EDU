package com.gl.education.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.JSAPPGetAriticleCommentEvent;
import com.gl.education.home.interactive.RecommendContentCommentInteractive;
import com.gl.education.home.model.GetArticleCommentBean;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/8/29.
 * 推荐的评论页面
 */

public class RecommendContentComment extends BaseFragment {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    protected AgentWeb mAgentWeb;

    private String url = "";
    private String channel_itemid = "";

    int type = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_recommend_content;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    public static RecommendContentComment newInstance(String url, String channel_itemid) {
        RecommendContentComment frag = new RecommendContentComment();
        Bundle b = new Bundle();

        b.putString("url", url);
        b.putString("channel_itemid", channel_itemid);

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
            channel_itemid = args.getString("channel_itemid");
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
        url = url + "?token="+token+"&channel_itemid="+channel_itemid;
        LogUtils.d(""+url);

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .closeIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.clearWebCache();
        type = AppCommonData.moreRecommendcontentID;
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RecommendContentCommentInteractive(mAgentWeb,
                getActivity(), type));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWebData(JSAPPGetAriticleCommentEvent event) {
        if (type != AppCommonData.moreRecommendcontentID){
            return;
        }
        HomeAPI.getArticleComment(event.getBean().getChannel_itemid(), "0", "0", "50", new JsonCallback<GetArticleCommentBean>() {

            @Override
            public void onSuccess(Response<GetArticleCommentBean> response) {
                GetArticleCommentBean bean = response.body();

                String json = Convert.toJson(bean.getData());

                mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                        .callJs_setCommentsData,  json);
            }
        });

    }
}
