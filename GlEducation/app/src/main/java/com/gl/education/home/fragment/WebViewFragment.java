package com.gl.education.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;

/**
 * Created by zy on 2018/6/11.
 */

public class WebViewFragment extends BaseFragment{

    public static WebViewFragment newInstance() {
        Bundle args = new Bundle();

        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_webview;
    }

    @Override
    public void initData() {
        LogUtils.d("initData");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LogUtils.d("onLazyInitView");
        ToastUtils.showShort("lazyInitView");
    }
}
