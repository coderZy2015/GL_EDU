package com.gl.education.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gl.education.R;
import com.gl.education.home.adapter.FragPagerAdapter;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.model.TablayoutModel;
import com.gl.education.home.presenter.HomePagePresenter;
import com.gl.education.home.utlis.FragmentFactory;
import com.gl.education.home.view.HomePageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/6.
 */

public class HomePageFragment extends BaseFragment<HomePageView, HomePagePresenter> implements
        HomePageView{

    @BindView(R.id.tabLayout)
    TabLayout mTab;

    @BindView(R.id.vp_pager)
    ViewPager viewPager;

    private int selectId = 0;

    /**
     * 记录频道排序的id
     */
    public List<TablayoutModel> mFragShowIdList = new ArrayList<>();

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    protected HomePagePresenter createPresenter() {
        return new HomePagePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_home_page;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        //排序的list
        mFragShowIdList.clear();
        for (int i = 0; i < 7; i++) {
            TablayoutModel model = new TablayoutModel();
            model.index = i;
            model.tabnName = FragmentFactory.getTabName(i);
            mFragShowIdList.add(model);
        }

        //mTab.addTab(mTab.newTab());
        FragPagerAdapter adapter = new FragPagerAdapter(getChildFragmentManager(), mFragShowIdList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(viewPager);
        //reflex(mTab);
    }

    @Override
    public TabLayout getTabLayout() {
        return mTab;
    }

    @Override
    public boolean onBackPressedSupport() {
        LogUtils.d("onBackPressedSupport");
        return super.onBackPressedSupport();
    }


    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 15);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
