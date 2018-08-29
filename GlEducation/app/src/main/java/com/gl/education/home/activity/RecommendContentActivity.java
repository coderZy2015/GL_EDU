package com.gl.education.home.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.home.adapter.RecommendContentAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RecommendContentActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    public String bookTitle = "";

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    RecommendContentAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_recommend_content;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        int channel_itemid = intent.getIntExtra("channel_itemid", 0);
        bookTitle = intent.getStringExtra("title");
        if (bookTitle!=null)
            top_title.setText(""+bookTitle);

        adapter = new RecommendContentAdapter(this, getSupportFragmentManager(), ""+channel_itemid);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(1);

        viewpager.setCurrentItem(0);  //初始化显示第一个页面
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressed();
        finish();
    }

}
