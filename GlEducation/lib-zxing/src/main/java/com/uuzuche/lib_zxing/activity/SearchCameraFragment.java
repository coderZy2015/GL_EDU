package com.uuzuche.lib_zxing.activity;

import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.camear.CameraPreview;
import com.uuzuche.lib_zxing.camear.FocusView;
import com.uuzuche.lib_zxing.camear.Utils;
import com.uuzuche.lib_zxing.view.Loading_view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zy on 2018/6/15.
 */

public class SearchCameraFragment extends Fragment implements CameraPreview
        .OnCameraStatusListener {

    //调试网址
    //public final String loadPicUrl = "http://appserbeta.hebeijiaoyu.cn/iclient/cliuser/uploadFile";
    //正常网址
    public final String loadPicUrl = "http://appser.hebeijiaoyu.cn/iclient/cliuser/uploadFile";

    private Loading_view loading;

    private CodeUtils.PhotographCallback photographCallback;

    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    CameraPreview mCameraPreview;
    CropImageView mCropImageView;
    RelativeLayout mTakePhotoLayout;
    LinearLayout mCropperLayout;

    private ImageView btnClose;
    private RelativeLayout btnShutter;  //拍照
    private ImageButton btnAlbum;  //图库

    private ImageView btnStartCropper;  //对
    private ImageView btnCloseCropper;  //错

    private TextView tvHint;
    private TextView crop_hint;

    public Context mContext;
    private int rotateNum = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private boolean mInitialized = false;
    private SensorManager mSensorManager;

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

        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        tvHint = view.findViewById(R.id.hint);
        crop_hint = view.findViewById(R.id.crop_hint);

    }

    @Override
    public void onResume() {
        super.onResume();

        Sensor mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速度传感器
        mSensorManager.registerListener(listener, mAccel, SensorManager.SENSOR_DELAY_UI);

        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(glistener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(listener);
        mSensorManager.unregisterListener(glistener);
    }

    /**
     * 位移 自动对焦
     */
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //当传感器的数值发生变化时调用
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

            if (deltaX > 0.5 || deltaY > 0.5 || deltaZ > 0.5) {
                mCameraPreview.setFocus();
            }
            mLastX = x;
            mLastY = y;
            mLastZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    /**
     * 自动旋转文字
     */
    private SensorEventListener glistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = -1;
            float X = -values[0];
            float Y = -values[1];
            float Z = -values[2];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }

            if (orientation > 45 && orientation < 135) {
                //Log.i("zy_code", "反向横向");
                if (rotateNum != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                    rotateDegree(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
            } else if (orientation > 135 && orientation < 225) {
                //Log.i("zy_code", "反向竖向");
                if (rotateNum != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    rotateDegree(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                }
            } else if (orientation > 225 && orientation < 315) {
                //Log.i("zy_code", "横向");
                if (rotateNum != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    rotateDegree(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 &&
                    orientation < 45)) {
                if (rotateNum != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    rotateDegree(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                //Log.i("zy_code", "竖向");
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void rotateDegree(int rotate) {
        float degree_start = 0;
        float degree_end = 90f;

        switch (rotateNum) {
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                degree_start = 0;
                break;
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                degree_start = 90;
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                degree_start = 180;
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                degree_start = 270;
                break;
            default:
                break;
        }

        switch (rotate) {
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                if (degree_start == 270) {
                    degree_end = 360;
                } else {
                    degree_end = 0;
                }

                break;
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                degree_end = 90f;
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                degree_end = 180;
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                if (degree_start == 0) {
                    degree_end = -90;
                } else {
                    degree_end = 270;
                }
                break;
            default:
                break;
        }
        rotateNum = rotate;

        ObjectAnimator animator = ObjectAnimator.ofFloat(tvHint, "rotation", degree_start,
                degree_end);
        animator.setStartDelay(1);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
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
     */
    private View.OnClickListener cropcper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_closecropper) {
                showTakePhotoLayout();
            } else if (view.getId() == R.id.btn_startcropper) {
                try {
                    //获取截图并旋转90度
                    Bitmap cropperBitmap = mCropImageView.getCroppedImage();
                    Bitmap bitmap;
                    bitmap = Utils.rotate(cropperBitmap, 0);

                    // 系统时间
                    long dateTaken = System.currentTimeMillis();
                    // 图像名称
                    String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss",
                            dateTaken).toString() + ".jpg";
                    // 存储进本地库
                    String path = mContext.getExternalCacheDir().getPath()+"/";
                    Log.i("zy_code", "path == "+path);
                    Uri uri = insertImage(mContext.getContentResolver(), filename,
                            dateTaken, path, filename, bitmap, null);

                    crop_hint.setText("查找中，请稍后...");
                    loading = new Loading_view(getActivity(), R.style.CustomDialog);
                    loading.show();

                    OkGo.<String>post(loadPicUrl)
                            .params("a1_upload", new File(path + filename))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    if (photographCallback != null) {
                                        photographCallback.onPotographSuccess(path+filename, response
                                                .body());
                                    }
                                    loading.dismiss();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    crop_hint.setText("查找超时，请重试");
                                    loading.dismiss();
                                    if (response.body() != null) {
                                        if (photographCallback != null) {
                                            photographCallback.onPhotographFailed(path+filename);
                                        }
                                    }

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "图片处理出现问题", Toast.LENGTH_SHORT).show();
                    showTakePhotoLayout();
                }
            }
        }
    };

    /**
     * 拍照成功后回调
     * 存储图片并显示截图界面
     *
     * @param data
     */
    @Override
    public void onCameraStopped(byte[] data) {

        // 创建图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        Log.i("zy_code", "bitmap.getWidth() = "+bitmap.getWidth());
        Log.i("zy_code", "bitmap.getHeight() = "+bitmap.getHeight());

        //有些手机拍照后图片过于小，读取了裁剪控件的宽高来放大图片。
        mCropImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                mCropImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.i("zy_code", "mCropImageView.getWidth() = "+mCropImageView.getWidth());
                Log.i("zy_code", "mCropImageView.getHeight() = "+mCropImageView.getHeight());
                Bitmap bm = setImgSize(bitmap, mCropImageView.getHeight(), mCropImageView.getWidth());
                mCropImageView.setImageBitmap(bm);
                mCropImageView.rotateImage(90);
            }
        });

//        if (mCameraPreview.getCameraParamsInit()){
//            mCropImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                @Override
//                public void onGlobalLayout() {
//                    // TODO Auto-generated method stub
//                    mCropImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    Log.i("zy_code", "mCropImageView.getWidth() = "+mCropImageView.getWidth());
//                    Log.i("zy_code", "mCropImageView.getHeight() = "+mCropImageView.getHeight());
//                    Bitmap bm = setImgSize(bitmap, mCropImageView.getHeight(), mCropImageView.getWidth());
//                    mCropImageView.setImageBitmap(bm);
//                    mCropImageView.rotateImage(90);
//                }
//            });
//        }else{
//            mCropImageView.setImageBitmap(bitmap);
//            mCropImageView.rotateImage(90);
//        }

        showCropperLayout();

    }

    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth)*0.9f / width;
        float scaleHeight = ((float) newHeight)*0.9f / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /*
    * 获取图片回调
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
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

    private void showTakePhotoLayout() {
        mTakePhotoLayout.setVisibility(View.VISIBLE);
        mCropperLayout.setVisibility(View.GONE);
        setCameraBottomShow();
    }

    private void showCropperLayout() {
        mTakePhotoLayout.setVisibility(View.GONE);
        mCropperLayout.setVisibility(View.VISIBLE);
        mCameraPreview.start();   //继续启动摄像头
        setCameraBottomHide();
    }


    public void setAnalyzeCallback(CodeUtils.PhotographCallback analyzeCallback) {
        this.photographCallback = analyzeCallback;
    }

    public void setCameraBottomHide() {
        ((CaptureActivity)getActivity()).cameraHide();
    }
    public void setCameraBottomShow() {
        ((CaptureActivity)getActivity()).cameraShow();
    }
}
