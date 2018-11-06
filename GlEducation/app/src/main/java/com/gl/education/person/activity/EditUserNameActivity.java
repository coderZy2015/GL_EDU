package com.gl.education.person.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.UserProfileBean;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑我的昵称页面
 */
public class EditUserNameActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.edit_name)
    EditText edit_name;

    @BindView(R.id.edit_name_finish)
    ImageView edit_name_finish;

    //    定义按钮对象
    private RadioButton mMaleRb;
    private RadioButton mFamaleRb;

    private String userName = "";   //userName
    private String xb = "";   //性别
    private Loading_view loading;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_edit_user_name;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();
        xb = intent.getStringExtra("xb");

        edit_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    userName = edit_name.getText().toString();
                    if (userName.equals("")){
                        ToastUtils.showShort("请填写昵称");
                    }else{
                        sendName();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }
    public void sendName(){
        loading.show();
        HomeAPI.setUserProfile(userName, xb, new JsonCallback<UserProfileBean>() {
            @Override
            public void onSuccess(Response<UserProfileBean> response) {
                if (response.body().getResult() == 1000){
                    loading.hide();
                    Intent intent = new Intent();
                    intent.putExtra("userName", userName);
                    setResult(1, intent);
                    finish();
                    ToastUtils.showShort("修改成功");
                }else{
                    loading.hide();
                    ToastUtils.showShort("提交失败，请重试");
                }
            }

            @Override
            public void onError(Response<UserProfileBean> response) {
                super.onError(response);
            }
        });
    }

    @OnClick(R.id.edit_name_finish)
    public void editName(){
        userName = edit_name.getText().toString();
        if (userName.equals("")){
            ToastUtils.showShort("请填写昵称");
        }else{
            sendName();
        }

    }


}
