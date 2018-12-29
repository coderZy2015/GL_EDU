package com.gl.education.person.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;
import com.gl.education.home.model.CouponDataBean;

import java.util.List;

/**
 * Created by zy on 2018/11/15.
 */

public class CouponAdapter extends BaseQuickAdapter<CouponDataBean, BaseViewHolder> {

    public CouponAdapter(int layoutResId, @Nullable List<CouponDataBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CouponDataBean item) {
        helper.setText(R.id.act_title, item.getTitle());
        helper.setText(R.id.act_content, item.getContent());
        helper.setBackgroundRes(R.id.act_image, item.getPic_url());
    }
}
