package com.gl.education.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.TransmitPersonDataEvent;
import com.gl.education.home.event.UpdateUserDataEvent;
import com.gl.education.home.model.GetUserCenterDataBean;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.home.utlis.ImageLoader;
import com.gl.education.login.LoginInfoActivity;
import com.gl.education.person.activity.InstallActivity;
import com.gl.education.person.activity.MyCompositionActivity;
import com.gl.education.person.activity.MyJCCollectionActivity;
import com.gl.education.person.activity.MyJFCollectionActivity;
import com.gl.education.person.activity.MyMessageActivity;
import com.gl.education.person.activity.MyWKCollectionActivity;
import com.gl.education.person.activity.MyWalletActivity;
import com.gl.education.person.activity.MyZXCollectionActivity;
import com.gl.education.person.activity.PersonDataActivity;
import com.gl.education.person.activity.WrongTopicBookActivity;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zy on 2018/6/7.
 * 个人中心
 */

public class MineFragment extends BaseFragment {

    private final String downloadUrl = "http://gl-appres.oss-cn-qingdao.aliyuncs.com/";

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

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
    @BindView(R.id.zuowen_num)
    TextView zuowen_num;

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

    @BindView(R.id.btn_mine_collection)
    LinearLayout btn_mine_collection;

    @BindView(R.id.btn_mine_message)
    LinearLayout btn_message;

    private boolean isClick = false;

    private String pic_url = "";
    private String phone = "";
    private String xb = "";

    private Loading_view loading;


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
        //mine_person.setType(RoundImageView.TYPE_CIRCLE);
        loading = new Loading_view(getActivity(), com.uuzuche.lib_zxing.R.style.CustomDialog);
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
    @OnClick({R.id.profile_image})
    public void intoPersonDataActivity(){

        if (ButtonUtils.isFastDoubleClick(R.id.profile_image)){
            return;
        }
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }

        TransmitPersonDataEvent event = new TransmitPersonDataEvent();
        event.setNickName( mine_user_name.getText().toString());
        event.setUrl( pic_url);
        event.setPhone(phone);
        event.setXb(xb);
        EventBus.getDefault().postSticky(event);

        Intent intent = new Intent();
        intent.setClass(getActivity(), PersonDataActivity.class);
        startActivity(intent);
    }
    //跳转到我的钱包页面
    @OnClick(R.id.btn_mine_wallet)
    public void intoWallet(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_mine_wallet)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_mine_message)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.mine_user_name)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_relax_text)){
            return;
        }
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
    //我的收藏
    @OnClick(R.id.btn_mine_collection)
    public void intoCollection(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_mine_collection)){
            return;
        }
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyZXCollectionActivity.class);
        startActivity(intent);
    }

    //跳转到设置界面
    @OnClick(R.id.btn_mine_install)
    public void intoInstall(){
        if (ButtonUtils.isFastDoubleClick(R.id.btn_mine_install)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_teaching_material)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_teaching_assistant)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_small_class)){
            return;
        }
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
        if (ButtonUtils.isFastDoubleClick(R.id.btn_composition)){
            return;
        }
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginInfoActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent();
        intent.setClass(getActivity(), MyCompositionActivity.class);
        startActivity(intent);
    }

    //更新我的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(UpdateUserDataEvent event) {
        loading.show();
        HomeAPI.getUserCenterHome(new JsonCallback<GetUserCenterDataBean>() {
            @Override
            public void onSuccess(Response<GetUserCenterDataBean> response) {
                loading.dismiss();
                if (response.body().getResult() == 1000){//成功
                    AppCommonData.isLogin = true;
                    if (response.body().getData() != null){

                        GetUserCenterDataBean.DataBean bean = response.body().getData();
                        if (bean.getNick_name() == null){
                            mine_user_name.setText(""+bean.getUsername());
                        }else{
                            mine_user_name.setText(""+bean.getNick_name());
                        }
                        if (bean.getAvatar() == null){
                            Glide.with(getContext()).load(R.drawable.ic_photo).into(profile_image);
                            pic_url = "";
                        }else{
                            ImageLoader.loadImage(getContext(), downloadUrl+bean.getAvatar(), profile_image);
                            pic_url = bean.getAvatar();
                        }
                        if (bean.getXb() == null){
                            xb = "男";
                        }else{
                            xb = bean.getXb();
                        }

                        phone = bean.getPhone();
                        jiaocai_num.setText(""+bean.getJiaocai());
                        jiaofu_num.setText(""+bean.getJiaofu());
                        weike_num.setText(""+bean.getWeike());
                        zuowen_num.setText(""+bean.getZuowen());
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
                loading.dismiss();
            }
        });
    }
}
