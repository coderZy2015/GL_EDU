package com.gl.education.home.activity;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.camera.activity.CameraActivity;
import com.gl.education.helper.DialogHelper;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenCameraEvent;
import com.gl.education.home.event.RequestNetWorkAgainEvent;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.fragment.HomePageFragment;
import com.gl.education.home.fragment.MineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页面
 */
public class HomePageActivity extends BaseActivity {

    public static final int FIRST = 0;
    public static final int SECOND = 1;

    private BaseFragment[] mFragments = new BaseFragment[2];
    private long exitTime = 0;

    @BindView(R.id.camera)
    RelativeLayout photoView;

    @BindView(R.id.btn_home_page)
    LinearLayout btn_home_page;

    @BindView(R.id.btn_home_mine)
    LinearLayout btn_home_mine;
    @BindView(R.id.image_ic_page)
    ImageView image_ic_page;
    @BindView(R.id.image_ic_person)
    ImageView image_ic_person;

    @BindView(R.id.request_again)
    TextView request_again;

    @BindView(R.id.layout_network_error)
    RelativeLayout layout_network_error;

    private boolean isShowError = false;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        BaseFragment firstFragment = findFragment(HomePageFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomePageFragment.newInstance();
            mFragments[SECOND] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.data_content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(MineFragment.class);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.btn_home_page, R.id.btn_home_mine })
    public void onClick(LinearLayout btn){
        if (btn.getId() == R.id.btn_home_page){
            image_ic_page.setBackgroundResource(R.drawable.home_ic_page_s);
            image_ic_person.setBackgroundResource(R.drawable.home_ic_person);
            showHideFragment(mFragments[FIRST], mFragments[SECOND]);
            if (isShowError){
                layout_network_error.setVisibility(View.VISIBLE);
            }
        }
        else if(btn.getId() == R.id.btn_home_mine){
//            Intent intent = new Intent();
//            intent.setClass(this, MineActivity.class);
//            startActivity(intent);

            image_ic_page.setBackgroundResource(R.drawable.home_ic_page);
            image_ic_person.setBackgroundResource(R.drawable.home_ic_person_s);
            showHideFragment(mFragments[SECOND], mFragments[FIRST]);

            UpdateUserDataEvent event = new UpdateUserDataEvent();
            EventBus.getDefault().post(event);
            if (isShowError){
                layout_network_error.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.camera)
    public void onClick(){
        sendRequest();//请求权限
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
            return;
        } else {
            finish();
            System.exit(0);
        }
        super.onBackPressedSupport();
    }

    //获取拍照权限
    public void sendRequest() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {//被拒绝再次发起请求
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                        Intent intent = new Intent(HomePageActivity.this, CameraActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        ToastUtils.showShort("onDenied");
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .theme(new PermissionUtils.ThemeCallback() {
                    @Override
                    public void onActivityCreate(Activity activity) {
                        ScreenUtils.setFullScreen(activity);
                    }
                })
                .request();
    }

    //打开摄像机
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openCamera(OpenCameraEvent event) {
        Intent intent = new Intent(HomePageActivity.this, CameraActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.request_again)
    public void requestAgain(){
        RequestNetWorkAgainEvent event = new RequestNetWorkAgainEvent();
        EventBus.getDefault().post(event);
    }


    public void showNoConnect(){
        layout_network_error.setVisibility(View.VISIBLE);
        isShowError = true;
    }

    public void hideNoConnect(){
        layout_network_error.setVisibility(View.GONE);
        isShowError = false;
    }
}
