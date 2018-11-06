package com.gl.education.composition.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.event.CloseViewEvent;
import com.gl.education.composition.event.JSgetPayParamEvent;
import com.gl.education.composition.event.JSrequestPayEvent;
import com.gl.education.composition.event.JSrequestPayMoneyEvent;
import com.gl.education.composition.interactive.RAWPayInteractive;
import com.gl.education.composition.model.CommonDataBean;
import com.gl.education.composition.model.DbDeductBean;
import com.gl.education.composition.model.PayInfo;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.person.activity.RechargeCenterActivity;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RAWPayActivity extends BaseActivity {


    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    private double count;
    private int price;
    private String orderId;

    private Loading_view loading;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rawpay;
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

        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();

        count = intent.getDoubleExtra("count", 0);
        price = intent.getIntExtra("price", 0);
        orderId = intent.getStringExtra("orderId");

        top_title.setText("支付");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token=" + token;
        String url = AppConstant.YY_WEB_COMPOSITION_RAW_PAY + token;

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
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RAWPayInteractive(mAgentWeb,
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPayParam(JSgetPayParamEvent event) {
        PayInfo info = new PayInfo();
        info.lastMoney = count * 100;
        info.price = price * 100;
        String json = Convert.toJson(info);
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setPayParam, json);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestPayMoney(JSrequestPayMoneyEvent event) {
        ToastUtils.showShort("余额不足");
        Intent intent = new Intent();
        intent.setClass(this, RechargeCenterActivity.class);
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickPayButton(JSrequestPayEvent event) {
        loading.show();
        HomeAPI.db_deduct("" + price * 100, "zuowen", "作文批改支付",
                "-100", "-1", new JsonCallback<DbDeductBean>() {
                    @Override
                    public void onSuccess(Response<DbDeductBean> response) {
                        if (response.body().getResult() == 1000) {
                            if (response.body().getData().getCode() == 1) {
                                HomeAPI.afterPayCallback(orderId, new
                                        JsonCallback<CommonDataBean>() {
                                    @Override
                                    public void onSuccess(Response<CommonDataBean> response) {
                                        loading.dismiss();
                                        if (response.body().getResult() == 1000) {
                                            dialog();
                                        }
                                    }

                                    @Override
                                    public void onError(Response<CommonDataBean> response) {
                                        super.onError(response);
                                        loading.dismiss();
                                    }
                                });
                            } else {
                                ToastUtils.showShort("支付失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<DbDeductBean> response) {
                        super.onError(response);
                        loading.dismiss();
                    }
                });

    }

    public void dialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.dialog_sure, null);

        AlertDialog costDialog = new AlertDialog.Builder(this).create();
        costDialog.setCancelable(false);
        costDialog.show();

        Window window = costDialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        costDialog.setContentView(layout);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的4/5
        window.setAttributes(lp);

        Button btn_sure = layout.findViewById(R.id.cost_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costDialog.dismiss();
                CloseViewEvent close = new CloseViewEvent();
                EventBus.getDefault().post(close);
                CompositionDataManager.data.clear();
                finish();
            }
        });
    }


}
