package com.gl.education.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.login.model.LoginBean;

import java.util.List;

/**
 * Created by zy on 2018/7/25.
 */

public class TransactionRecordAdapter extends BaseQuickAdapter<LoginBean, BaseViewHolder> {

    public TransactionRecordAdapter(int layoutResId, @Nullable List<LoginBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LoginBean item) {

    }
}
