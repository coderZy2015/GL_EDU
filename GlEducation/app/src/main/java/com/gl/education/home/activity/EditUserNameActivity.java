package com.gl.education.home.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.login.model.LoginBean;
import com.lzy.okgo.model.Response;

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

    private String userName = "";   //userName

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

    @OnClick(R.id.btn_back)
    public void onBack(){
        onBackPressed();
        finish();
    }

    @OnClick(R.id.edit_name_finish)
    public void editName(){
        userName = edit_name.getText().toString();
        if (userName.equals("")){
            ToastUtils.showShort("请填写昵称");
        }else{
            HomeAPI.setUserProfile(userName," 0", new JsonCallback<LoginBean>() {
                @Override
                public void onSuccess(Response<LoginBean> response) {
                    if (response.body().getResult() == 1000){
                        setResult(1);
                        finish();
                        ToastUtils.showShort("修改成功");
                    }else{
                        ToastUtils.showShort("提交失败，请重试");
                    }
                }
            });
        }

    }
}
