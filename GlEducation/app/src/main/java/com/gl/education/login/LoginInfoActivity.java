package com.gl.education.login;

import android.os.Bundle;

import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class LoginInfoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        String into = getIntent().getStringExtra("into");

        if (into != null){
            if (into.equals("forget")){
                if (findFragment(LoginFragment.class) == null) {
                    loadRootFragment(R.id.login_container, ForgetPasswordFragment.newInstance());  // 加载根Fragment
                }
                return;
            }
        }

        if (findFragment(LoginFragment.class) == null) {
            loadRootFragment(R.id.login_container, LoginFragment.newInstance());  // 加载根Fragment
        }

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
