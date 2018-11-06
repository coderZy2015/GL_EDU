package com.gl.education.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gl.education.app.AppConstant;
import com.gl.education.home.fragment.SearchResultJCFragment;
import com.gl.education.home.fragment.SearchResultJFFragment;
import com.gl.education.home.fragment.SearchResultWKFragment;
import com.gl.education.home.fragment.SearchResultZXFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/8/23.
 */

public class SearchResultAdapter extends FragmentPagerAdapter {

  FragmentManager mFragmentManager;
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private String srarchName;
    public SearchResultAdapter(Context context, FragmentManager fm, String name) {
        super(fm);
        mContext = context;
        mFragmentManager = fm;
        srarchName = name;
        mList.add("资讯");
        mList.add("教材");
        mList.add("教辅");
        mList.add("微课");
        urlList.add(AppConstant.search_tuijian);
        urlList.add(AppConstant.search_jiaocai);
        urlList.add(AppConstant.search_jiaofu);
        urlList.add(AppConstant.search_weike);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0){
            fragment = SearchResultZXFragment.newInstance(urlList.get(position), srarchName);
        }
        else if (position == 1){
            fragment = SearchResultJCFragment.newInstance(urlList.get(position), srarchName);
        }
        else if (position == 2){
            fragment = SearchResultJFFragment.newInstance(urlList.get(position), srarchName);
        }
        else if (position == 3){
            fragment = SearchResultWKFragment.newInstance(urlList.get(position), srarchName);
        }else{
            //fragment = SearchResultJFFragment.newInstance(urlList.get(position), srarchName);
        }
        return fragment;

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

}
