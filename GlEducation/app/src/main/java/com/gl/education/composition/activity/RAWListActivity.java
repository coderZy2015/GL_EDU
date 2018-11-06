package com.gl.education.composition.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.event.JSgetListEvent;
import com.gl.education.composition.event.JSopenArticleEvent;
import com.gl.education.composition.interactive.RAWListInteractive;
import com.gl.education.composition.model.GetListByCatalogBean;
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

public class RAWListActivity extends BaseActivity {

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";
    
    public String first = "";
    public String second = "";

    String type;//热点2  满分3  案例4
    
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

        first = intent.getStringExtra("first");
        second = intent.getStringExtra("second");
        type = intent.getStringExtra("type");
        top_title.setText("素材列表");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_RAW_LIST + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RAWListInteractive(mAgentWeb,
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
    public void getList(JSgetListEvent event) {
        HomeAPI.getListByCatalog(type, first, second, "1", "1000", new JsonCallback<GetListByCatalogBean>() {
            @Override
            public void onSuccess(Response<GetListByCatalogBean> response) {
                if (response.body().getResult() == 1000){
                    String json = Convert.toJson(response.body().getData());

                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                            .callJs_setList,  json);
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openArticle(JSopenArticleEvent event) {
        int article = event.getBean().getArticle();

        Intent intent = new Intent();
        intent.setClass(this, RAWDetailActivity.class);
        intent.putExtra("xh", article);
        startActivity(intent);

    }

}
