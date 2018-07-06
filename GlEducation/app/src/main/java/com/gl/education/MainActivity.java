package com.gl.education;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.model.UserBean;
import com.gl.education.home.presenter.LoginRegistPresenter;
import com.gl.education.home.view.LoginRegistView;

public class MainActivity extends BaseActivity<LoginRegistView, LoginRegistPresenter> implements LoginRegistView {

    @Override
    protected LoginRegistPresenter createPresenter() {
        return new LoginRegistPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mPresenter.toLogin("123123", "123132");
    }

    @Override
    public void showProgress(String tipString) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loginSuccess(UserBean user) {
        ToastUtils.showShort("success");
    }

    @Override
    public void registerSuccess(UserBean user) {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void registerFail() {

    }
}
