package com.gl.education.person.activity;

import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.versionCode)
    TextView versionName;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        versionName.setText(""+ AppCommonData.versionName);
    }
}
