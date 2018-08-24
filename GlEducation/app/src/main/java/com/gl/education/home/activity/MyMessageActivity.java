package com.gl.education.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.home.adapter.MyMessageAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyMessageAdapter adapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        mList.add("听说快到时间了");
        mList.add("听说你要出版本了？");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new MyMessageAdapter(R.layout.item_mine_message, mList);
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
}
