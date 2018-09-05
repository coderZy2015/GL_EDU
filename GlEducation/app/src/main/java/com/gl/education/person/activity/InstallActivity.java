package com.gl.education.person.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gl.education.R;
import com.gl.education.home.activity.ChannelActivity;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心设置界面
 */
public class InstallActivity extends BaseActivity {

    @BindView(R.id.btn_lanmu_set)
    LinearLayout btn_lanmu_set;

    @BindView(R.id.btn_person_set)
    LinearLayout btn_person_set;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_install;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick(R.id.btn_lanmu_set)
    public void channelSet(){
        Intent intent = new Intent();
        intent.setClass(this, ChannelActivity.class);
        intent.putExtra("from", ChannelActivity.FROM_MAIN);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_person_set)
    public void personSet(){
        Intent intent = new Intent();
        intent.setClass(this, PersonDataActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    public void onClick(){
        onBackPressed();
        finish();
    }
}
