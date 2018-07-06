package com.gl.education.home.fragment;

import android.content.Intent;
import android.widget.Button;

import com.gl.education.R;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.LoginInfoActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/7.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.btn_test)
    Button tbn_test;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_mine;
    }

    @OnClick(R.id.btn_test)
    public void onClick(){
        Intent intenet = new Intent();
        intenet.setClass(_mActivity, LoginInfoActivity.class);
        _mActivity.startActivity(intenet);
    }

}
