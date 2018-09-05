package com.gl.education.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.person.adapter.MyCompositionAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 个人中心 - 我的作文
 */
public class MyCompositionActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyCompositionAdapter adapter;
    private List<String> mList = new ArrayList<>();



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_compostion;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new MyCompositionAdapter(R.layout.item_mine_compoistion, mList);
        //设置数据
        recyclerView.setAdapter(adapter);
        //        //动画
        //        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //短按item子控件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }


}
