package com.uuzuche.lib_zxing.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.uuzuche.lib_zxing.R;

/**
 * Initial the camera
 * <p>
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout search_camera; //拍照搜题
    private RelativeLayout cover_camera;  //拍封面
    private RelativeLayout search_isbn;   //扫ISBN
    private RelativeLayout Scanning_code; //扫描码

    CaptureFragment captureFragment;
    SearchCameraFragment searchCameraFragment;
    SearchCoverFragment searchCoverFragment;
    SearchISBNFragment searchISBNFragment;

    ImageView image_search;
    ImageView image_cover;
    ImageView image_isbn;
    ImageView image_scan;

    int selectId = 0;

    LinearLayout cameraBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        initView();

        SearchCameraFragment searchCameraFragment = SearchCameraFragment.newInstance();
        searchCameraFragment.setAnalyzeCallback(photographCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container,
                searchCameraFragment).commit();
    }

    public void initView() {

        search_camera = findViewById(R.id.search_camera);
        cover_camera = findViewById(R.id.cover_camera);
        search_isbn = findViewById(R.id.search_isbn);
        Scanning_code = findViewById(R.id.Scanning_code);

        cameraBottom = findViewById(R.id.camera_bottom);

        image_search = findViewById(R.id.image_search);
        image_cover = findViewById(R.id.image_cover);
        image_isbn = findViewById(R.id.image_isbn);
        image_scan = findViewById(R.id.image_scan);

        search_camera.setOnClickListener(this);
        cover_camera.setOnClickListener(this);
        search_isbn.setOnClickListener(this);
        Scanning_code.setOnClickListener(this);

    }

    public void cameraHide(){
        cameraBottom.setVisibility(View.GONE);
    }
    public void cameraShow(){
        cameraBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        setSelectFragment(v.getId());

        if (v.getId() == R.id.search_camera) {
            selectId = 0;
            SearchCameraFragment searchCameraFragment = SearchCameraFragment.newInstance();
            searchCameraFragment.setAnalyzeCallback(photographCallback);
            transaction.replace(R.id.fl_zxing_container, searchCameraFragment);
            transaction.commit();

        } else if (v.getId() == R.id.cover_camera) {
            selectId = 1;
            SearchCoverFragment searchCoverFragment = SearchCoverFragment.newInstance();
            searchCoverFragment.setAnalyzeCallback(photographCallback);
            transaction.replace(R.id.fl_zxing_container, searchCoverFragment);

            transaction.commit();
        } else if (v.getId() == R.id.search_isbn) {
            selectId = 2;
            SearchISBNFragment searchISBNFragment = SearchISBNFragment.newInstance();
            searchISBNFragment.setAnalyzeCallback(isbnCallback);
            searchISBNFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
                @Override
                public void callBack(Exception e) {
                    if (e == null) {
                    } else {
                    }
                }
            });
            transaction.replace(R.id.fl_zxing_container, searchISBNFragment);

            transaction.commit();
        } else if (v.getId() == R.id.Scanning_code) {
            selectId = 3;
            CaptureFragment captureFragment = CaptureFragment.newInstance();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
                @Override
                public void callBack(Exception e) {
                    if (e == null) {
                    } else {
                    }
                }
            });
            transaction.replace(R.id.fl_zxing_container, captureFragment);

            transaction.commit();
        }

    }


    public void setSelectFragment(int id) {
        image_search.setBackgroundResource(R.drawable.camera_search);
        image_cover.setBackgroundResource(R.drawable.camera_cover);
        image_isbn.setBackgroundResource(R.drawable.camera_isbn);
        image_scan.setBackgroundResource(R.drawable.camera_scan);

        if (id == R.id.search_camera) {
            image_search.setBackgroundResource(R.drawable.camera_search_s);
        } else if (id == R.id.cover_camera) {
            image_cover.setBackgroundResource(R.drawable.camera_cover_s);
        } else if (id == R.id.search_isbn) {
            image_isbn.setBackgroundResource(R.drawable.camera_isbn_s);
        } else if (id == R.id.Scanning_code) {
            image_scan.setBackgroundResource(R.drawable.camera_scan_s);
        }

    }

    CodeUtils.PhotographCallback photographCallback = new CodeUtils.PhotographCallback(){

        @Override
        public void onPotographSuccess(String path, String result) {

            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            bundle.putString("path", path);
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1001, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onPhotographFailed(String path) {

            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            bundle.putString("path", path);
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1001, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void cancle() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_CANCLE);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            CaptureActivity.this.setResult(1001, resultIntent);
            CaptureActivity.this.finish();
        }
    };


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);

            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1004, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1004, resultIntent);
            CaptureActivity.this.finish();
        }
    };

    /**
     * isbn解析回调函数
     */
    CodeUtils.AnalyzeCallback isbnCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);

            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1003, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(1003, resultIntent);
            CaptureActivity.this.finish();
        }
    };

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_CANCLE);
        resultIntent.putExtras(bundle);
        CaptureActivity.this.setResult(1005, resultIntent);
        CaptureActivity.this.finish();

    }
}