package com.gl.education.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.utlis.TimeButton;
import com.gl.education.login.model.ForgetPasswordBean;
import com.gl.education.login.model.IdentifyBean;
import com.gl.education.login.presenter.ForgetPasswordPresenter;
import com.gl.education.login.view.ForgetPasswordView;
import com.uuzuche.lib_zxing.view.Loading_view;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/27.
 */

public class ForgetPasswordFragment extends BaseFragment<ForgetPasswordView,
        ForgetPasswordPresenter> implements ForgetPasswordView {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.edit_usename)
    EditText edit_usename;
    @BindView(R.id.edit_new_password)
    EditText edit_password;
    @BindView(R.id.edit_corm_password)
    EditText edit_corm_password;
    @BindView(R.id.edit_identify)
    EditText edit_identify;

    @BindView(R.id.btn_sure)
    ImageButton btn_sure;

    @BindView(R.id.get_code)
    TimeButton get_code;

    private Loading_view loading;

    String username = "";
    String password1 = "";
    String password2 = "";
    String identify = "";

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    public static ForgetPasswordFragment newInstance() {
        return new ForgetPasswordFragment();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_forget_password;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        get_code.setTextAfter("秒后重新获取").setTextBefore("点击发送验证码").setLenght(60 * 1000);
        loading = new Loading_view(getActivity(), com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @OnClick(R.id.btn_sure)
    public void onClickBtn(){
        username = edit_usename.getText().toString();
        identify = edit_identify.getText().toString();
        password1 = edit_password.getText().toString();
        password2 = edit_corm_password.getText().toString();

        if(TextUtils.isEmpty(username)){// 判断密码
            // 以字母开头，长度在6~18之间，只能包含字母、数字和下划线。
            ToastUtils.showShort("账号不能为空");
        }else if(TextUtils.isEmpty(identify)){
            ToastUtils.showShort("验证码不能为空");
        } else if(TextUtils.isEmpty(password1)){
            ToastUtils.showShort("密码不能为空");
        }else if(!password1.equals(password2)){
            ToastUtils.showShort("两次输入的密码不一样");
        }else {
            loading.show();
            mPresenter.toSend(username, identify, password1, password2);
        }

    }

    @OnClick(R.id.get_code)
    public void getCode(){
        username = edit_usename.getText().toString();

        if (username.length() == 11) {
            get_code.startTime();
            mPresenter.getIdentifyCode(username, "resetpassword");
        } else if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入手机号码！");
        } else {
            ToastUtils.showShort("长度不够11位数字");
        }

    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        clickBackBtn();
    }

    @Override
    public void reviseSuccess(ForgetPasswordBean bean) {
        loading.dismiss();
        ToastUtils.showShort("修改成功");
        _mActivity.onBackPressed();
    }

    @Override
    public void reviseFail(String msg) {
        ToastUtils.showShort(msg);
        loading.dismiss();
    }

    @Override
    public void sendCode(IdentifyBean bean) {
        ToastUtils.showShort("发送成功");
    }

    @Override
    public void sendCodeError(String msg) {
        ToastUtils.showShort(msg);
    }
}
