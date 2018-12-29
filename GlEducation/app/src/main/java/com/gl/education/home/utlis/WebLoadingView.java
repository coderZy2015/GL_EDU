package com.gl.education.home.utlis;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.gl.education.R;
import com.just.agentweb.BaseIndicatorView;

/**
 * Created by zy on 2018/12/19.
 * webview的加载中动画
 */

public class WebLoadingView extends BaseIndicatorView {

    public WebLoadingView(Context context) {
        super(context);
        View rootView = View.inflate(getContext(), R.layout.dialog_one_price, null);
        addView(rootView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void setProgress(int newProgress) {
        super.setProgress(newProgress);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public LayoutParams offerLayoutParams() {
        return super.offerLayoutParams();
    }
}
