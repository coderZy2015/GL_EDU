package com.gl.education.camera.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gl.education.R;
import com.gl.education.camera.activity.PhotographResultBean;
import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.utlis.CustomViewPager;
import com.gl.education.home.utlis.ImageLoader;

import butterknife.BindView;

/**
 * Created by zy on 2018/6/29.
 */

public class ShowResultFragment extends BaseFragment {

    public static final String DownloadPic_Url = "http://jiaofu-image.oss-cn-qingdao.aliyuncs.com";

    @BindView(R.id.ques_container)
    LinearLayout ques_container;

    @BindView(R.id.answer_container)
    LinearLayout answer_container;

    @BindView(R.id.answer_text)
    TextView answer_text;

    PhotographResultBean.DataBean dataBean;
    private boolean isLast = false;

    CustomViewPager vp;
    private int fragmentID=0;


    public static ShowResultFragment newInstance(PhotographResultBean.DataBean parentCategory) {
        ShowResultFragment frag = new ShowResultFragment();
        Bundle b = new Bundle();

        b.putSerializable("parentCategory", parentCategory);

        frag.setArguments(b);
        return frag;
    }

    public void setViewPagerID(CustomViewPager vp, int fragmentID)
    {
        this.vp = vp;
        this.fragmentID =fragmentID;
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

    public void setData() {
        //题目
        for (int i = 0; i < dataBean.getQues_body().size(); i++) {
            addImg(ques_container, dataBean.getQues_body().get(i));
        }
        //答案
        //显示文字
        if (dataBean.getQues_answer_txt() != null){
            answer_text.setVisibility(View.VISIBLE);
            answer_text.setText(""+dataBean.getQues_answer_txt());
        }else{
            answer_text.setVisibility(View.GONE);
             for (int i=0; i<dataBean.getQues_answer_pic().size(); i++){
                 if (i == dataBean.getQues_answer_pic().size() - 1){
                     isLast = true;
                 }else{
                     isLast = false;
                 }
                 addImg(answer_container, dataBean.getQues_answer_pic().get(i));
              }
        }

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

    public void addImg(LinearLayout container, String url) {

        ImageView newImg = new ImageView(getContext());
        //设置想要的图片，相当于android:src="@drawable/image"
        ImageLoader.loadImage(getActivity(), DownloadPic_Url + url, newImg);
        //设置子控件在父容器中的位置布局，wrap_content,match_parent
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (isLast){
            params.setMargins(10, 5, 10, 20);
        }else{
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

        //        //addView(View child, LayoutParams params)，往已有的view后面添加，后插入,并设置布局
        //        contentLlayout.addView(newImg,params1);
        //
        //        //addView(View view,int index, LayoutParams params),在某个index处插入
        //        contentLlayout.addView(newImg, 0, params1);

    }
}
