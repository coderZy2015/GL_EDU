package com.gl.education.person.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.HomeShare;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.adapter.RecommendContentAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetArticleBean;
import com.gl.education.home.model.SetArticleLikeBean;
import com.lzy.okgo.model.Response;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uuzuche.lib_zxing.view.Loading_view;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 推荐的详细内容
 */
public class RecommendContentActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.top_title)
    TextView top_title;

    //评论按钮
    @BindView(R.id.bottom_pinglun)
    RelativeLayout bottom_pinglun;
    //评论内容
    @BindView(R.id.comment_content)
    EditText comment_content;
    //点赞按钮
    @BindView(R.id.bottom_dianzan)
    RelativeLayout bottom_dianzan;
    //收藏按钮
    @BindView(R.id.bottom_shoucang)
    RelativeLayout bottom_shoucang;
    //分享按钮
    @BindView(R.id.bottom_share)
    RelativeLayout bottom_share;

    @BindView(R.id.image_shoucang)
    ImageView image_shoucang;
    @BindView(R.id.image_dianzan)
    ImageView image_dianzan;

    private GetArticleBean viewData;

    private int isLike = 0;
    private int isCollection = 0;

    public String bookTitle = "";

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    RecommendContentAdapter adapter;

    private Loading_view loading;

    private String channel_itemid;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_recommend_content;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();
        channel_itemid = intent.getStringExtra("channel_itemid");
        bookTitle = intent.getStringExtra("title");
        if (bookTitle != null)
            top_title.setText("" + bookTitle);

        adapter = new RecommendContentAdapter(this, getSupportFragmentManager(), "" +
                channel_itemid);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(1);

        viewpager.setCurrentItem(0);  //初始化显示第一个页面

        //初始化点赞收藏按钮
        isCollection = SPUtils.getInstance().getInt(AppConstant.SP_RECOMMEND_COLLECTION +
                channel_itemid, 0);
        isLike = SPUtils.getInstance().getInt(AppConstant.SP_RECOMMEND_ISLIKE + channel_itemid, 0);

        if (isLike == 0) {
            image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan);
        } else {
            image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan_a);
        }

        if (isCollection == 0) {
            image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang);
        } else {
            image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang_a);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R.id.btn_back)
    public void backPressed() {
        onBackPressed();
        finish();
    }

    @OnClick(R.id.bottom_shoucang)
    public void clickShowCang() {
        loading.show();

        isCollection = SPUtils.getInstance().getInt(AppConstant.SP_RECOMMEND_COLLECTION +
                channel_itemid, 0);

        if (isCollection == 0) {
            isCollection = 1;
        } else {
            isCollection = 0;
        }

        HomeAPI.setArticleLike(channel_itemid, isCollection, new JsonCallback<SetArticleLikeBean>
                () {
            @Override
            public void onSuccess(Response<SetArticleLikeBean> response) {
                loading.dismiss();
                if (isCollection == 1) {
                    image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang_a);
                } else {
                    image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang);
                }
                SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_COLLECTION + channel_itemid,
                        isCollection);

            }

            @Override
            public void onError(Response<SetArticleLikeBean> response) {
                super.onError(response);
                loading.dismiss();
            }
        });
    }


    @OnClick(R.id.bottom_share)
    public void sendShare() {

        String url = AppConstant.YY_WEB_DETAIL;
        url = url + "?channel_itemid=" + channel_itemid + "&isShare=true";
        String title = viewData.getData().getTitle();

        HomeShare.shareWeb(this, url, title, "河北教育资源云平台", new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d("onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.showShort("分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                LogUtils.d(throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });

    }


    @OnClick(R.id.bottom_pinglun)
    public void sendPinglun() {

        String content = comment_content.getText().toString();
        if (content.equals("")) {
            ToastUtils.showShort("评论不能为空");
        } else {
            loading.show();
            HomeAPI.setArticleComment(channel_itemid, "0", content, new
                    JsonCallback<SetArticleLikeBean>() {

                        @Override
                        public void onSuccess(Response<SetArticleLikeBean> response) {
                            loading.dismiss();
                            ToastUtils.showShort("发表成功");
                        }

                        @Override
                        public void onError(Response<SetArticleLikeBean> response) {
                            super.onError(response);
                            loading.dismiss();
                            ToastUtils.showShort("网络连接错误");
                        }
                    });
        }

    }


    @OnClick(R.id.bottom_dianzan)
    public void clickDianZan() {
        loading.show();

        isLike = SPUtils.getInstance().getInt(AppConstant.SP_RECOMMEND_ISLIKE + channel_itemid, 0);

        if (isLike == 0) {
            isLike = 1;
        } else {
            isLike = 0;
        }

        HomeAPI.setArticleLike(channel_itemid, isLike, new JsonCallback<SetArticleLikeBean>() {
            @Override
            public void onSuccess(Response<SetArticleLikeBean> response) {
                loading.dismiss();
                if (isLike == 1) {
                    image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan_a);
                } else {
                    image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan);
                }
                SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_ISLIKE + channel_itemid, isLike);
            }

            @Override
            public void onError(Response<SetArticleLikeBean> response) {
                super.onError(response);
                loading.dismiss();
            }
        });
    }

    //初始化页面按钮
    public void initViewData(GetArticleBean _bean) {
        viewData = _bean;
        //        if (viewData.getData().getLikes() == 1){
        //            bottom_dianzan.setBackgroundResource(R.drawable.bottom_dianzan_a);
        //        }else{
        //            bottom_dianzan.setBackgroundResource(R.drawable.bottom_dianzan);
        //        }

    }

}
