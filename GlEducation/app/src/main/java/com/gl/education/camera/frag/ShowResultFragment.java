package com.gl.education.camera.frag;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.camera.PhotographResultBean;
import com.gl.education.camera.activity.CameraFinishExplainActivity;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.OrderPayActivity;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.event.OpenCameraEvent;
import com.gl.education.home.event.OpenJFChannelEvent;
import com.gl.education.home.model.CameraIntoContentBean;
import com.gl.education.home.model.GetDbCountBean;
import com.gl.education.home.utlis.ButtonUtils;
import com.gl.education.home.utlis.ImageLoader;
import com.gl.education.home.utlis.OnePriceDialog;
import com.gl.education.person.activity.RechargeCenterActivity;
import com.gl.education.teachingassistant.activity.JFBookMenuActivity;
import com.lzy.okgo.model.Response;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/6/29.
 */

public class ShowResultFragment extends BaseFragment implements OnePriceDialog.SelectListener{

    @BindView(R.id.ques_container)
    LinearLayout ques_container;

    @BindView(R.id.answer_container)
    LinearLayout answer_container;

    @BindView(R.id.answer_text)
    TextView answer_text;

    @BindView(R.id.layout_into_content)
    RelativeLayout layout_into_content;

    @BindView(R.id.btn_into_content)
    ImageView btn_into_content;

    @BindView(R.id.text_introduce)
    TextView text_introduce;

    @BindView(R.id.fankui)
    TextView fankui;

    PhotographResultBean.DataBean dataBean;
    private boolean isLast = false;

    private OnePriceDialog onePriceDialog;

    public CameraIntoContentBean cameraInfo;//存储对应题目的数据


    public static ShowResultFragment newInstance(PhotographResultBean.DataBean parentCategory) {
        ShowResultFragment frag = new ShowResultFragment();
        Bundle b = new Bundle();

        b.putSerializable("parentCategory", parentCategory);

        frag.setArguments(b);
        return frag;
    }

