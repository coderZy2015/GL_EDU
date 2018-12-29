package com.gl.education.home.utlis;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.gl.education.R;

/**
 * Created by zy on 2018/12/19.
 */

public class OnePriceDialog extends AlertDialog implements View.OnClickListener {

    public OnePriceDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle();
        initView();
    }

    /**
     * 加载视图
     */
    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.dialog_one_price, null);
        rootView.findViewById(R.id.buy1).setOnClickListener(this);
        rootView.findViewById(R.id.buy2).setOnClickListener(this);
        rootView.findViewById(R.id.one_price_cancel).setOnClickListener(this);
        this.setContentView(rootView);
    }

    /**
     * 设置特征
     */
    private void setStyle() {
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.width = getPhoneSize().widthPixels * 4 / 5;
        layoutParams.y = 40;
        this.setCanceledOnTouchOutside(false);
    }
    private DisplayMetrics getPhoneSize() {
        return getContext().getResources().getDisplayMetrics();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_price_cancel:
                this.dismiss();
                break;
            case R.id.buy1:
                this.dismiss();
                if (getSelectListener() != null) {
                    getSelectListener().onePrice();
                }
                break;
            case R.id.buy2:
                this.dismiss();
                if (getSelectListener() != null) {
                    getSelectListener().intoJF();
                }
                break;
            default:

                break;
        }
    }

    private SelectListener selectListener;

    public SelectListener getSelectListener() {
        return selectListener;
    }

    public void setSelectListener(SelectListener resultListener) {
        this.selectListener = resultListener;
    }

    public interface SelectListener {
        /**
         * 支付一块
         */
        void onePrice();

        /**
         * 进入教辅目录
         */
        void intoJF();
    }
}
