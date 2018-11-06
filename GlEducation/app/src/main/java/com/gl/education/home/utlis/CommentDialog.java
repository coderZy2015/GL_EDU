package com.gl.education.home.utlis;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.gl.education.R;

/**
 * Created by zy on 2018/10/30.
 */

public class CommentDialog extends AlertDialog implements View.OnClickListener {

    public String sendStr;
    public EditText editText;
    Handler handler = new Handler();
    private CommentInterface anInterface;
    public Button comment_btn;

    public CommentDialog(Context context) {
        super(context);
    }

    public CommentDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setStyle();

    }

    /**
     * 加载视图
     */
    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.dialog_comment, null);
        rootView.findViewById(R.id.comment_btn).setOnClickListener(this);
        editText = rootView.findViewById(R.id.comment_edit);
        comment_btn = rootView.findViewById(R.id.comment_btn);
        this.setContentView(rootView);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0){
                    comment_btn.setBackgroundResource(R.drawable.dialog_btn_s);
                }else{
                    comment_btn.setBackgroundResource(R.drawable.dialog_btn);
                }
            }
        });
        //解决dilaog中EditText无法弹出输入的问题
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

    }

    /**
     * 设置特征
     */
    private void setStyle() {
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.width = getPhoneSize().widthPixels ;
        layoutParams.y = 40;
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setAttributes(layoutParams);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    @Override
    public void show() {
        super.show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =(InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 100);
    }

    private DisplayMetrics getPhoneSize() {
        return getContext().getResources().getDisplayMetrics();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_btn:
                if (anInterface != null){
                    String content = editText.getText().toString();
                    if (content.equals("")) {
                        //ToastUtils.showShort("评论不能为空");
                        return;
                    }
                    anInterface.send(content);
                }
                break;
            default:
                break;
        }

    }

    public void setAnInterface(CommentInterface _interface){
        anInterface = _interface;
    }

    public interface CommentInterface {
        /**
         * 发送
         */
        void send(String str);
    }
}
