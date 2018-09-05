package com.gl.education.person.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.person.adapter.MyMessageAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetUserMsgBean;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyMessageAdapter adapter;
    private List<GetUserMsgBean.DataBean> mList = new ArrayList<>();

    private Loading_view loading;

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

        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);
        loading.show();
        HomeAPI.getUserMsg(new JsonCallback<GetUserMsgBean>() {
            @Override
            public void onSuccess(Response<GetUserMsgBean> response) {
                loading.dismiss();
                if (response.body().getResult() == 1000){
                    mList.clear();
                    mList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response<GetUserMsgBean> response) {
                super.onError(response);
                loading.dismiss();
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onClick(){
        onBackPressed();
        finish();
    }
}
