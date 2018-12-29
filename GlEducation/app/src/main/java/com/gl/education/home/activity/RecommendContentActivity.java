package com.gl.education.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.app.HomeShare;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.adapter.RecommendContentAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.AarticleVisitorBean;
import com.gl.education.home.model.GetArticlInfoBean;
import com.gl.education.home.model.GetArticleBean;
import com.gl.education.home.model.SetArticleLikeBean;
import com.gl.education.home.utlis.CommentDialog;
import com.gl.education.login.LoginInfoActivity;
import com.lzy.okgo.model.Response;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uuzuche.lib_zxing.view.Loading_view;

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
//    //评论内容
//    @BindView(R.id.comment_content)
//    EditText comment_content;
    //评论内容
    @BindView(R.id.send_comment)
    TextView send_comment;

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

    @BindView(R.id.zan_num)
    TextView zan_num;
    @BindView(R.id.recommend_num)
    TextView recommend_num;

    private GetArticleBean viewData = null;

    private int isLike = 0;
    private int isCollection = 0;

    public String bookTitle = "";

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    RecommendContentAdapter adapter;

    private CommentDialog commentDialog;

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
        AppCommonData.moreRecommendcontentID++;
        loading = new Loading_view(this, com.uuzuche.lib_zxing.R.style.CustomDialog);

        Intent intent = getIntent();
        channel_itemid = intent.getStringExtra("channel_itemid");

        //用户浏览记录，千人千面基于此。
        HomeAPI.setArticleVisitors(channel_itemid, new JsonCallback<AarticleVisitorBean>() {
            @Override
            public void onSuccess(Response<AarticleVisitorBean> response) {
                LogUtils.d("用户浏览了"+channel_itemid);
            }
        });

        if (bookTitle != null)
            top_title.setText("冠林教育");

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

        //获取点赞数，收藏数
        HomeAPI.getArticlInfoUrl(channel_itemid, "0", new JsonCallback<GetArticlInfoBean>() {
            @Override
            public void onSuccess(Response<GetArticlInfoBean> response) {
                if (response.body().getResult() == 1000){
                    int likes = response.body().getData().getLikes();
                    int commentsTotal = response.body().getData().getCommentsTotal();

                    if (zan_num!=null)
                    zan_num.setText(""+likes);

                    if (recommend_num!=null){
                        if (commentsTotal != 0){
                            recommend_num.setVisibility(View.VISIBLE);
                            recommend_num.setText(""+commentsTotal);
                        }else{
                            recommend_num.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            }
        });

    }

    @OnClick(R.id.send_comment)
    public void onClickComment(){
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(RecommendContentActivity.this, LoginInfoActivity.class);
            startActivity(intent);
            return;
        }

        if (commentDialog == null) {
            commentDialog = new CommentDialog(RecommendContentActivity.this);
        }
        if (!commentDialog.isShowing()) {
            commentDialog.show();
            commentDialog.setAnInterface(new CommentDialog.CommentInterface() {
                @Override
                public void send(String str) {
                    HomeAPI.setArticleComment(channel_itemid, "0", str, new
                            JsonCallback<SetArticleLikeBean>() {

                                @Override
                                public void onSuccess(Response<SetArticleLikeBean> response) {
                                    if (loading != null){
                                        loading.dismiss();
                                    }
                                    if (response.body().getResult() == 1000){
                                        ToastUtils.showShort("发表成功");
                                        commentDialog.editText.setText("");
                                        commentDialog.editText.clearFocus();

                                        try {
                                            //取消小键盘
                                            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(commentDialog.editText.getWindowToken(),0);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        commentDialog.dismiss();
                                    }else{
                                        ToastUtils.showShort("发表失败");
                                        commentDialog.dismiss();
                                    }

                                }
                                @Override
                                public void onError(Response<SetArticleLikeBean> response) {
                                    super.onError(response);
                                    loading.dismiss();
                                    ToastUtils.showShort("网络连接错误");
                                }
                            });
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppCommonData.moreRecommendcontentID -- ;
        LogUtils.d("onDestroy  "+AppCommonData.moreRecommendcontentID);
    }

    @OnClick(R.id.btn_back)
    public void backPressed() {
        if (viewpager.getCurrentItem() == 1){
            viewpager.setCurrentItem(0);  //初始化显示第一个页面
            return;
        }

        onBackPressed();
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        if (viewpager.getCurrentItem() == 1){
            viewpager.setCurrentItem(0);  //初始化显示第一个页面
            return;
        }
        super.onBackPressedSupport();
    }

    @OnClick(R.id.bottom_shoucang)
    public void clickShowCang() {
        if (!AppCommonData.isLogin){
            Intent intent = new Intent();
            intent.setClass(this, LoginInfoActivity.class);
            startActivity(intent);
            return;
        }
        loading.show();

        isCollection = SPUtils.getInstance().getInt(AppConstant.SP_RECOMMEND_COLLECTION +
                channel_itemid, 0);

        if (isCollection == 0) {
            isCollection = 1;
        } else {
            isCollection = 0;
        }

        //收藏
        HomeAPI.setArticleFavorite(channel_itemid, isCollection, new JsonCallback<SetArticleLikeBean>
                () {
            @Override
            public void onSuccess(Response<SetArticleLikeBean> response) {
                loading.dismiss();
                if (response.body().getResult() == 1000){
                    if (isCollection == 1) {
                        image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang_a);
                        ToastUtils.showShort("收藏成功");
                        SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_COLLECTION + channel_itemid,
                                isCollection);
                    } else {
                        image_shoucang.setBackgroundResource(R.drawable.bottom_shoucang);
                        ToastUtils.showShort("取消收藏");
                        SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_COLLECTION + channel_itemid,
                                0);
                    }
                }

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
        //未加载完数据前不能分享
        if (viewData == null){
            return;
        }
        String url = AppConstant.YY_WEB_SHARE;
        url = url + "?channel_itemid=" + channel_itemid;
        String title = viewData.getData().getTitle();

        HomeShare.shareWeb(this, url, title, "河北教育资源云平台", new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d("onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.showShort("分享成功");
                //分享成功回调服务器
                HomeAPI.articleShareCallback(channel_itemid, new JsonCallback<SetArticleLikeBean>() {

                    @Override
                    public void onSuccess(Response<SetArticleLikeBean> response) {
                    }
                });
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
        if (viewpager.getCurrentItem() == 0){
            viewpager.setCurrentItem(1);  //初始化显示第一个页面
        }else{
            viewpager.setCurrentItem(0);  //初始化显示第一个页面
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
                if (response.body().getResult() == 1000){
                    if (isLike == 1) {
                        image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan_a);
                        SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_ISLIKE + channel_itemid, isLike);
                        String zan = zan_num.getText().toString();
                        int num = Integer.parseInt(zan) + 1;
                        zan_num.setText(""+num);
                    } else {
                        image_dianzan.setBackgroundResource(R.drawable.bottom_dianzan);
                        SPUtils.getInstance().put(AppConstant.SP_RECOMMEND_ISLIKE + channel_itemid, 0);
                        String zan = zan_num.getText().toString();
                        int num = Integer.parseInt(zan) - 1;
                        zan_num.setText(""+num);
                    }
                }

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
    }

}
