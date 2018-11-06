package com.gl.education.person.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;
import com.gl.education.home.model.MyCompositionBean;

import java.util.List;

/**
 * Created by zy on 2018/7/27.
 */

public class MyCompositionAdapter extends BaseQuickAdapter<MyCompositionBean.DataBean.ListBean, BaseViewHolder> {

    public MyCompositionAdapter(int layoutResId, @Nullable List<MyCompositionBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCompositionBean.DataBean.ListBean item) {
        helper.setText(R.id.my_composition_name, ""+item.getTitle());

        if (item.getIs_check() == 0){
            helper.setText(R.id.my_composition_state, "未批改");
        }else{
            helper.setText(R.id.my_composition_state, "已批改");
        }
        helper.setText(R.id.my_composition_teacher, ""+item.getTeacher_name());
        if (item.getType() == 1){//1评语    2详批
            helper.setText(R.id.my_composition_type, "评语");
        }else{
            helper.setText(R.id.my_composition_type, "详解");
        }


    }

}
