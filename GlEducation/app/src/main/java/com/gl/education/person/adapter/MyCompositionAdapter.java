package com.gl.education.person.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;

import java.util.List;

/**
 * Created by zy on 2018/7/27.
 */

public class MyCompositionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyCompositionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.my_composition_name, item);
    }
}
