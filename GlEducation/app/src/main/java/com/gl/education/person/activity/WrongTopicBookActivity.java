package com.gl.education.person.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.model.UploadPictureBean;
import com.gl.education.composition.utils.BitmapUtils;
import com.gl.education.composition.utils.CameraUtils;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.composition.utils.CreatePictureDialog;
import com.gl.education.composition.utils.FileUtils;
import com.gl.education.helper.DialogHelper;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.person.interactive.WrongTopicBookInteractive;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 错题本
 */
public class WrongTopicBookActivity extends BaseActivity implements CreatePictureDialog.ResultListener {

    /**
     * 相机，图库的请求code
     */
    public final static int PICTURE_CODE = 10;
    public final static int GALLERY_CODE = 11;

    protected AgentWeb mAgentWeb;

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    private String username = "";

    private CreatePictureDialog picture_dialog;

    private String picturePath;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wrong_topic_book;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        String url = AppConstant.ctb_url + username;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                //.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                //.setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new WrongTopicBookInteractive(mAgentWeb,
                this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        setResult(1);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        boolean isBack = false;
        isBack = mAgentWeb.back();
        if (isBack == true)
            return;
        //返回刷新页面
        setResult(1);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("onActivityResult  requestCode = "+requestCode);
        switch (requestCode) {
            //拍照返回
            case PICTURE_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = BitmapUtils.decodeFileBitmap(this, picturePath);
                    File file = new File(picturePath);
                    compressBmpToFile(bitmap, file);
                    LogUtils.d("path = "+picturePath);
                    CompositionDataManager.data.upFile.add(file);
                    HomeAPI.uploadPicture(file, new JsonCallback<UploadPictureBean>() {
                        @Override
                        public void onSuccess(Response<UploadPictureBean> response) {
                            if (response.body().getResult() == 1000){
//                                if (imageType.equals("0")){
//                                    String json = Convert.toJson(CompositionDataManager.data);
//                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
//                                            .callJs_setCompositionData,  json);
//                                }else{
//
//                                    String json = Convert.toJson(CompositionDataManager.data);
//                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
//                                            .callJs_setCompositionData,  json);
//                                }
                                //deleteFile(picturePath);
                            }

                        }
                    });
                }
                break;
            //图库返回
            case GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    String path = CameraUtils.uriConvertPath(this, uri);
                    Bitmap bitmap = BitmapUtils.decodeFileBitmap(this, path);
                    File file = new File(path);
                    LogUtils.d("path = "+path);
                    compressBmpToFile(bitmap, file);
                    CompositionDataManager.data.upFile.add(file);
                    HomeAPI.uploadPicture(file, new JsonCallback<UploadPictureBean>() {
                        @Override
                        public void onSuccess(Response<UploadPictureBean> response) {
                            if (response.body().getResult() == 1000){
//                                if (imageType.equals("0")){
//                                    String json = Convert.toJson(CompositionDataManager.data);
//                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
//                                            .callJs_setCompositionData,  json);
//
//                                }else{
//                                    String json = Convert.toJson(CompositionDataManager.data);
//                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
//                                            .callJs_setCompositionData,  json);
//                                }
                                //deleteFile(picturePath);
                            }

                        }
                    });
                }
                break;
            default:

                break;
        }
    }


    //获取拍照权限  存储读取sd卡的权限
    public void requestPermission() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {//被拒绝再次发起请求
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (picture_dialog == null) {
                            picture_dialog = new CreatePictureDialog(WrongTopicBookActivity.this);
                            picture_dialog.setResultListener(WrongTopicBookActivity.this);
                        }
                        if (!picture_dialog.isShowing()) {
                            picture_dialog.show();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        ToastUtils.showShort("onDenied");
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .theme(new PermissionUtils.ThemeCallback() {
                    @Override
                    public void onActivityCreate(Activity activity) {
                        ScreenUtils.setFullScreen(activity);
                    }
                })
                .request();
    }

    @Override
    public void camera() {
        this.picturePath = FileUtils.getBitmapDiskFile(this.getApplicationContext());
        CameraUtils.openCamera(this, PICTURE_CODE, this.picturePath);
    }

    @Override
    public void photoAlbum() {
        CameraUtils.openGallery(this, GALLERY_CODE);
    }


    /**
     * 质量压缩
     * @param bmp bitmap 对象
     * @param file 路径文件
     */
    public static void compressBmpToFile(Bitmap bmp, File file){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 1000) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
