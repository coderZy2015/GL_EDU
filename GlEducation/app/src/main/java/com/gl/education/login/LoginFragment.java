package com.gl.education.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.login.model.LoginBean;
import com.gl.education.login.presenter.LoginPresenter;
import com.gl.education.login.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/27.
 */

public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView{

    @BindView(R.id.btn_login)
    ImageButton btn_login;

    @BindView(R.id.btn_password)
    TextView btn_repeatPassword;

    @BindView(R.id.btn_register)
    TextView btn_register;

    @BindView(R.id.edit_usename)
    EditText edit_usename;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;


    String username = "";
    String password = "";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_login;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick(R.id.btn_login)
    public void clickLogin(){
        username = edit_usename.getText().toString();
        password = edit_password.getText().toString();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {// 判断密码
            // 以字母开头，长度在6~18之间，只能包含字母、数字和下划线。
            ToastUtils.showShort("账号密码不能为空");
        }else{
            mPresenter.toLogin(username, password);
        }
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
    }

    @OnClick(R.id.btn_register)
    public void clickRegister(){
        startForResult(RegisterFragment.newInstance(), 0);
        //start(RegisterFragment.newInstance());
    }

    @OnClick(R.id.btn_password)
    public void clickReatPassword(){
        startForResult(ForgetPasswordFragment.newInstance(), 1);
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        _mActivity.onBackPressed();
        _mActivity.finish();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        if (requestCode == 0){
            _mActivity.onBackPressed();
        }
        else if(requestCode == 1){
            _mActivity.onBackPressed();
        }
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        if (bean.getData() != null){
            AppCommonData.isLogin = true;
            ToastUtils.showShort("登录成功！");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("token", bean.getData().getToken());
            _mActivity.setResult(1000, resultIntent);
            _mActivity.finish();
        }else{
            ToastUtils.showShort("登陆出现问题，请重新尝试");
        }

    }

    @Override
    public void loginFail(String msg) {
        ToastUtils.showShort(msg);
    }
}