    @Override
    public void init() {
        super.init();
        Bundle args = getArguments();
        if (args != null) {
            dataBean = (PhotographResultBean.DataBean) args.getSerializable("parentCategory");
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (dataBean != null)
            setData();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_show_result;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    public void setData() {
        //题目
        for (int i = 0; i < dataBean.getQues_body().size(); i++) {
            addImg(ques_container, dataBean.getQues_body().get(i));
        }
        //答案
        //显示文字
        if (dataBean.getQues_answer_txt() != null) {
            answer_text.setVisibility(View.VISIBLE);
            RichText.fromHtml("" + dataBean.getQues_answer_txt()).into(answer_text);
            //answer_text.setText("" + dataBean.getQues_answer_txt());
        } else {
            answer_text.setVisibility(View.GONE);
            for (int i = 0; i < dataBean.getQues_answer_pic().size(); i++) {
                if (i == dataBean.getQues_answer_pic().size() - 1) {
                    isLast = true;
                } else {
                    isLast = false;
                }
                addImg(answer_container, dataBean.getQues_answer_pic().get(i));
            }
        }

        String vedio_num = dataBean.getVideo_num();

        if (StringUtils.isEmpty(vedio_num)) {
            text_introduce.setText("");
            btn_into_content.setVisibility(View.GONE);
        } else {
            text_introduce.setText("本题提供视频讲解、举一反三和微课");
            btn_into_content.setVisibility(View.VISIBLE);
        }

    }

    //进入精讲
    @OnClick(R.id.btn_into_content)
    public void intoJJ() {
        if (ButtonUtils.isFastDoubleClick(R.id.btn_into_content)) {
            return;
        }
        String token = SPUtils.getInstance().getString(AppConstant.SP_TOKEN, "");
        HomeAPI.getQuesaccessInfo(dataBean.getQues_id(), dataBean.getCatalog_id(), token, new JsonCallback<CameraIntoContentBean>() {
            @Override
            public void onSuccess(Response<CameraIntoContentBean> response) {
                boolean isBuy = response.body().getData().isIsbuy();
                cameraInfo = response.body();
                if (isBuy == false){
                    if (onePriceDialog == null) {
                        onePriceDialog = new OnePriceDialog(getActivity());
                        onePriceDialog.setSelectListener(ShowResultFragment.this);
                    }
                    if (!onePriceDialog.isShowing()) {
                        onePriceDialog.show();
                    }
                }else{
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CameraFinishExplainActivity.class);
                    intent.putExtra("qesid", "" + dataBean.getQues_id());
                    intent.putExtra("catalogid", dataBean.getCatalog_id());
                    startActivity(intent);
                }
            }
        });


    }

    @OnClick(R.id.fankui)
    public void fankui(){
        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        View layout = inflater.inflate(R.layout.dialog_no_find, null);

        AlertDialog costDialog = new AlertDialog.Builder(_mActivity, R.style.dialog_no_find).create();
        costDialog.setCancelable(false);
        costDialog.show();

        Window window = costDialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        costDialog.setContentView(layout);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = _mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth() * 5 / 6; // 设置dialog宽度为屏幕的4/5
        window.setAttributes(lp);

        TextView btn_again = layout.findViewById(R.id.try_again);
        TextView btn_go_jiaofu = layout.findViewById(R.id.go_jiaofu);
        TextView btn_cancel = layout.findViewById(R.id.cancel);

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costDialog.dismiss();
                OpenCameraEvent event = new OpenCameraEvent();
                EventBus.getDefault().post(event);
                _mActivity.finish();
            }
        });

        btn_go_jiaofu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costDialog.dismiss();
                OpenJFChannelEvent event = new OpenJFChannelEvent();
                EventBus.getDefault().post(event);
                _mActivity.finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costDialog.dismiss();
            }
        });
    }



    public void addImg(LinearLayout container, String url) {

        ImageView newImg = new ImageView(getContext());
        //设置想要的图片，相当于android:src="@drawable/image"
        ImageLoader.loadImage(getActivity(), AppConstant.Camera_search_url + url, newImg);
        //设置子控件在父容器中的位置布局，wrap_content,match_parent
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (isLast) {
            params.setMargins(10, 5, 10, 20);
        } else {
            params.setMargins(10, 5, 10, 0);
        }
        newImg.setLayoutParams(params);
        // 也可以自己想要的宽度，参数（int width, int height）均表示px
        // 如dp单位，首先获取屏幕的分辨率在求出密度，根据屏幕ppi=160时，1px=1dp
        //则公式为 dp * ppi / 160 = px ——> dp * dendity = px
        //如设置为48dp：1、获取屏幕的分辨率 2、求出density 3、设置
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int) (48 * density),
                (int) (48 * density));

        //相当于android:layout_marginLeft="8dp"
        params1.leftMargin = 8;

        //addView(View child)，默认往已有的view后面添加，后插入，不设置布局params,默认wrap_content
        container.addView(newImg);
    }

    @Override
    public void onePrice() {

        HomeAPI.dbAmount(new JsonCallback<GetDbCountBean>() {
            @Override
            public void onSuccess(Response<GetDbCountBean> response) {

                if (response.body().getResult() == 1000){
                    double count = response.body().getData().getDbcount();
                    LogUtils.d("count = "+count);
                    if (count>= 1){//大于1  则进入支付界面
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), OrderPayActivity.class);
                        intent.putExtra("price", "" + cameraInfo.getData().getOrderInfo().getPrice());
                        intent.putExtra("guid", "" + cameraInfo.getData().getOrderInfo().getGuid());
                        intent.putExtra("grade_id", "" + cameraInfo.getData().getOrderInfo().getGrade_id());
                        intent.putExtra("count", "" + count);
                        startActivityForResult(intent, 1000);
                    }else{
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), RechargeCenterActivity.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onError(Response<GetDbCountBean> response) {
                super.onError(response);
            }
        });
    }

    @Override
    public void intoJF() {

        Intent intent = new Intent();
        intent.putExtra("url", cameraInfo.getData().getCatalogUrl());
        intent.putExtra("title", "教辅目录");
        intent.setClass(getActivity(), JFBookMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000){
            Intent intent = new Intent();
            intent.setClass(getActivity(), CameraFinishExplainActivity.class);
            intent.putExtra("qesid", "" + dataBean.getQues_id());
            intent.putExtra("catalogid", dataBean.getCatalog_id());
            startActivity(intent);
        }

    }
}
