package com.gl.education.camera.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.gl.education.camera.activity.PhotographResultBean;
import com.gl.education.camera.frag.ShowResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/6/29.
 */

public class CameraSearchAdapter extends FragmentPagerAdapter {
    private List<PhotographResultBean.DataBean> mFragShowIdList = new ArrayList<>();
    FragmentManager mFragmentManager;
    //保存每个Fragment的Tag，刷新页面的依据
    protected SparseArray<String> tags = new SparseArray<>();


    public CameraSearchAdapter(FragmentManager fm, List<PhotographResultBean.DataBean> _list) {
        super(fm);
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        String tag = fragment.getTag();
        //保存每个Fragment的Tag
        tags.put(position, tag);
        return fragment;
    }

    //拿到指定位置的Fragment
    public Fragment getFragmentByPosition(int position) {
        return mFragmentManager.findFragmentByTag(tags.get(position));
    }

    public List<Fragment> getFragments(){
        return mFragmentManager.getFragments();
    }
}
