package com.gl.education.home.activity;

import android.support.v7.widget.RecyclerView;

import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import butterknife.BindView;

public class InterestChoiceActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_interest_choice;
    }

    @Override
    public void initView() {
        super.initView();
    }

}
