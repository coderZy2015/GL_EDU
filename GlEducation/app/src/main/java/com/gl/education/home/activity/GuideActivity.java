package com.gl.education.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.home.adapter.GuidePageAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.model.ApplyTokenBean;
import com.gl.education.home.presenter.GuidePresenter;
import com.gl.education.home.view.GuideView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 开机5连图
 */
public class GuideActivity extends BaseActivity<GuideView, GuidePresenter> implements GuideView{

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.btn_enter)
    Button guide_start;

    @BindView(R.id.bottom_indicator)
    MagicIndicator magicIndicator;

    private ArrayList<Integer> imageList;
    private List<View> pages;

    @Override
    protected GuidePresenter createPresenter() {
        return new GuidePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        if (!token.equals("")){
            Intent intent = new Intent();
            intent.setClass(this, HomePageActivity.class);
            startActivity(intent);
            finish();

            return;
        }

        imageList = new ArrayList<>();
        imageList.add(R.drawable.open1);
        imageList.add(R.drawable.open2);
        imageList.add(R.drawable.open3);
        imageList.add(R.drawable.open4);
        pages = getPages();

        initViewPager();
        initIndicator();
    }

    private List<View> getPages() {
        List<View> pages = new ArrayList<>();
         for (int id : imageList){
             ImageView view = new ImageView(this);
             view.setBackgroundResource(id);

             view.setScaleType(ImageView.ScaleType.CENTER_CROP);
             pages.add(view);
          }

        return pages;
    }

    public void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter adapter = new GuidePageAdapter(pages);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageList.size()-1){
                    guide_start.setVisibility(View.VISIBLE);
                }else{
                    guide_start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {

        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return imageList == null ? 0 : imageList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                clipPagerTitleView.setText("");
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });

        viewPager.setCurrentItem(1);

    }


    @OnClick(R.id.btn_enter)
    public void onClick(){

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");

        //无存储token 第一次进入app
        if (token.equals("")){
            LogUtils.d("deviceId = "+getDeviceId());
            mPresenter.applayToken(getDeviceId());
        }else{

        }

    }

    public String getDeviceId(){
        String deviceId = "";

        deviceId = DeviceUtils.getAndroidID();
        if (deviceId.equals("")){

        }
        return deviceId;
    }


    @Override
    public void getTokenSuccess(ApplyTokenBean bean) {
        if (bean != null){
            if (bean.getData() != null){
                SPUtils.getInstance().put(AppConstant.SP_TOKEN, bean.getData().getToken());

                HttpHeaders headers = new HttpHeaders();
                headers.put("GL-Token", bean.getData().getToken());
                OkGo.getInstance().addCommonHeaders(headers);

                boolean firstEnter = SPUtils.getInstance().getBoolean(AppConstant.SP_FIRST_ENTER, true);
                if (firstEnter){
                    AppCommonData.loginBackground = true;
                    Intent intent = new Intent();
                    intent.setClass(this, ChannelActivity.class);
                    intent.putExtra("from",ChannelActivity.FROM_BEGIN);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }
    }

    @Override
    public void getTokenError() {
        SPUtils.getInstance().put(AppConstant.SP_TOKEN, "");
    }
}
