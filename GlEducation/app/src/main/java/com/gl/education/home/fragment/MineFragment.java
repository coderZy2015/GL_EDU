package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.InstallActivity;
import com.gl.education.home.activity.MyJCCollectionActivity;
import com.gl.education.home.activity.MyJFCollectionActivity;
import com.gl.education.home.activity.MyMessageActivity;
import com.gl.education.home.activity.MyWKCollectionActivity;
import com.gl.education.home.activity.MyWalletActivity;
import com.gl.education.home.activity.PersonDataActivity;
import com.gl.education.home.activity.WrongTopicBookActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.event.TransmitPersonDataEvent;
import com.gl.education.home.model.GetUserCenterDataBean;
import com.gl.education.home.utlis.ImageLoader;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.widget.RoundImageView;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/7.
 * 个人中心
 */

public class MineFragment extends BaseFragment {

    private final String downloadUrl = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";

    @BindView(R.id.mine_person)
    RoundImageView mine_person;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.btn_mine_wallet)
    LinearLayout btn_mine_wallet;

    @BindView(R.id.btn_mine_install)
    LinearLayout btn_mine_install;

    @BindView(R.id.mine_user_name)
    TextView mine_user_name;

    @BindView(R.id.jiaocai_num)
    TextView jiaocai_num;
    @BindView(R.id.jiaofu_num)
    TextView jiaofu_num;
    @BindView(R.id.weike_num)
    TextView weike_num;

    @BindView(R.id.btn_teaching_material)
    LinearLayout btn_teaching_material;
    @BindView(R.id.btn_teaching_assistant)
    LinearLayout btn_teaching_assistant;
    @BindView(R.id.btn_small_class)
    LinearLayout btn_small_class;
    @BindView(R.id.btn_composition)
    LinearLayout btn_composition;

    @BindView(R.id.btn_relax_text)
    LinearLayout btn_relax_text;

    @BindView(R.id.btn_mine_message)
    LinearLayout btn_message;

    private String pic_url = "";
    private boolean isGetUserData = false;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_mine;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        mine_person.setType(RoundImageView.TYPE_CIRCLE);

        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
    //跳转到我的个人中心页面
    @OnClick({ R.id.imageView, R.id.mine_person})
    public void intoPersonDataActivity(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        if (!isGetUserData){
            return;
        }
        TransmitPersonDataEvent event = new TransmitPersonDataEvent();
        event.setNickName( mine_user_name.getText().toString());
        event.setUrl( pic_url);
        EventBus.getDefault().postSticky(event);

        Intent intent = new Intent();
        intent.setClass(getActivity(), PersonDataActivity.class);
        startActivity(intent);
    }
    //跳转到我的钱包页面
    @OnClick(R.id.btn_mine_wallet)
    public void intoWallet(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyWalletActivity.class);
        startActivity(intent);
    }
    //跳转到我的消息页面
    @OnClick(R.id.btn_mine_message)
    public void myMessage(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyMessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mine_user_name)
    public void clickName(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
    }

    //跳转到错题本
    @OnClick(  R.id.btn_relax_text)
    public void intoWrongBook(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), WrongTopicBookActivity.class);
        startActivity(intent);
    }

    //跳转到设置界面
    @OnClick(R.id.btn_mine_install)
    public void intoInstall(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        TransmitPersonDataEvent event = new TransmitPersonDataEvent();
        event.setNickName( mine_user_name.getText().toString());
        event.setUrl( pic_url);
        EventBus.getDefault().postSticky(event);

        Intent intent = new Intent();
        intent.setClass(getActivity(), InstallActivity.class);
        startActivity(intent);
    }

    //跳转到我的教材页面
    @OnClick(R.id.btn_teaching_material)
    public void toMyJcChannel(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyJCCollectionActivity.class);
        startActivity(intent);
    }
    //跳转到我的教辅页面
    @OnClick(R.id.btn_teaching_assistant)
    public void toMyJfChannel(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyJFCollectionActivity.class);
        startActivity(intent);
    }
    //跳转到我的微课页面
    @OnClick(R.id.btn_small_class)
    public void toMyWkChannel(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyWKCollectionActivity.class);
        startActivity(intent);
    }
    //跳转到我的作文页面
    @OnClick(R.id.btn_composition)
    public void toMyZwChannel(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), MyCompositionActivity.class);
//        startActivity(intent);
    }

    //跳转到详情页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toBookMenuEvent(UpdateUserDataEvent event) {

        HomeAPI.getUserCenterHome(new JsonCallback<GetUserCenterDataBean>() {
            @Override
            public void onSuccess(Response<GetUserCenterDataBean> response) {
                if (response.body().getResult() == 1000){//成功
                    AppCommonData.isLogin = true;
                    if (response.body().getData() != null){
                        isGetUserData = true;
                        GetUserCenterDataBean.DataBean bean = response.body().getData();
                        if (bean.getNick_name() == null){
                            mine_user_name.setText(""+bean.getUsername());
                        }else{
                            mine_user_name.setText(""+bean.getNick_name());
                        }
                        if (bean.getAvatar() == null){
                            Glide.with(getContext()).load(R.drawable.ic_photo).into(mine_person);
                            pic_url = "";
                        }else{
                            ImageLoader.loadImage(getContext(), downloadUrl+bean.getAvatar(), mine_person);
                            pic_url = bean.getAvatar();
                        }

                        jiaocai_num.setText(""+bean.getJiaocai());
                        jiaofu_num.setText(""+bean.getJiaofu());
                        weike_num.setText(""+bean.getWeike());
                    }

                }else if(response.body().getResult() == 2102){//游客
                    AppCommonData.isLogin = false;
                    mine_user_name.setText("点击请登录");
                }
            }

            @Override
            public void onError(Response<GetUserCenterDataBean> response) {
                super.onError(response);
                AppCommonData.isLogin = false;
                mine_user_name.setText("点击请登录");
            }
        });
    }
}
