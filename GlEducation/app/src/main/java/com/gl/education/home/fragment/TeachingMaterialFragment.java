package com.gl.education.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.gl.education.R;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.ToTeachingAssistantDetailPageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/11.
 * 教材fragment
 */

public class TeachingMaterialFragment extends BaseFragment{

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_teaching_assistant;
    }

    public static TeachingMaterialFragment newInstance() {
        Bundle args = new Bundle();

        TeachingMaterialFragment fragment = new TeachingMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        super.init();
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    //跳转到教辅详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookDetailEvent(ToTeachingAssistantDetailPageEvent event) {

    }

}
