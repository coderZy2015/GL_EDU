package com.gl.education.home.activity;

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
import com.gl.education.widget.RoundImageView;
import com.lzy.okgo.model.Response;

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

        HomeAPI.dbAmount(new JsonCallback<GetDbCountBean>() {
            @Override
            public void onSuccess(Response<GetDbCountBean> response) {
                if (response.body().getResult() == 1000){
                    double count = response.body().getData().getDbcount();
                    wallet_num.setText(""+count);
                }

            }
        });
    }

    @OnClick(R.id.btn_wallet_transaction)
    public void intoTransaction(){
        Intent intent = new Intent();
        intent.setClass(this, TransactionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }
}
