package com.gl.education.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.HomeThirdLogin;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.event.UpdateChannelDataEvent;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.login.model.LoginBean;
import com.gl.education.login.presenter.LoginPresenter;
import com.gl.education.login.view.LoginView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/27.
 */

public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView{

    @BindView(R.id.btn_login)
    ImageButton btn_login;

    @BindView(R.id.btn_password)
    TextView btn_repeatPassword;

    @BindView(R.id.btn_register)
    TextView btn_register;

    @BindView(R.id.edit_usename)
    EditText edit_usename;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.btn_login_wx)
    RelativeLayout btn_login_wx;

    @BindView(R.id.btn_login_qq)
    RelativeLayout btn_login_qq;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    String username = "";
    String password = "";

    private Loading_view loading;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_login;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @OnClick(R.id.btn_login)
    public void clickLogin(){

        username = edit_usename.getText().toString();
        password = edit_password.getText().toString();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {   // 判断密码
            //以字母开头，长度在6~18之间，只能包含字母、数字和下划线。
            ToastUtils.showShort("账号密码不能为空");
        }else{
            loading.show();
            mPresenter.toLogin(username, password);
        }
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        loading = new Loading_view(getActivity(), com.uuzuche.lib_zxing.R.style.CustomDialog);
    }

    @OnClick(R.id.btn_register)
    public void clickRegister(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_register)){
            return;
        }
        start(RegisterFragment.newInstance());
    }

    @OnClick(R.id.btn_password)
    public void clickReatPassword(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_password)){
            return;
        }
        start(ForgetPasswordFragment.newInstance());
    }

    @OnClick(R.id.btn_back)
    public void onBack(){
        _mActivity.onBackPressed();
        _mActivity.finish();
    }

    @OnClick(R.id.btn_login_qq)
    public void qqLogin(){
    }

    @OnClick(R.id.btn_login_wx)
    public void wxLogin(){
        loading.show();

        HomeThirdLogin.loginWX(getActivity(), getContext(), new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d("onStart   "+share_media);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                LogUtils.d("i == "+i);
//                LogUtils.d("unionid = "+map.get("unionid"));
//                LogUtils.d("unionid = "+map.get("screen_name"));
//                LogUtils.d("unionid = "+map.get("gender"));
//                LogUtils.d("unionid = "+map.get("profile_image_url"));

                HomeAPI.checkWechat(map.get("unionid"), new JsonCallback<LoginBean>() {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        loading.dismiss();
                        if (response.body().getResult() == 1000){
                            loginSuccess(response.body());
                            LogUtils.d("loginSuccess");
                        }else{
                            LogUtils.d("loginFail");
                            start(RegisterWxFragment.newInstance(map.get("unionid")));
                        }
                    }

                    @Override
                    public void onError(Response<LoginBean> response) {
                        super.onError(response);
                    }
                });


            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                loading.dismiss();
                LogUtils.d("onError   "+share_media);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                loading.dismiss();
                LogUtils.d("onCancel   "+share_media);
            }
        });
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        loading.dismiss();
        AppCommonData.isLogin = true;

        String token = bean.getData().getToken();
        SPUtils.getInstance().put(AppConstant.SP_TOKEN, token);
        OkGo.getInstance().getCommonHeaders().put("GL-Token", token);

        //通知三频道更换token
        UpdateChannelDataEvent event1 = new UpdateChannelDataEvent();
        EventBus.getDefault().post(event1);

        //通知个人中心刷新
        UpdateUserDataEvent event = new UpdateUserDataEvent();
        EventBus.getDefault().post(event);

        //通知web
        ToastUtils.showShort("登录成功！");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("token", bean.getData().getToken());
        _mActivity.setResult(1000, resultIntent);
        _mActivity.finish();
    }

    @Override
    public void loginFail(LoginBean bean) {
        loading.dismiss();
        ToastUtils.showShort(bean.getMessage());
        if (bean.getResult() == 2101){

        }

    }
}
