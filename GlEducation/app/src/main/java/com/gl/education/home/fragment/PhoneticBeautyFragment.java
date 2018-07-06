package com.gl.education.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gl.education.R;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;

/**
 * Created by zy on 2018/6/11.
 * 音体美fragment
 */

public class PhoneticBeautyFragment extends BaseFragment {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_webview;
    }

    public static PhoneticBeautyFragment newInstance() {
        Bundle args = new Bundle();

        PhoneticBeautyFragment fragment = new PhoneticBeautyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
