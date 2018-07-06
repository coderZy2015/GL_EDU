package com.gl.education.camera.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraResultActivity extends BaseActivity {

    @BindView(R.id.c_result_close)
    RelativeLayout btn_close;

    @BindView(R.id.camera_photo)
    ImageView camera_photo;

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        if(intent !=null)
        {


        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_camera_result;
    }

    @OnClick(R.id.c_result_close)
    public void closeView(){
        onBackPressed();
        finish();
    }
}
