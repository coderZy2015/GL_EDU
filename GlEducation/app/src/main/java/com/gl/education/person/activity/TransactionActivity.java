package com.gl.education.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.person.adapter.TransactionRecordAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetOrderRecordBean;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 交易记录
 */
public class TransactionActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    TransactionRecordAdapter adapter;
    List<GetOrderRecordBean.DataBean.BuyOrderBean> mList = new ArrayList<>();

    private Loading_view loading;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_transaction;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new TransactionRecordAdapter(R.layout.item_transaction_record, mList);
        //设置数据
        recyclerView.setAdapter(adapter);
        //短按item子控件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        loading.show();
        HomeAPI.getOrderRecord(new JsonCallback<GetOrderRecordBean>() {
            @Override
            public void onSuccess(Response<GetOrderRecordBean> response) {
                loading.hide();
                if (response.body().getResult() == 1000){
                    mList.clear();
                    mList.addAll(response.body().getData().getBuyOrder());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response<GetOrderRecordBean> response) {
                super.onError(response);
                loading.hide();
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }
}
