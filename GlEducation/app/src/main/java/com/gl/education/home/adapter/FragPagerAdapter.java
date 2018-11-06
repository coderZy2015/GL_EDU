package com.gl.education.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.utlis.FragmentFactory;

import java.util.List;

/**
 * Created by zy on 2018/6/11.
 * ViewPager+Fragment 的适配器
 */

public class FragPagerAdapter extends FragmentStatePagerAdapter {
    private List<ChannelEntity> mFragShowIdList;
    private FragmentManager fragmentManager;
    private BaseFragment mCurrentFragment;


    public FragPagerAdapter(FragmentManager fm, List<ChannelEntity> _list) {
        super(fm);
        fragmentManager = fm;
        mFragShowIdList = _list;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;//必须返回的是POSITION_NONE，否则不会刷新
        //return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = FragmentFactory.getFragment(mFragShowIdList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragShowIdList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragShowIdList.get(position).getName();
    }

}