package com.gl.education.composition.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.composition.event.CloseViewEvent;
import com.gl.education.composition.event.JSgetCompositionDataEvent;
import com.gl.education.composition.event.JSrequestComImageEvent;
import com.gl.education.composition.event.JSrequestTeacherParamEvent;
import com.gl.education.composition.event.JSsetCompositionTitleEvent;
import com.gl.education.composition.event.JSuploadCompositionEvent;
import com.gl.education.composition.event.RefreshViewEvent;
import com.gl.education.composition.interactive.RAWUploadInteractive;
import com.gl.education.composition.model.CompositionCallback;
import com.gl.education.composition.model.CompositionPreorderBean;
import com.gl.education.composition.model.UploadCallbackBean;
import com.gl.education.composition.model.UploadPictureBean;
import com.gl.education.composition.utils.BitmapUtils;
import com.gl.education.composition.utils.CameraUtils;
import com.gl.education.composition.utils.CompositionDataManager;
import com.gl.education.composition.utils.CreatePictureDialog;
import com.gl.education.composition.utils.FileUtils;
import com.gl.education.helper.Convert;
import com.gl.education.helper.DialogHelper;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetDbCountBean;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RAWUploadActivity extends BaseActivity implements CreatePictureDialog.ResultListener {

    public final String uploadPic = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";    //OSS的路径

    public final String ZWJJP_url = "http://zuowen.hebeijiaoyu.com.cn/glwz";    //作文及时批的路径

    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    private CreatePictureDialog picture_dialog;

    private Loading_view loading;

    private String teacherID = "";
    private String type = "";   //1评语  2详批
    private String teacherName = "";
    private String picturePath;
    /**
     * 相机，图库的请求code
     */
    public final static int PICTURE_CODE = 10;
    public final static int GALLERY_CODE = 11;

    private boolean isHeader = false;
    private boolean isContent = false;

    private String imageType = "";

    private boolean intentData = false;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rawupload;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        // 注册订阅者
        EventBus.getDefault().register(this);

        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();
        intentData = intent.getBooleanExtra("intent", false);
        if (intentData == false){
            teacherID = CompositionDataManager.data.teacherID;
            type = CompositionDataManager.data.rawType;
            teacherName = CompositionDataManager.data.teacherName;
        }else{
            teacherID = intent.getStringExtra("id");
            type = intent.getStringExtra("type");
            teacherName = intent.getStringExtra("name");

            try {
                if (!teacherID.equals("")){
                    CompositionDataManager.data.teacherID = teacherID;
                }
                if (!type.equals("")){
                    CompositionDataManager.data.rawType = type;
                }
                if (!teacherName.equals("")){
                    CompositionDataManager.data.teacherName = teacherName;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        top_title.setText("作文上传");

        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN);
        token = "?token="+token;
        String url = AppConstant.YY_WEB_COMPOSITION_RAW_UPLOAD + token;

        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        //mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new RAWUploadInteractive(mAgentWeb,
                this));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        mAgentWeb.getWebLifeCycle().onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressedSupport();
    }

    @Override
    public void onBackPressedSupport() {
        //        boolean isBack = false;
        //        isBack = mAgentWeb.back();
        //        if (isBack == true)
        //            return;

        super.onBackPressedSupport();
    }

    //更新页面数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCompositionData(JSgetCompositionDataEvent event) {

        String json = Convert.toJson(CompositionDataManager.data);
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setCompositionData,  json);
    }

    //0是作文要求   1是作文内容
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestComImage(JSrequestComImageEvent event) {
        imageType = event.getBean().getImgType();

        requestPermission();
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestTeacherParam(JSrequestTeacherParamEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, RAWTeacherListActivity.class);
        startActivity(intent);
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
                                if (imageType.equals("0")){
                                    CompositionDataManager.data.tImage = uploadPic+response.body().getData().getPath();
                                    String json = Convert.toJson(CompositionDataManager.data);
                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                            .callJs_setCompositionData,  json);
                                }else{
                                    CompositionDataManager.data.cImage.add(uploadPic + response.body().getData().getPath());

                                    String json = Convert.toJson(CompositionDataManager.data);
                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                            .callJs_setCompositionData,  json);
                                }
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
                                if (imageType.equals("0")){
                                    CompositionDataManager.data.tImage = uploadPic+response.body().getData().getPath();
                                    String json = Convert.toJson(CompositionDataManager.data);
                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                            .callJs_setCompositionData,  json);

                                }else{
                                    CompositionDataManager.data.cImage.add(uploadPic + response.body().getData().getPath());

                                    String json = Convert.toJson(CompositionDataManager.data);
                                    mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                                            .callJs_setCompositionData,  json);
                                }
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


    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadComposition(JSuploadCompositionEvent event) {
        String money = "";
        if (CompositionDataManager.data.rawType.equals("1")){
            money = "15";
        }else{
            money = "30";
        }
        final int price = Integer.parseInt(money);
        loading.show();
        //获取作文的预下单号
        HomeAPI.getPreorder(CompositionDataManager.data.title, CompositionDataManager.data.teacherID
                , CompositionDataManager.data.rawType, money, new JsonCallback<CompositionPreorderBean>() {
                    @Override
                    public void onSuccess(Response<CompositionPreorderBean> response) {
                        if (response.body().getResult() == 1000){
                            String orderId = response.body().getData().getRw_orderid();
                            //上传文件到作文及时批
                            HomeAPI.compositionUpload(CompositionDataManager.data.upFile, new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    CompositionCallback bean = Convert.fromJson(response.body(), CompositionCallback.class);
                                    //进行映射对比
                                    HomeAPI.uploadCallback(orderId, bean.getFilePath(), new JsonCallback<UploadCallbackBean>() {

                                        @Override
                                        public void onSuccess(Response<UploadCallbackBean> response) {

                                            if (response.body().getResult() == 1000){

                                                HomeAPI.dbAmount(new JsonCallback<GetDbCountBean>() {
                                                    @Override
                                                    public void onSuccess(Response<GetDbCountBean> response) {
                                                        loading.dismiss();
                                                        if (response.body().getResult() == 1000){
                                                            double count = response.body().getData().getDbcount();
                                                            //进入支付

                                                            Intent intent = new Intent();
                                                            intent.setClass(RAWUploadActivity.this, RAWPayActivity.class);
                                                            intent.putExtra("count", count);
                                                            intent.putExtra("price", price);
                                                            intent.putExtra("orderId", orderId);
                                                            startActivity(intent);

                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Response<GetDbCountBean> response) {
                                                        super.onError(response);
                                                        loading.dismiss();
                                                    }
                                                });

                                            }
                                        }

                                        @Override
                                        public void onError(Response<UploadCallbackBean> response) {
                                            super.onError(response);
                                            loading.dismiss();
                                        }
                                    });

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    loading.dismiss();
                                }
                            });
                        }

                    }

                    @Override
                    public void onError(Response<CompositionPreorderBean> response) {
                        super.onError(response);
                        loading.dismiss();
                    }
                });


    }

    //每次刷新频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshView(RefreshViewEvent event) {
        String json = Convert.toJson(CompositionDataManager.data);
        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                .callJs_setCompositionData,  json);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCompositionTitle(JSsetCompositionTitleEvent event) {
        CompositionDataManager.data.title = event.getBean().getTitle();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCompositionTitle(CloseViewEvent event) {
        finish();
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
                            picture_dialog = new CreatePictureDialog(RAWUploadActivity.this);
                            picture_dialog.setResultListener(RAWUploadActivity.this);
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
    public static void compressBmpToFile(Bitmap bmp,File file){

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
