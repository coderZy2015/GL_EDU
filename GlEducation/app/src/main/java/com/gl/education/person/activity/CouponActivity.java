package com.gl.education.person.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.CouponDataBean;
import com.gl.education.person.adapter.CouponAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CouponActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CouponAdapter adapter;
    private List<CouponDataBean> mList = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        initDData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new CouponAdapter(R.layout.list_coupon, mList);
        //设置数据
        recyclerView.setAdapter(adapter);
        //        //动画
        //        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //短按item子控件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(CouponActivity.this, DetailActivity.class);
                intent.putExtra("url", mList.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.btn_back)
    public void onClick(){
        onBackPressed();
        finish();
    }

    public void initDData(){
        CouponDataBean bean = new CouponDataBean();
        bean.setTitle("超级海鲜盛宴");
        bean.setPic_url(R.drawable.act_pc1);
        bean.setContent("超级好吃的火锅现在正式上架啦，只需1块钱即可立享豪华版海鲜大餐！更有啤酒饮料不限量供应！快来试一试吧！");
        bean.setUrl("https://shop800874.youzan.com/wscshop/goods/2xbfletohc9hu?step=2&redirect_count=1&sf=wx_sm&is_share=1");

        mList.add(bean);
    }
}
