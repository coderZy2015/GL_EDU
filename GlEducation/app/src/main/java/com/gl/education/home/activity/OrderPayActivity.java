package com.gl.education.home.activity;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.model.DbDeductBean;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderPayActivity extends BaseActivity {

    @BindView(R.id.pay_sure)
    TextView click_sure;

    @BindView(R.id.one_price_yue)
    TextView one_price_yue;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;


    String guid, grade_id, price, count;

    @Override
    public void initView() {
        super.initView();

        price = getIntent().getStringExtra("price");
        guid = getIntent().getStringExtra("guid");
        grade_id = getIntent().getStringExtra("grade_id");
        count = getIntent().getStringExtra("count");

        one_price_yue.setText("账户余额 "+count);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }

    @OnClick(R.id.pay_sure)
    public void onPay(){
        HomeAPI.db_deduct("" + price, guid,"拍照搜题1元支付", "" + grade_id,
                "-1", new JsonCallback<DbDeductBean>() {
                    @Override
                    public void onSuccess(Response<DbDeductBean> response) {
                        if (response.body().getResult() == 1000) {
                            if (response.body().getData().getCode() == 1) {
                                setResult(1000);
                                finish();
                                ToastUtils.showShort("支付成功");
                            } else {
                                ToastUtils.showShort("支付失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<DbDeductBean> response) {
                        super.onError(response);
                    }
                });
    }

}
