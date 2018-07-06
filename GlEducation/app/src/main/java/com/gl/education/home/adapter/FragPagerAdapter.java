package com.gl.education.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.model.TablayoutModel;
import com.gl.education.home.utlis.FragmentFactory;

import java.util.List;

/**
 * Created by zy on 2018/6/11.
 * ViewPager+Fragment 的适配器
 */

public class FragPagerAdapter extends FragmentPagerAdapter {
    private List<TablayoutModel> mFragShowIdList;

    public FragPagerAdapter(FragmentManager fm, List<TablayoutModel> _list) {
        super(fm);
        mFragShowIdList = _list;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        int index = mFragShowIdList.get(position).index;
        BaseFragment fragment = FragmentFactory.getFragment(index);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragShowIdList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragShowIdList.get(position).tabnName;
    }
}