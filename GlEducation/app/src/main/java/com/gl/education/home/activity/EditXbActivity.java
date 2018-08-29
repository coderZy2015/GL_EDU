package com.gl.education.home.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RadioButton;

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

public class EditXbActivity extends BaseActivity {

    @BindView(R.id.male_rb)
    RadioButton male_rb;

    @BindView(R.id.famale_rb)
    RadioButton famale_rb;

    @BindView(R.id.edit_name_finish)
    ImageView edit_name_finish;

    private Loading_view loading;
    private String xb;
    private String username;

    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_edit_xb;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        username = getIntent().getStringExtra("username");
    }

    @OnClick(R.id.edit_name_finish)
    public void editFinish(){
        loading.show();

        if (male_rb.isChecked()){
            xb = "0";
        }
        if (famale_rb.isChecked()){
            xb = "1";
        }
        HomeAPI.setUserProfile(username, xb, new JsonCallback<UserProfileBean>() {
            @Override
            public void onSuccess(Response<UserProfileBean> response) {
                if (response.body().getResult() == 1000){
                    loading.hide();
                    Intent intent = new Intent();
                    intent.putExtra("xb", xb);
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
}
