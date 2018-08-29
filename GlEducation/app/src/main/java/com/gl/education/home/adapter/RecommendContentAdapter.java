package com.gl.education.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gl.education.app.AppConstant;
import com.gl.education.home.fragment.RecommendContentComment;
import com.gl.education.home.fragment.RecommendContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/8/29.
 */

public class RecommendContentAdapter extends FragmentPagerAdapter {
    FragmentManager mFragmentManager;
    private Context mContext;
    private List<String> urlList = new ArrayList<>();
    private String channel_itemid = "";

    public RecommendContentAdapter(Context context, FragmentManager fm, String id) {
        super(fm);
        mContext = context;
        mFragmentManager = fm;
        channel_itemid = id;
        urlList.add(AppConstant.YY_WEB_DETAIL);
        urlList.add(AppConstant.YY_WEB_COMMENT);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = RecommendContentFragment.newInstance(urlList.get(position), channel_itemid);
        } else if (position == 1) {
            fragment = RecommendContentComment.newInstance(urlList.get(position), channel_itemid);
        }

        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return urlList == null ? 0 : urlList.size();
    }

}
