package com.gl.education.camera.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.camera.adapter.CameraSearchAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenCameraEvent;
import com.gl.education.home.event.OpenJFChannelEvent;
import com.gl.education.home.utlis.MyViewPager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
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

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    MyViewPager mViewPager;

    @BindView(R.id.camera_search_again)
    ImageView camera_search_again;
    @BindView(R.id.camera_search_in_jiaofu)
    ImageView camera_search_in_jiaofu;

    @BindView(R.id.layout_error)
    RelativeLayout layout_error;


    private PhotographResultBean dataBean;

    ArrayList<String> mTitleDataList = new ArrayList<>();

    private int[] tabIcons = {
            R.drawable.select_tab1,
            R.drawable.select_tab2,
            R.drawable.select_tab3,
            R.drawable.select_tab4
    };

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_camera;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        if (openCamera) {
            openCamera = false;
            Intent intent = new Intent(CameraActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    @OnClick(R.id.c_result_close)
    public void closeView() {
        onBackPressed();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CANCLE) {
            if (data != null) {
                Bundle bundle = data.getExtras();

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_CANCLE) {
                    finish();
                }
            }
        }

        if (resultCode == PHOTOGRAPH) {//处理拍照搜题结果

            if (data != null) {
                Bundle bundle = data.getExtras();

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //拍照的图片
                    String path = bundle.getString("path");
                    Bitmap bm = getDiskBitmap(path);
                    if (bm != null) {
                        camera_photo.setImageBitmap(bm);
                        delFile(path);
                    }

                    try {
                        Gson gson = new Gson();
                        PhotographResultBean bean = gson.fromJson(result, PhotographResultBean
                                .class);
                        if (bean != null) {
                            if (bean.getData() != null) {
                                if (bean.getData().size() == 0) {
                                    layout_error.setVisibility(View.VISIBLE);
                                }
                            } else {
                                layout_error.setVisibility(View.VISIBLE);
                                return;
                            }

                            CameraSearchAdapter adapter = new CameraSearchAdapter(this,
                                    getSupportFragmentManager(), bean.getData());
                            mViewPager.setAdapter(adapter);
                            mViewPager.setOffscreenPageLimit(3);
                            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }
                                @Override
                                public void onPageSelected(int position) {
                                    mViewPager.resetHeight(position);
                                }
                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });

                            tabLayout.setupWithViewPager(mViewPager);

                            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                                tabLayout.getTabAt(i).setCustomView(getTabView(i));
                            }

                        } else {
                            layout_error.setVisibility(View.VISIBLE);
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        ToastUtils.showShort("解析结果失败");
                    }

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    //拍照的图片
                    String path = bundle.getString("path");
                    Bitmap bm = getDiskBitmap(path);
                    if (bm != null) {
                        camera_photo.setImageBitmap(bm);
                        delFile(path);
                    }

                    layout_error.setVisibility(View.VISIBLE);
                    Toast.makeText(CameraActivity.this, "搜索结果失败", Toast.LENGTH_LONG).show();
                }

            }
        }
        else if(resultCode == SEARCHCOVER){//处理拍封面结果
            layout_error.setVisibility(View.VISIBLE);
            Toast.makeText(CameraActivity.this, "搜索结果失败", Toast.LENGTH_LONG).show();
        }
        else if(resultCode == SEARCHISBN){//扫ISBN结果
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);

                    Intent intent = new Intent();
                    intent.setClass(CameraActivity.this, CameraResultISBNActivity.class);
                    intent.putExtra("isbn", result);
                    startActivity(intent);
                    finish();

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    layout_error.setVisibility(View.VISIBLE);
                    Toast.makeText(CameraActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }

        }
        else if (resultCode == CAPTURE) {//扫码结果
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.showShort(""+result);
                    Intent intent = new Intent();
                    intent.setClass(CameraActivity.this, CameraResultSMActivity.class);
                    intent.putExtra("id", result);
                    startActivity(intent);
                    finish();

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    layout_error.setVisibility(View.VISIBLE);
                    Toast.makeText(CameraActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.camera_result_layout, null);
        ImageView img_title = (ImageView) view.findViewById(R.id.result_num);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    @OnClick(R.id.camera_search_in_jiaofu)
    public void openJF(){
        OpenJFChannelEvent event = new OpenJFChannelEvent();
        EventBus.getDefault().post(event);
        finish();
    }

    @OnClick(R.id.camera_search_again)
    public void cameraAgain(){
        OpenCameraEvent event = new OpenCameraEvent();
        EventBus.getDefault().post(event);
        finish();

    }


    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    //删除图片
    public void delFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile()) {
            file.delete();
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + fileName + "'";
        mContentResolver.delete(uri, where, null);

    }
}
