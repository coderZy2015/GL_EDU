package com.gl.education.home.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.home.adapter.ChannelAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.event.UpdateChannelEvent;
import com.gl.education.home.helper.ItemDragHelperCallback;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.presenter.ChannelPresenter;
import com.gl.education.home.view.ChannelView;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 频道列表
 */
public class ChannelActivity extends BaseActivity<ChannelView, ChannelPresenter> implements
        ChannelView {
    public static final int FROM_BEGIN = 1;
    public static final int FROM_MAIN = 2;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.channel_title)
    TextView channel_title;

    public List<ChannelEntity> myChannelList = new ArrayList<>();
    public List<ChannelEntity> otherChannelList = new ArrayList<>();
    private ChannelAdapter adapter;

    public int openFrom = -1;

    private Loading_view loading;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_channel;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();
        openFrom = intent.getIntExtra("from", -1);
        if (openFrom == FROM_BEGIN){
            channel_title.setText("频道选择");
        }
        else if (openFrom == FROM_MAIN){
            channel_title.setText("频道选择");
        }
        loading.show();
        //请求全部频道
        mPresenter.getAllChannel(openFrom);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new ChannelAdapter(this, helper, myChannelList, otherChannelList);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter
                        .TYPE_OTHER ? 1 : 4;
            }
        });
        recyclerView.setAdapter(adapter);

        //我的频道点击事件
        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position <= myChannelList.size()-1){
                    if (openFrom == FROM_BEGIN){

                    }
                    else if(openFrom == FROM_MAIN){
                        UpdateChannelEvent event = new UpdateChannelEvent();
                        event.setSelectChannel(position);
                        EventBus.getDefault().post(event);
                        finish();
                    }

                }
            }
        });
    }


    @Override
    protected ChannelPresenter createPresenter() {
        return new ChannelPresenter();
    }

    @Override
    public void getChannelDataSuccess(List<ChannelEntity> mList, List<ChannelEntity> oList) {
        myChannelList.clear();
        otherChannelList.clear();
        myChannelList.addAll(mList);
        otherChannelList.addAll(oList);
        adapter.notifyDataSetChanged();
        loading.dismiss();
    }

    @Override
    public void getChannelDataErroer(List<ChannelEntity> oList) {
        myChannelList.clear();
        otherChannelList.clear();
        otherChannelList.addAll(oList);
        adapter.notifyDataSetChanged();
        loading.dismiss();
    }
}
