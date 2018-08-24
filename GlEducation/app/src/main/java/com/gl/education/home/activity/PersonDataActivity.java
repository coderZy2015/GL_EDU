package com.gl.education.home.activity;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.TransmitPersonDataEvent;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.model.UploadBean;
import com.gl.education.home.utlis.ImageLoader;
import com.gl.education.widget.RoundImageView;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonDataActivity extends BaseActivity {

    private final String downloadUrl = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";

    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.mine_person)
    RoundImageView mine_person;
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.edit_name)
    TextView edit_name;

    private String nickName = "";
    private String pic_url = "";

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

    @OnClick({ R.id.imageView, R.id.mine_person})
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

        mine_person.setType(RoundImageView.TYPE_CIRCLE);

        //数据
        ArrayList data_list = new ArrayList<String>();
        data_list.add("男  ");
        data_list.add("女  ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(TransmitPersonDataEvent event) {
        if (!nickName.equals(""))
            edit_name.setText("" + nickName);

        if (pic_url.equals("")){
            Glide.with(this).load(R.drawable.ic_photo).into(mine_person);
        }else{
            ImageLoader.loadImage(this, downloadUrl+pic_url, mine_person);
        }
    }

    @OnClick(R.id.edit_name)
    public void editName(){
        Intent intent = new Intent();
        intent.setClass(this, EditUserNameActivity.class);
        startActivityForResult(intent, 1001);
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
                    final Bitmap bm = getZoomImage(bitmap, 200, 200);
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
                            mine_person.setImageBitmap(bm);
                            mine_person.setType(RoundImageView.TYPE_CIRCLE);
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
            if (data != null) {
                //edit_name
            }
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
