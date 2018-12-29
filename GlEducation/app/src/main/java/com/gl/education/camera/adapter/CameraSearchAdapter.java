package com.gl.education.camera.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gl.education.camera.PhotographResultBean;
import com.gl.education.camera.frag.ShowResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/6/29.
 */

public class CameraSearchAdapter extends FragmentPagerAdapter {
    private List<PhotographResultBean.DataBean> mFragShowIdList = new ArrayList<>();
    FragmentManager mFragmentManager;
    private Context mContext;


    public CameraSearchAdapter(Context context, FragmentManager fm, List<PhotographResultBean.DataBean> _list) {
        super(fm);
        mContext = context;
        mFragmentManager = fm;
        mFragShowIdList = _list;
    }

    @Override
    public Fragment getItem(int position) {
        PhotographResultBean.DataBean bean = mFragShowIdList.get(position);
        return ShowResultFragment.newInstance(bean);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mFragShowIdList == null ? 0 : mFragShowIdList.size();
    }

}
