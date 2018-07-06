package com.gl.education.camera.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.camera.adapter.CameraSearchAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.utlis.MyViewPager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity {

    public static final int PHOTOGRAPH = 1001;      //拍照返回的结果标识
    public static final int SEARCHCOVER = 1002;     //拍封面返回的结果标识
    public static final int SEARCHISBN = 1003;      //扫ISBN返回的结果标识
    public static final int CAPTURE = 1004;         //扫一维码二维码返回的结果标识
    public static final int CANCLE = 1005;         //扫一维码二维码返回的结果标识

    private boolean openCamera = true;

    @BindView(R.id.c_result_close)
    RelativeLayout btn_close;

    @BindView(R.id.camera_photo)
    ImageView camera_photo;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.view_pager)
    MyViewPager mViewPager;

    @BindView(R.id.camera_search_error)
    TextView camera_search_error;

    private PhotographResultBean dataBean;

    ArrayList<String> mTitleDataList = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_camera;
    }

    @Override
    public void initView() {
        super.initView();

        if (openCamera){
            openCamera = false;
            Intent intent = new Intent(CameraActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 0);
        }

    }

    @OnClick(R.id.c_result_close)
    public void closeView(){
        onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtils.d("requestCode = " + requestCode + "  resultCode = " + resultCode);
        if (requestCode == 0) {

            if (resultCode == CANCLE){
                if (data != null) {
                    Bundle bundle = data.getExtras();

                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_CANCLE) {
                        finish();
                    }
                }
            }

            if (resultCode == PHOTOGRAPH) {

                if (data != null) {
                    //拍照的图片
                    byte[] bis = data.getByteArrayExtra("bitmap");
                    if (bis != null){
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
                        camera_photo.setImageBitmap(bitmap);
                    }

                    Bundle bundle = data.getExtras();

                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);

                        try {
                            Gson gson = new Gson();
                            PhotographResultBean bean = gson.fromJson(result, PhotographResultBean
                                    .class);
                            if (bean != null){
                                if ( bean.getData() != null){
                                    if (bean.getData().size() == 0){
                                        camera_search_error.setVisibility(View.VISIBLE);
                                    }
                                }else{
                                    camera_search_error.setVisibility(View.VISIBLE);
                                    return;
                                }

                                CameraSearchAdapter adapter = new CameraSearchAdapter(getSupportFragmentManager(), bean.getData());
                                mViewPager.setAdapter(adapter);
                                mViewPager.setOffscreenPageLimit(2);
//                                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                                    @Override
//                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                                    }
//                                    @Override
//                                    public void onPageSelected(int position) {
//                                        mViewPager.resetHeight(position);
//                                    }
//                                    @Override
//                                    public void onPageScrollStateChanged(int state) {
//                                    }
//                                });

                                for (int i=0; i<bean.getData().size(); i++){
                                    mTitleDataList.add("No."+i);
                                }
                                initMagicIndicator();
                                ViewPagerHelper.bind(magicIndicator, mViewPager);

                            }else{
                                camera_search_error.setVisibility(View.VISIBLE);
                            }

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            ToastUtils.showShort("解析结果失败");
                        }

                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        camera_search_error.setVisibility(View.VISIBLE);
                        Toast.makeText(CameraActivity.this, "搜索结果失败", Toast.LENGTH_LONG).show();
                    }

                }
            } else if (resultCode == CAPTURE) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(CameraActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }

    public void initMagicIndicator()
    {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }


}
