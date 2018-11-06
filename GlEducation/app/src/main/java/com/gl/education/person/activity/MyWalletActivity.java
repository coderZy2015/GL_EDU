package com.gl.education.person.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetDbCountBean;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.person.activity.RechargeCenterActivity;
import com.gl.education.person.activity.RechargeRecordActivity;
import com.gl.education.person.activity.TransactionActivity;
import com.gl.education.teachingassistant.event.JSJFBookRechargeOpenWebViewEvent;
import com.gl.education.widget.RoundImageView;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.wallet_bar)
    RoundImageView wallet_bar;

    @BindView(R.id.wallet_num)
    TextView wallet_num;

    @BindView(R.id.btn_wallet_transaction)
    LinearLayout btn_wallet_transaction;//交易记录

    @BindView(R.id.btn_recharge_center)
    LinearLayout btn_recharge_center;//充值中心

    @BindView(R.id.btn_wallet_recharge)
    LinearLayout btn_wallet_recharge;//充值记录

    private Loading_view loading;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        wallet_bar.setType(RoundImageView.TYPE_ROUND);
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);
        loading.show();
        HomeAPI.dbAmount(new JsonCallback<GetDbCountBean>() {
            @Override
            public void onSuccess(Response<GetDbCountBean> response) {
                loading.dismiss();
                if (response.body().getResult() == 1000){
                    double count = response.body().getData().getDbcount();
                    wallet_num.setText(""+count);
                }

            }

            @Override
            public void onError(Response<GetDbCountBean> response) {
                super.onError(response);
                loading.dismiss();
            }
        });
    }

    @OnClick(R.id.btn_wallet_transaction)
    public void intoTransaction(){
        if ( ButtonUtils.isFastDoubleClick(R.id.btn_wallet_transaction)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, TransactionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_wallet_recharge)
    public void intoRecharge(){
        if ( ButtonUtils.isFastDoubleClick(R.id.btn_wallet_recharge)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, RechargeRecordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_recharge_center)
    public void intoRechargeCenter(){
        if ( ButtonUtils.isFastDoubleClick(R.id.btn_recharge_center)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, RechargeCenterActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paySuccess(JSJFBookRechargeOpenWebViewEvent event) {
        HomeAPI.dbAmount(new JsonCallback<GetDbCountBean>() {
            @Override
            public void onSuccess(Response<GetDbCountBean> response) {

                if (response.body().getResult() == 1000){
                    double count = response.body().getData().getDbcount();
                    wallet_num.setText(""+count);
                }
            }

            @Override
            public void onError(Response<GetDbCountBean> response) {
                super.onError(response);
            }
        });
    }


    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }
}
