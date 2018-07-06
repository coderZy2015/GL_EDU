package com.uuzuche.lib_zxing.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.camear.CameraPreview;
import com.uuzuche.lib_zxing.camear.FocusView;
import com.uuzuche.lib_zxing.camear.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zy on 2018/6/15.
 */

public class SearchCameraFragment extends Fragment implements CameraPreview
        .OnCameraStatusListener, SensorEventListener {

    public final String loadPicUrl = "http://a1guanlin.eugames.cn/iclient/cliuser/uploadFile";

    //true:横屏   false:竖屏
    public static final boolean isTransverse = false;
    private CodeUtils.PhotographCallback photographCallback;

    private static final String TAG = "SearchCameraFragment";
    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PATH = Environment.getExternalStorageDirectory().toString() +
            "/AndroidMedia/";
    CameraPreview mCameraPreview;
    CropImageView mCropImageView;
    RelativeLayout mTakePhotoLayout;
    LinearLayout mCropperLayout;

    private ImageView btnClose;
    private ImageView btnShutter;
    private ImageButton btnAlbum;  //图库

    private ImageView btnStartCropper;  //对
    private ImageView btnCloseCropper;  //错

    TextView tvHint;
    TextView crop_hint;
    /**
     * 旋转文字
     */
    private boolean isRotated = true;
    public Context mContext;
    private SurfaceHolder holder;
    public boolean firstEnter = true;

    public static SearchCameraFragment newInstance() {
        Bundle args = new Bundle();

        SearchCameraFragment fragment = new SearchCameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_take_phote, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(onClickListener);
        btnShutter = view.findViewById(R.id.btn_shutter);
        btnShutter.setOnClickListener(onClickListener);
        btnAlbum = view.findViewById(R.id.btn_album);
        btnAlbum.setOnClickListener(onClickListener);

        btnStartCropper = view.findViewById(R.id.btn_startcropper);
        btnStartCropper.setOnClickListener(cropcper);
        btnCloseCropper = view.findViewById(R.id.btn_closecropper);
        btnCloseCropper.setOnClickListener(cropcper);

        mTakePhotoLayout = view.findViewById(R.id.take_photo_layout);
        mCameraPreview = view.findViewById(R.id.cameraPreview);
        FocusView focusView = view.findViewById(R.id.view_focus);

        mCropperLayout = view.findViewById(R.id.cropper_layout);
        mCropImageView = view.findViewById(R.id.CropImageView);
        mCropImageView.setGuidelines(2);

        mCameraPreview.setFocusView(focusView);
        mCameraPreview.setOnCameraStatusListener(this);
        holder = mCameraPreview.getHolder();

        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tvHint = view.findViewById(R.id.hint);
        crop_hint = view.findViewById(R.id.crop_hint);
        btnShutter = view.findViewById(R.id.btn_shutter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isTransverse) {
            if (!isRotated) {

                ObjectAnimator animator = ObjectAnimator.ofFloat(tvHint, "rotation", 0f, 90f);
                animator.setStartDelay(800);
                animator.setDuration(500);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnShutter, "rotation", 0f, 90f);
                animator1.setStartDelay(800);
                animator1.setDuration(500);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.start();

                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(crop_hint, "rotation", 0f, 90f);
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(crop_hint, "translationX", 0f, -50f);
                animSet.play(animator2).before(moveIn);
                animSet.setDuration(10);
                animSet.start();
                isRotated = true;
            }
        } else {
            if (!isRotated) {

                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(crop_hint, "rotation", 0f, 90f);
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(crop_hint, "translationX", 0f, -50f);
                animSet.play(animator2).before(moveIn);
                animSet.setDuration(10);
                animSet.start();
                isRotated = true;
            }
        }
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    /**
     * 拍照界面
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_close) {
                getActivity().onBackPressed();

            } else if (view.getId() == R.id.btn_shutter) {
                if (mCameraPreview != null) {
                    mCameraPreview.takePicture();
                }
            } else if (view.getId() == R.id.btn_album) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1001);
            }

        }
    };

    /**
     * 截图界面
     *
     * @param view
     */
    private View.OnClickListener cropcper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_closecropper) {
                showTakePhotoLayout();
            } else if (view.getId() == R.id.btn_startcropper) {
                //获取截图并旋转90度
                Bitmap cropperBitmap = mCropImageView.getCroppedImage();

                Bitmap bitmap;
                bitmap = Utils.rotate(cropperBitmap, 0);

                // 系统时间
                long dateTaken = System.currentTimeMillis();
                // 图像名称
                String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss",
                        dateTaken).toString() + ".jpg";
                //存储
                Uri uri = insertImage(mContext.getContentResolver(), filename,
                        dateTaken, PATH, filename, bitmap, null);


                if (fileIsExists(PATH + filename)) {
                    Log.i("zy_code", "you--------------  " + PATH + filename);
                } else {
                    Log.i("zy_code", "no---------------   " + PATH + filename);
                }

                crop_hint.setText("查找中，请稍后...");

