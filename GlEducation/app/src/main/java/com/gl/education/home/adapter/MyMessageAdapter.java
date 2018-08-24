package com.gl.education.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;

import java.util.List;

/**
 * Created by zy on 2018/8/23.
 */

public class MyMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MyMessageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.my_message_content, item);

        //helper.setText(R.id.my_message_name, item);
    }
}