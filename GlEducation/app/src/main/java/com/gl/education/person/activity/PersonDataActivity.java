package com.gl.education.person.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.TransmitPersonDataEvent;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.model.UploadBean;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.home.utlis.ImageLoader;
import com.gl.education.login.LoginInfoActivity;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonDataActivity extends BaseActivity {

    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.edit_name)
    TextView edit_name;

    @BindView(R.id.user_phone)
    TextView user_phone;

    @BindView(R.id.exit_account)
    ImageView exit_count;

    @BindView(R.id.change_password)
    LinearLayout change_password;

    @BindView(R.id.user_xb)
    TextView user_xb;

    private String nickName = "";
    private String pic_url = "";
    private String xb = "0";

    private Loading_view loading;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_person_data;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick({R.id.profile_image})
    public void getPicture(){
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1000);
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        UpdateUserDataEvent event = new UpdateUserDataEvent();
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void initView() {
        super.initView();
        // 注册订阅者
        EventBus.getDefault().register(this);
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(TransmitPersonDataEvent event) {
        if (!event.getNickName().equals(""))
            edit_name.setText("" + event.getNickName());
            nickName = event.getNickName();
        if (event.getUrl().equals("")){
            Glide.with(this).load(R.drawable.ic_photo).into(profile_image);
        }else{
            ImageLoader.loadImage(this, AppConstant.downloadUrl + event.getUrl(), profile_image);
        }

        if (!event.getPhone().equals("")){
            String mobile = event.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            user_phone.setText(""+mobile);
        }

        if (!event.getXb().equals("")){
            String xb = event.getXb();
            if (xb.equals("0")){
                xb = "男";
            }
            if (xb.equals("1")){
                xb = "女";
            }
            user_xb.setText(xb);
        }
    }

    //更换账号
    @OnClick(R.id.exit_account)
    public void exitAccount(){
        //loading.show();
        Intent intent = new Intent();
        intent.setClass(this, LoginInfoActivity.class);
        startActivityForResult(intent, 1003);
//        HomeAPI.logout(new JsonCallback<logoutBean>() {
//            @Override
//            public void onSuccess(Response<logoutBean> response) {
//                loading.hide();
//                UpdateUserDataEvent event = new UpdateUserDataEvent();
//                EventBus.getDefault().post(event);
//                finish();
//            }
//
//            @Override
//            public void onError(Response<logoutBean> response) {
//                super.onError(response);
//                ToastUtils.showShort("请检查网络连接");
//                loading.hide();
//            }
//        });

    }
    //密码修改
    @OnClick(R.id.change_password)
    public void changePassword(){
        if (ButtonUtils.isFastDoubleClick(R.id.change_password)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, LoginInfoActivity.class);
        intent.putExtra("into", "forget");
        startActivity(intent);
    }
    //名字修改
    @OnClick(R.id.edit_name)
    public void editName(){
        if (ButtonUtils.isFastDoubleClick(R.id.edit_name)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, EditUserNameActivity.class);
        intent.putExtra("xb", xb);
        startActivityForResult(intent, 1001);
    }

    //性别修改
    @OnClick(R.id.user_xb)
    public void editXb(){
        if (ButtonUtils.isFastDoubleClick(R.id.edit_name)){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, EditXbActivity.class);
        intent.putExtra("username", nickName);
        startActivityForResult(intent, 1002);
    }

    /*
   * 获取图片回调
   * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    final Bitmap bm = getZoomImage(bitmap, 400, 400);
                    // 系统时间
                    long dateTaken = System.currentTimeMillis();
                    // 图像名称
                    String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss",
                            dateTaken).toString() + ".jpg";
                    // 存储进本地库
                    Uri uri2 = insertImage(getContentResolver(), filename,
                            dateTaken, PATH, filename, bm, null);

                    HomeAPI.setUserAvatar(new File(PATH + filename), new JsonCallback<UploadBean>() {
                        @Override
                        public void onSuccess(Response<UploadBean> response) {
                            LogUtils.d("success");
                            //与拍照保持一致方便处理
                            profile_image.setImageBitmap(bm);
                            delFile(filename);
                        }

                        @Override
                        public void onError(Response<UploadBean> response) {
                            super.onError(response);
                            delFile(filename);
                        }
                    });

                } catch (Exception e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
        }else if(requestCode == 1001){
            if (data!=null){
                String username = data.getStringExtra("userName");

                edit_name.setText(username);
            }
            //通知个人中心刷新
            UpdateUserDataEvent event = new UpdateUserDataEvent();
            EventBus.getDefault().post(event);
        }else if(requestCode == 1002){
            if (data!=null){
               String xb = data.getStringExtra("xb");
                if (xb.equals("0")){
                    xb = "男";
                }
                if (xb.equals("1")){
                    xb = "女";
                }
                user_xb.setText(xb);
            }
            //通知个人中心刷新
            UpdateUserDataEvent event = new UpdateUserDataEvent();
            EventBus.getDefault().post(event);
        }else if(requestCode == 1003){
            //通知个人中心刷新
            UpdateUserDataEvent event = new UpdateUserDataEvent();
            EventBus.getDefault().post(event);
            finish();
        }

    }

    /**
     * 存储图像并将信息添加入媒体数据库
     */
    private Uri insertImage(ContentResolver cr, String name, long dateTaken,
                            String directory, String filename, Bitmap source, byte[] jpegData) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        return cr.insert(IMAGE_URI, values);
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

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }
        LogUtils.d("");

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }
    
}
