package com.gl.education.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.utlis.TimeButton;
import com.gl.education.login.model.IdentifyBean;
import com.gl.education.login.model.LoginBean;
import com.gl.education.login.model.RegisterBean;
import com.gl.education.login.presenter.RegisterPresenter;
import com.gl.education.login.view.RegisterView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterWxFragment extends BaseFragment<RegisterView, RegisterPresenter> implements RegisterView {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    //注册按钮
    @BindView(R.id.btn_register)
    ImageButton btn_register;

    @BindView(R.id.register_get_code)
    TimeButton register_get_code;
    @BindView(R.id.btn_agree)
    ImageView btn_agree;
    @BindView(R.id.btn_agreement)
    TextView btn_agreement;
    @BindView(R.id.edit_usename)
    EditText edit_usename;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_identify)
    EditText edit_identify;

    private Loading_view loading;

    private String username = "";
    private String password = "";
    private String identify_code = "";
    private String unid = "";
    boolean isSend = false;
    private boolean isAgree = true;

    public static RegisterWxFragment newInstance(String unid) {
        RegisterWxFragment frag = new RegisterWxFragment();
        Bundle b = new Bundle();

        b.putString("unid", unid);

        frag.setArguments(b);
        return frag;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_wx_fragment;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }


    @OnClick(R.id.btn_back)
    public void backPressed(){
        _mActivity.onBackPressed();

    }

    @OnClick(R.id.btn_register)
    public void clickRegisterBtn(){
        username = edit_usename.getText().toString();
        password = edit_password.getText().toString();
        identify_code = edit_identify.getText().toString();
        if(TextUtils.isEmpty(username)){// 判断密码
            // 以字母开头，长度在6~18之间，只能包含字母、数字和下划线。
            ToastUtils.showShort("账号不能为空");
            return;
        }else if(TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }else if(TextUtils.isEmpty(identify_code)){
            ToastUtils.showShort("验证码不能为空");
            return;
        }else if(!isAgree){
            ToastUtils.showShort("请查看协议");
            return;
        }
        mPresenter.toWXRegister(username, password, identify_code, unid);
    }

    @OnClick(R.id.btn_agree)
    public void agreeBtn(){
        //同意协议
        if (isAgree){
            isAgree = false;
            btn_agree.setBackgroundResource(R.drawable.register_disagree);
        }else{
            isAgree = true;
            btn_agree.setBackgroundResource(R.drawable.register_agree);
        }
    }

    @OnClick(R.id.btn_agreement)
    public void intoAgreement(){
        //进入协议详情
    }

    @OnClick(R.id.register_get_code)
    public void getDentifyCode(){
        username = edit_usename.getText().toString();

        if (username.length() == 11) {
            register_get_code.startTime();
            mPresenter.getIdentifyCode(username, "regist");
        } else if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入手机号码！");
        } else {
            ToastUtils.showShort("长度不够11位数字");
        }

    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        register_get_code.setTextAfter("秒后重新获取").setTextBefore("点击发送验证码").setLenght(60 * 1000);
        Bundle args = getArguments();
        if (args != null) {
            unid = args.getString("unid");
        }
        loading = new Loading_view(getActivity(), com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @Override
    public void registerSuccess(RegisterBean bean) {
    }

    @Override
    public void registerFail(String msg) {
    }

    @Override
    public void sendCode(IdentifyBean bean) {
        ToastUtils.showShort("短信已发送");
    }

    @Override
    public void sendCodeError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void registerWXSuccess(RegisterBean bean) {

        String token = bean.getData().getToken();
        SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
        OkGo.getInstance().getCommonHeaders().put("GL-Token", token);

        HomeAPI.login(username, password, new JsonCallback<LoginBean>() {
            @Override
            public void onSuccess(Response<LoginBean> response) {
                loading.dismiss();
                LoginBean responseData = response.body();
                if (responseData.getResult() == 1000){
                    ToastUtils.showShort("注册成功");

                    String token = responseData.getData().getToken();
                    SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
                    OkGo.getInstance().getCommonHeaders().put("GL-Token", token);

                    //通知个人中心刷新
                    UpdateUserDataEvent event = new UpdateUserDataEvent();
                    EventBus.getDefault().post(event);

                    AppCommonData.isLogin = true;
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("token", responseData.getData().getToken());
                    _mActivity.setResult(1000, resultIntent);
                    _mActivity.finish();
                }
            }

            @Override
            public void onError(Response<LoginBean> response) {
                super.onError(response);
                loading.dismiss();
            }
        });

    }

    @Override
    public void registerWXFail(String msg) {
        ToastUtils.showShort(msg);
        loading.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //register_get_code.onDestroy();
    }
}
