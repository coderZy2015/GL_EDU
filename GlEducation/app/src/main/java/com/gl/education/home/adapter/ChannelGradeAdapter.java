package com.gl.education.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gl.education.R;

import java.util.List;

/**
 * Created by zy on 2018/7/27.
 */

public class ChannelGradeAdapter extends BaseQuickAdapter<ChannelAdapter.GradeData, BaseViewHolder>{

    public int selectPostion = 0;

    public ChannelGradeAdapter(int layoutResId, @Nullable List<ChannelAdapter.GradeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelAdapter.GradeData item) {
        helper.setText(R.id.grade_tv, item.gradeStr);
        if (helper.getLayoutPosition() == selectPostion){
            helper.setTextColor(R.id.grade_tv, mContext.getResources().getColor(R.color.mine_top));
            helper.setBackgroundRes(R.id.grade_tv, R.drawable.bg_channel_p);
        }else{
            helper.setTextColor(R.id.grade_tv, mContext.getResources().getColor(R.color.channel_item));
            helper.setBackgroundRes(R.id.grade_tv, R.drawable.bg_channel_n);
        }

        //helper.setTextColor(R.id.tv, mContext.getResources().getColor(R.color.white));
        //        helper.setImageResource(R.id.icon, item.getImageResource());
        //        // 加载网络图片
        //        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
    }

    public void setSelectPostion(int _p){
        selectPostion = _p;
        notifyDataSetChanged();
    }



}
