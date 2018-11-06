package com.gl.education.person.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.MyCompositionBean;
import com.gl.education.person.adapter.MyCompositionAdapter;
import com.gl.education.person.data.MycompositionDataManager;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 个人中心 - 我的作文
 */
public class MyCompositionActivity extends BaseActivity {

    public static final String compositionURL = "http://zuowen.hebeijiaoyu.com.cn/glwz";

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyCompositionAdapter adapter;
    private List<MyCompositionBean.DataBean.ListBean> mList = new ArrayList<>();

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new MyCompositionAdapter(R.layout.item_mine_compoistion, mList);
        //设置数据
        recyclerView.setAdapter(adapter);
        //        //动画
        //        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //短按item子控件

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).getIs_check() == 0){
                    ToastUtils.showShort("请等待老师批改");
                }else{
                    Intent intent = new Intent();
                    intent.setClass(MyCompositionActivity.this, MyCompositionDetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });

        HomeAPI.getCompositionList(new JsonCallback<MyCompositionBean>() {
            @Override
            public void onSuccess(Response<MyCompositionBean> response) {
                if (response.body().getResult() == 1000){
                    mList.addAll(response.body().getData().getList());
                    MycompositionDataManager.bean = response.body().getData();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }


}
