package com.gl.education.person.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;
import com.gl.education.home.model.GetOrderRecordBean;

import java.util.List;

/**
 * Created by zy on 2018/7/25.
 */

public class TransactionRecordAdapter extends BaseQuickAdapter<GetOrderRecordBean.DataBean.BuyOrderBean, BaseViewHolder> {

    public TransactionRecordAdapter(int layoutResId, @Nullable List<GetOrderRecordBean.DataBean.BuyOrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderRecordBean.DataBean.BuyOrderBean item) {
        helper.setText(R.id.record_name, ""+item.getContent());

        float price = ((float)item.getOrderPrice()/100);
        helper.setText(R.id.record_num, ""+price);
        helper.setText(R.id.record_data, ""+item.getCreatetime());
    }
}
