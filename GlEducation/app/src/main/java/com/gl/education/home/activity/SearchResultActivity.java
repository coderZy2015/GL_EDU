package com.gl.education.home.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.gl.education.R;
import com.gl.education.home.adapter.SearchResultAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索结果页面
 */
public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.tabLayout)
    TabLayout mTab;

    @BindView(R.id.vp_pager)
    ViewPager viewPager;

    private SearchResultAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressedSupport();
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }

    @Override
    public void initView() {
        super.initView();

        String searchName = getIntent().getStringExtra("name");

        adapter = new SearchResultAdapter(SearchResultActivity.this, getSupportFragmentManager(), searchName);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(viewPager);
    }
}


