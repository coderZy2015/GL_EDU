package com.gl.education.camera.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.camera.CameraCoverInteractive;
import com.gl.education.helper.Convert;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.CameraCoverOpenBookguidEvent;
import com.gl.education.home.event.GetCoverListDataEvent;
import com.gl.education.home.event.OpenJCChannelEvent;
import com.gl.education.home.event.OpenJFChannelEvent;
import com.gl.education.home.model.CameraCoverBean;
import com.gl.education.home.model.CameraCoverOpenBookBean;
import com.gl.education.home.model.GetCoverISBNBean;
import com.gl.education.teachingassistant.activity.JFBookMenuActivity;
import com.gl.education.teachingmaterial.activity.JCBookMenuActivity;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraCoverActivity extends BaseActivity {

    String PIC_URL = "http://jiaofu-image.oss-cn-qingdao.aliyuncs.com";
    String ISBN_1 = "http://appapi.hebeijiaoyu.cn/";
    String ISBN_2 = "index.php/jiaofu/isbnToJiaofu/isbn/";


    @BindView(R.id.web_container)
    LinearLayout web_container;

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    protected AgentWeb mAgentWeb;
    public String bookTitle = "";

    public String result = "";

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_camera_cover;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();

        result = getIntent().getStringExtra("result");

        // 注册订阅者
        EventBus.getDefault().register(this);

        mAgentWeb = AgentWeb.with(this) //传入Activity
                .setAgentWebParent(web_container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator() // 使用默认进度条
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(AppConstant.YY_WEB_CAMERA_COVER);

        mAgentWeb.getWebCreator().getWebView().setHorizontalScrollBarEnabled(false); //水平不显示
        mAgentWeb.getWebCreator().getWebView().setVerticalScrollBarEnabled(false);   //垂直不显示
        //mAgentWeb.getWebCreator().getWebView().reload();    //刷新
        mAgentWeb.clearWebCache();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new CameraCoverInteractive(mAgentWeb,
                this));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册订阅者
        mAgentWeb.getWebLifeCycle().onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressed();
        finish();
    }

    //获取封面数据信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SetCoverListData(GetCoverListDataEvent event) {
        Gson gson = new Gson();
        CameraCoverBean bean = gson.fromJson(result, CameraCoverBean
                .class);

        for (int i=0; i<bean.getData().size(); i++){
            bean.getData().get(i).setPic_url(PIC_URL + bean.getData().get(i).getPicture_bigurl());
        }
        String json = Convert.toJson(bean.getData());

        mAgentWeb.getJsAccessEntrace().quickCallJs(AppConstant
                        .callJs_setCoverListData, json);
        LogUtils.d("setCoverListData json = "+json);
    }

    //打开教辅频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OpenJFChannelEvent(OpenJFChannelEvent event) {
        finish();
    }

    //打开教材频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OpenJCChannelEvent(OpenJCChannelEvent event) {
        finish();
    }

    //打开教材频道
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OpenJCChannelEvent(CameraCoverOpenBookguidEvent event) {
        CameraCoverOpenBookBean bean = event.getBean();

        HomeAPI.getCoverISBN(bean.getGuid(), new JsonCallback<GetCoverISBNBean>() {

            @Override
            public void onSuccess(Response<GetCoverISBNBean> response) {
                if (response.body().getResult() == 1000){
                    if (response.body().getData().get(0).getType().equals("jiaofu")){
                        Intent intent = new Intent();
                        intent.setClass(CameraCoverActivity.this, JFBookMenuActivity.class);
                        intent.putExtra("url", ISBN_1+ISBN_2+response.body().getData().get(0).getIsbn());
                        intent.putExtra("title", "教辅目录");
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent();
                        intent.setClass(CameraCoverActivity.this, JCBookMenuActivity.class);
                        intent.putExtra("url", ISBN_1+ISBN_2+response.body().getData().get(0).getIsbn());
                        intent.putExtra("title", "教材目录");
                        startActivity(intent);
                    }

                }else{
                    ToastUtils.showShort("数据出现问题");
                }

            }
        });
    }

}