//                OkHttpClient client = OkGo.getInstance().getOkHttpClient();
//                Log.i("zy_code", "write超时时间 == " +  client.writeTimeoutMillis());
//                Log.i("zy_code", "read超时时间 == " +  client.readTimeoutMillis());
//                Log.i("zy_code", "connect超时时间 == " +  client.connectTimeoutMillis());


                OkGo.<String>post(loadPicUrl)
                        .params("a1_upload", new File(PATH + filename))

                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                delFile(filename);
                                if (photographCallback != null) {
                                    photographCallback.onPotographSuccess(bitmap, response.body().toString());
                                }
                                Log.i("zy_code", "" + response.body().toString());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                delFile(filename);
                                crop_hint.setText("查找超时，请重试");
                                if (response != null){
                                    if (response.body() != null){
                                        if (photographCallback != null) {
                                            photographCallback.onPhotographFailed(bitmap);
                                        }
                                        Log.i("zy_code", "" + response.body().toString());
                                    }
                                }

                            }
                        });

                //                Intent intent = new Intent(getActivity(),
                //                        ShowCropperedActivity.class);
                //                intent.setData(uri);
                //                intent.putExtra("path", PATH + filename);
                //                intent.putExtra("width", bitmap.getWidth());
                //                intent.putExtra("height", bitmap.getHeight());
                //                //                  intent.putExtra("cropperImage", bitmap);
                //                startActivity(intent);
//                bitmap.recycle();
//                getActivity().finish();

            }
        }
    };

    //删除文件
    public static void delFile(String fileName) {
        File file = new File(PATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
    }

    /**
     * 拍照成功后回调
     * 存储图片并显示截图界面
     *
     * @param data
     */
    @Override
    public void onCameraStopped(byte[] data) {
        Log.i("TAG", "==onCameraStopped==");
        // 创建图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        if (!isTransverse) {
            bitmap = Utils.rotate(bitmap, 90);
        }
        // 系统时间
        long dateTaken = System.currentTimeMillis();
        // 图像名称
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken).toString() + ".jpg";
        // 存储图像（PATH目录）
        //        Uri source = insertImage(mContext.getContentResolver(), filename, dateTaken,
        // PATH, filename,
        //                bitmap, data);

        //准备截图
        bitmap = Utils.rotate(bitmap, 0);
        mCropImageView.setImageBitmap(bitmap);
        showCropperLayout();
    }

    /*
    * 获取图片回调
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("zy_code", "requestCode = " + requestCode);
        Log.i("zy_code", "resultCode = " + resultCode);

        //            if (resultCode == RESULT_OK) {
        //
        //            }
        Uri uri = data.getData();
        Log.e("uri", uri.toString());
        ContentResolver cr = getActivity().getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //与拍照保持一致方便处理
            bitmap = Utils.rotate(bitmap, 0);
            mCropImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage(), e);
        }
        super.onActivityResult(requestCode, resultCode, data);
        showCropperLayout();
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
            Log.e(TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
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

    private void showTakePhotoLayout() {
        mTakePhotoLayout.setVisibility(View.VISIBLE);
        mCropperLayout.setVisibility(View.GONE);
    }

    private void showCropperLayout() {
        mTakePhotoLayout.setVisibility(View.GONE);
        mCropperLayout.setVisibility(View.VISIBLE);
        mCameraPreview.start();   //继续启动摄像头
    }


    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private boolean mInitialized = false;
    private SensorManager mSensorManager;
    private Sensor mAccel;


    /**
     * 位移 自动对焦
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (deltaX > 0.8 || deltaY > 0.8 || deltaZ > 0.8) {
            mCameraPreview.setFocus();
        }
        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setAnalyzeCallback(CodeUtils.PhotographCallback analyzeCallback) {
        this.photographCallback = analyzeCallback;
    }
}
