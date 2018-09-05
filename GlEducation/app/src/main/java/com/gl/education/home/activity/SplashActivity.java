package com.gl.education.home.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.ApplyTokenBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.sp_bg)
    ImageView mSpBgImage;
    @BindView(R.id.sp_jump_btn)
    TextView mSpJumpBtn;

    boolean isLogin = false;
    private Loading_view loading;

    //由于CountDownTimer有一定的延迟，所以这里设置3400
    private CountDownTimer countDownTimer = new CountDownTimer(3400, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            gotoLoginOrMainActivity();
        }
    };

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        mSpJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();

        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @OnClick({R.id.sp_bg, R.id.sp_jump_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp_bg:
                break;
            case R.id.sp_jump_btn:
                gotoLoginOrMainActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    private void gotoLoginOrMainActivity() {
        countDownTimer.cancel();
        loading.show();
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        if (!token.equals("")){
            HttpHeaders headers = new HttpHeaders();
            headers.put("GL-Token", token);
            OkGo.getInstance().addCommonHeaders(headers);

            HomeAPI.loginAuth(new JsonCallback<ApplyTokenBean>() {
                @Override
                public void onSuccess(Response<ApplyTokenBean> response) {
                    if (response.body().getResult() == 1000){
                        loading.dismiss();
                        String token = response.body().getData().getToken();
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
                        OkGo.getInstance().getCommonHeaders().put("GL-Token", token);
                        AppCommonData.loginBackground = true;

                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        SPUtils.getInstance().put(AppConstant.SP_TOKEN, "");
                        AppCommonData.loginBackground = false;
                        //token无效 直接申请新token
                        applayToken(getDeviceId());
                    }
                }

                @Override
                public void onError(Response<ApplyTokenBean> response) {
                    super.onError(response);
                    ToastUtils.showShort("网络连接超时");
                    loading.dismiss();
                }
            });
        }
    }

    public void applayToken(String deviceId){
        HomeAPI.applyToken(deviceId, new JsonCallback<ApplyTokenBean>() {
            @Override
            public void onSuccess(Response<ApplyTokenBean> response) {
                if (response.body().getResult() == 1000){
                    loading.dismiss();
                    ApplyTokenBean bean = response.body();
                    SPUtils.getInstance().put(AppConstant.SP_TOKEN, bean.getData().getToken());

                    AppCommonData.loginBackground = true;
                    HttpHeaders headers = new HttpHeaders();
                    headers.put("GL-Token", bean.getData().getToken());
                    OkGo.getInstance().addCommonHeaders(headers);

                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onError(Response<ApplyTokenBean> response) {
                super.onError(response);
                ToastUtils.showShort("网络连接超时");
                loading.dismiss();
            }

        });
    }

    public String getDeviceId(){
        String deviceId = "";

        deviceId = DeviceUtils.getAndroidID();
        if (deviceId.equals("")){

        }
        return deviceId;
    }

}
