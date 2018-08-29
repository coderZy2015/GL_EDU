package com.gl.education.home.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @BindView(R.id.guide_btn)
    TextView guide_start;

    @BindView(R.id.main_linear)
    LinearLayout mLinearLayout;

    private ArrayList<Integer> imageList;
    //ImageView动态数组
    private List<ImageView> mImageViewList = new ArrayList<ImageView>();
    private int mNum = 0;

    private boolean clickLogin = false;

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
        getData();
        initViewPager();

        //第一次显示小白点
        mLinearLayout.getChildAt(0).setEnabled(true);
    }

    /**
     * 获取数据
     */
    private void getData() {
        //设置图片
        ImageView imageView;
        View view;
        for (int pic : imageList) {
            imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(pic);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //添加到数组
            mImageViewList.add(imageView);

            //创建底部指示器(小圆点)
            view = new View(GuideActivity.this);
            view.setBackgroundResource(R.drawable.background);
            view.setEnabled(false);
            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            //设置间隔
            if (pic != imageList.get(0)) {
                layoutParams.leftMargin = 10;
            }
            //添加到LinearLayout
            mLinearLayout.addView(view, layoutParams);
        }
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
        PagerAdapter adapter = new GuidePageAdapter(mImageViewList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mLinearLayout.getChildAt(mNum).setEnabled(false);
                mLinearLayout.getChildAt(position).setEnabled(true);
                mNum = position;

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

    @OnClick(R.id.guide_btn)
    public void onClick(){
        if (clickLogin){
            return;
        }
        if (!clickLogin){
            clickLogin = true;
        }

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

    @Override
    public void getTokenError() {
        SPUtils.getInstance().put(AppConstant.SP_TOKEN, "");
    }
}
