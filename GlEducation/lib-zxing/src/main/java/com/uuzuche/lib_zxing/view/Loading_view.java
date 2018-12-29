package com.uuzuche.lib_zxing.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.uuzuche.lib_zxing.R;

/**
 * Created by zy on 2018/8/24.
 */

public class Loading_view extends ProgressDialog {
    private Context mContext;
    public Loading_view(Context context) {
        super(context);
        mContext = context;
    }
    public Loading_view(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }
    private void init(Context context) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading); //loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
    @Override
    public void show() {//开启
        if (mContext != null)
        super.show();
    }
    @Override
    public void dismiss() {//关闭
        if (mContext != null)
        super.dismiss();
    }
}
