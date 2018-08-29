package com.gl.education.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;
import com.gl.education.home.model.GetOrderRecordBean;

import java.util.List;

/**
 * Created by zy on 2018/8/29.
 */

public class RechargeRecordAdapter extends BaseQuickAdapter<GetOrderRecordBean.DataBean.RechargeOrderBean, BaseViewHolder> {

    public RechargeRecordAdapter(int layoutResId, @Nullable List<GetOrderRecordBean.DataBean.RechargeOrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderRecordBean.DataBean.RechargeOrderBean item) {
        helper.setText(R.id.record_name, item.getContent());

        //helper.setText(R.id.record_num, item.getOrderPrice());
        helper.setText(R.id.record_data, item.getCreatetime());
    }
}
