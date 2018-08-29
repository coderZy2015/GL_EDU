package com.gl.education.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gl.education.R;
import com.gl.education.app.AppCommonData;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.activity.ChannelActivity;
import com.gl.education.home.activity.HomePageActivity;
import com.gl.education.home.event.ReloadChannelEvent;
import com.gl.education.home.helper.OnDragVHListener;
import com.gl.education.home.helper.OnItemMoveListener;
import com.gl.education.home.model.ChannelEntity;
import com.gl.education.home.model.SetUserChannelGradeBean;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/7/9.
 * 年级、频道 选择器
 */

/**
 * 拖拽排序 + 增删
 * Created by YoKeyword on 15/12/28.
 */
public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        OnItemMoveListener {

    public static final int TYPE_GRADE = 4;
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;
    // 确定按钮
    public static final int TYPE_SURE = 5;

    //我的年级的header
    private static final int COUNT_PRE_MY_GRADE = 1;
    // 我的频道之前的header数量  该demo中 即标题部分 为 2
    private static final int COUNT_PRE_MY_HEADER = COUNT_PRE_MY_GRADE + 1;
    // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

    private static final long ANIM_TIME = 360L;

    // touch 点击开始时间
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;

    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;

    // 是否为 编辑 模式
    private boolean isEditMode;

    private Context mContext;

    private List<ChannelEntity> mMyChannelItems, mOtherChannelItems;

    // 我的频道点击事件
    private OnMyChannelItemClickListener mChannelItemClickListener;
    // 我的频道点击事件
    private OnMyGradeItemClickListener myGradeItemClickListener;

    public int selectGrade = 1;//一年级

    //年级的字符串data
    private List<String> gradeDataStringList = new ArrayList<>();
    //年级的data
    private List<GradeData> gradeDataList = new ArrayList<>();

    public ChannelAdapter(Context context, ItemTouchHelper helper, List<ChannelEntity>
            mMyChannelItems, List<ChannelEntity> mOtherChannelItems) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.mMyChannelItems = mMyChannelItems;
        this.mOtherChannelItems = mOtherChannelItems;
        selectGrade = AppCommonData.userGrade;
        initGradeDate();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {    // 我的频道 标题部分
            return TYPE_GRADE;
        } else if (position == 1) {
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {    // 其他频道 标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {
            return TYPE_MY;
        } else if (position == mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER) {
            return TYPE_SURE;
        } else {
            return TYPE_OTHER;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case TYPE_GRADE:
                view = mInflater.inflate(R.layout.item_grade_select, parent, false);
                return new SelectMyGradeHeaderViewHolder(view);

            case TYPE_MY_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_my_channel_header, parent, false);
                final MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);

                //点击编辑按钮
                holder.tvBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            startEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.finish);
                        } else {
                            cancelEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.edit);
                        }
                    }
                });
                return holder;

            case TYPE_MY:
                view = mInflater.inflate(R.layout.item_my, parent, false);
                final MyViewHolder myHolder = new MyViewHolder(view);

                //点击 我的频道的item 进行跳转或去除
                myHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        int position = myHolder.getAdapterPosition();
                        if (isEditMode) {
                            if (position == COUNT_PRE_MY_HEADER){
                                return;
                            }
                            RecyclerView recyclerView = ((RecyclerView) parent);
                            View targetView = recyclerView.getLayoutManager().findViewByPosition
                                    (mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
                            View currentView = recyclerView.getLayoutManager().findViewByPosition
                                    (position);
                            // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,
                            // 因为此时notifyItemMoved自带一个向目标移动的动画
                            // 如果在屏幕内,则添加一个位移动画

                            if (recyclerView.indexOfChild(targetView) >= 0) {
                                int targetX, targetY;

                                RecyclerView.LayoutManager manager = recyclerView
                                        .getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();

                                // 移动后 高度将变化 (我的频道Grid 最后一个item在新的一行第一个)
                                if ((mMyChannelItems.size() - COUNT_PRE_MY_HEADER) % spanCount ==
                                        0) {
                                    View preTargetView = recyclerView.getLayoutManager()
                                            .findViewByPosition(mMyChannelItems.size() +
                                                    COUNT_PRE_OTHER_HEADER - 1);
                                    targetX = preTargetView.getLeft();
                                    targetY = preTargetView.getTop();
                                } else {
                                    targetX = targetView.getLeft();
                                    targetY = targetView.getTop();
                                }

                                moveMyToOther(myHolder);
                                startAnimation(recyclerView, currentView, targetX, targetY);

                            } else {
                                moveMyToOther(myHolder);
                            }
                        } else {
                            //点击事件
                            mChannelItemClickListener.onItemClick(v, position -
                                    COUNT_PRE_MY_HEADER);
                        }
                    }
                });

                //长按 我的频道的item 进入编辑模式
                myHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        //排除2个头布局
                        if (myHolder.getLayoutPosition() == COUNT_PRE_MY_HEADER){
                            return false;
                        }
                        if (!isEditMode) {
                            RecyclerView recyclerView = ((RecyclerView) parent);
                            startEditMode(recyclerView);

                            // header 按钮文字 改成 "完成"
                            View view = recyclerView.getChildAt(1);
                            if (view == recyclerView.getLayoutManager().findViewByPosition(0)) {
                                TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                                tvBtnEdit.setText(R.string.finish);
                            }
                        }

                        //mItemTouchHelper.startDrag(myHolder);

                        return true;
                    }
                });


//                myHolder.textView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (isEditMode) {
//                            switch (MotionEventCompat.getActionMasked(event)) {
//                                case MotionEvent.ACTION_DOWN:
//                                    startTime = System.currentTimeMillis();
//                                    break;
//                                case MotionEvent.ACTION_MOVE:
//                                    if (System.currentTimeMillis() - startTime > SPACE_TIME) {
//                                        mItemTouchHelper.startDrag(myHolder);
//                                    }
//                                    break;
//                                case MotionEvent.ACTION_CANCEL:
//                                case MotionEvent.ACTION_UP:
//                                    startTime = 0;
//                                    break;
//                            }
//
//                        }
//                        return false;
//                    }
//                });
                return myHolder;

            case TYPE_OTHER_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_other_channel_header, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };

            case TYPE_SURE:
                view = mInflater.inflate(R.layout.item_channel_sure, parent, false);
                ChannelSureViewHolder channelSureHolder = new ChannelSureViewHolder(view);
                //点击确定按钮
                channelSureHolder.btnSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Gson gson = new Gson();
                        String json = gson.toJson(mMyChannelItems);
                        String str = new String(EncodeUtils.base64Encode(json));

                        HomeAPI.setUserChannelGrade(selectGrade, str, new JsonCallback<SetUserChannelGradeBean>() {
                            @Override
                            public void onSuccess(Response<SetUserChannelGradeBean> response) {
                                if (response.body().getResult() == 1000){
                                    if (((ChannelActivity)mContext).openFrom == ChannelActivity.FROM_BEGIN){
                                        Intent intent = new Intent();
                                        intent.setClass(mContext, HomePageActivity.class);
                                        mContext.startActivity(intent);
                                        AppCommonData.userGrade = selectGrade;
                                        ((ChannelActivity)mContext).finish();
                                    }
                                    if (((ChannelActivity)mContext).openFrom == ChannelActivity.FROM_MAIN){
                                        AppCommonData.userGrade = selectGrade;
                                        ReloadChannelEvent event = new ReloadChannelEvent();

//                                        //当前观看频道位置切换
//                                        if (!mMyChannelItems.get(mPostion).getName().equals(mName)){
//                                             for (int i=0; i<mMyChannelItems.size(); i++) {
//                                                 if (mMyChannelItems.get(i).getName().equals(mName)) {
//                                                     event.setSelectChannel(i);
//                                                 }
//                                             }
//                                        }

                                        EventBus.getDefault().post(event);
                                        ((ChannelActivity)mContext).finish();
                                    }
                                }
                            }

                            @Override
                            public void onError(Response<SetUserChannelGradeBean> response) {
                                super.onError(response);
                                ToastUtils.showShort("网络连接失败，请检查网络");
                            }
                        });
                    }
                });
                return channelSureHolder;

            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.item_other, parent, false);
                final OtherViewHolder otherHolder = new OtherViewHolder(view);
                otherHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView recyclerView = ((RecyclerView) parent);
                        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                        int currentPiosition = otherHolder.getAdapterPosition();
                        // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
                        View currentView = manager.findViewByPosition(currentPiosition);
                        // 目标位置的前一个item  即当前MyChannel的最后一个
                        View preTargetView = manager.findViewByPosition(mMyChannelItems.size() -
                                1 + COUNT_PRE_MY_HEADER);

                        // 如果targetView不在屏幕内,则为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                        // 如果在屏幕内,则添加一个位移动画
                        if (recyclerView.indexOfChild(preTargetView) >= 0) {
                            int targetX = preTargetView.getLeft();
                            int targetY = preTargetView.getTop();

                            int targetPosition = mMyChannelItems.size() - 1 +
                                    COUNT_PRE_OTHER_HEADER;

                            GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
                            int spanCount = gridLayoutManager.getSpanCount();
                            // target 在最后一行第一个
                            if ((targetPosition - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                View targetView = manager.findViewByPosition(targetPosition);
                                targetX = targetView.getLeft();
                                targetY = targetView.getTop();
                            } else {
                                targetX += preTargetView.getWidth();

                                // 最后一个item可见
                                if (gridLayoutManager.findLastVisibleItemPosition() ==
                                        getItemCount() - 1) {
                                    // 最后的item在最后一行第一个位置
                                    if ((getItemCount() - 1 - mMyChannelItems.size() -
                                            COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                                        // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 +
                                        // item.height
                                        int firstVisiblePostion = gridLayoutManager
                                                .findFirstVisibleItemPosition();
                                        if (firstVisiblePostion == 0) {
                                            // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕
                                            // , targetY值不需要变化
                                            // // FirstCompletelyVisibleItemPosition != 0 即
                                            // 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                                            if (gridLayoutManager
                                                    .findFirstCompletelyVisibleItemPosition() !=
                                                    0) {
                                                int offset = (-recyclerView.getChildAt(0).getTop
                                                        ()) - recyclerView.getPaddingTop();
                                                targetY += offset;
                                            }
                                        } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的
                                            // position != 0),
                                            // 移动后, targetY值  + 一个item的高度
                                            targetY += preTargetView.getHeight();
                                        }
                                    }
                                } else {
                                    System.out.println("current--No");
                                }
                            }

                            // 如果当前位置是otherChannel可见的最后一个
                            // 并且 当前位置不在grid的第一个位置
                            // 并且 目标位置不在grid的第一个位置

                            // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
                            // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
                            if (currentPiosition == gridLayoutManager.findLastVisibleItemPosition()
                                    && (currentPiosition - mMyChannelItems.size() -
                                    COUNT_PRE_OTHER_HEADER) % spanCount != 0
                                    && (targetPosition - COUNT_PRE_MY_HEADER) % spanCount != 0) {
                                moveOtherToMyWithDelay(otherHolder);
                            } else {
                                moveOtherToMy(otherHolder);
                            }
                            startAnimation(recyclerView, currentView, targetX, targetY);

                        } else {
                            moveOtherToMy(otherHolder);
                        }
                    }
                });
                return otherHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyViewHolder myHolder = (MyViewHolder) holder;
            myHolder.textView.setText(mMyChannelItems.get(position - COUNT_PRE_MY_HEADER).getName
                    ());
            if (isEditMode) {
                myHolder.imgEdit.setVisibility(View.VISIBLE);
            } else {
                myHolder.imgEdit.setVisibility(View.INVISIBLE);
            }

        } else if (holder instanceof OtherViewHolder) {

            ((OtherViewHolder) holder).textView.setText(mOtherChannelItems.get(position -
                    mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER).getName());

        } else if (holder instanceof MyChannelHeaderViewHolder) {//我的频道

            MyChannelHeaderViewHolder headerHolder = (MyChannelHeaderViewHolder) holder;
            if (isEditMode) {
                headerHolder.tvBtnEdit.setText(R.string.finish);
            } else {
                headerHolder.tvBtnEdit.setText(R.string.edit);
            }
        } else if (holder instanceof SelectMyGradeHeaderViewHolder) {//我的年级栏目
            SelectMyGradeHeaderViewHolder headerHolder = (SelectMyGradeHeaderViewHolder) holder;

            if (headerHolder.recyclerView.getAdapter() == null) {
                final ChannelGradeAdapter adapter = new ChannelGradeAdapter(R.layout
                        .item_grade_bg, gradeDataList);
                headerHolder.recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter_, View view, int position) {
                        adapter.setSelectPostion(position);
                        selectGrade = position + 1;
                    }
                });

            } else {
                headerHolder.recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

    }

    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size + 我的年级 +确定
        return mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER + 1;
    }

    /**
     * 开始增删动画
     */
    private void startAnimation(RecyclerView recyclerView, final View currentView, float targetX,
                                float targetY) {
        final ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();
        final ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView);

        Animation animation = getTranslateAnimator(
                targetX - currentView.getLeft(), targetY - currentView.getTop());
        currentView.setVisibility(View.INVISIBLE);
        mirrorView.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewGroup.removeView(mirrorView);
                if (currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 我的频道 移动到 其他频道
     *
     * @param myHolder
     */
    private void moveMyToOther(MyViewHolder myHolder) {
        int position = myHolder.getAdapterPosition();

        int startPosition = position - COUNT_PRE_MY_HEADER;
        if (startPosition > mMyChannelItems.size() - 1) {
            return;
        }
        ChannelEntity item = mMyChannelItems.get(startPosition);
        mMyChannelItems.remove(startPosition);
        mOtherChannelItems.add(0, item);

        notifyItemMoved(position, mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
    }

    /**
     * 其他频道 移动到 我的频道
     *
     * @param otherHolder
     */
    private void moveOtherToMy(OtherViewHolder otherHolder) {
        int position = processItemRemoveAdd(otherHolder);
        if (position == -1) {
            return;
        }
        notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
    }

    /**
     * 其他频道 移动到 我的频道 伴随延迟
     *
     * @param otherHolder
     */
    private void moveOtherToMyWithDelay(OtherViewHolder otherHolder) {
        final int position = processItemRemoveAdd(otherHolder);
        if (position == -1) {
            return;
        }
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
            }
        }, ANIM_TIME);
    }

    private Handler delayHandler = new Handler();

    private int processItemRemoveAdd(OtherViewHolder otherHolder) {
        int position = otherHolder.getAdapterPosition();

        int startPosition = position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER;
        if (startPosition > mOtherChannelItems.size() - 1) {
            return -1;
        }
        ChannelEntity item = mOtherChannelItems.get(startPosition);
        mOtherChannelItems.remove(startPosition);
        mMyChannelItems.add(item);
        return position;
    }


    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view) {
        /**
         * 我们要获取cache首先要通过setDrawingCacheEnable方法开启cache，然后再调用getDrawingCache方法就可以获得view的cache图片了。
         buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，若果cache没有建立，系统会自动调用buildDrawingCache
         方法生成cache。
         若想更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         当调用setDrawingCacheEnabled方法设置为false, 系统也会自动把原来的cache销毁。
         */
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(recyclerView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);//以屏幕为坐标点的坐标
        int[] parenLocations = new int[2];
        recyclerView.getLocationOnScreen(parenLocations);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap
                .getHeight());
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
        parent.addView(mirrorView, params);

        return mirrorView;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ChannelEntity item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void startEditMode(RecyclerView parent) {
        isEditMode = true;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null && i != 2) {
                imgEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 完成编辑模式
     *
     * @param parent
     */
    private void cancelEditMode(RecyclerView parent) {
        isEditMode = false;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 获取位移动画
     */
    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY);
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public interface OnMyChannelItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnMyChannelItemClickListener(OnMyChannelItemClickListener listener) {
        this.mChannelItemClickListener = listener;
    }

    public interface OnMyGradeItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnMyGradeItemClickListener(OnMyGradeItemClickListener listener) {
        this.myGradeItemClickListener = listener;
    }

    /**
     * 我的频道
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        public TextView textView;
        public ImageView imgEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
        }

        /**
         * item 被选中时
         */
        @Override
        public void onItemSelected() {
            textView.setBackgroundResource(R.drawable.bg_channel_p);
        }

        /**
         * item 取消选中时
         */
        @Override
        public void onItemFinish() {
            textView.setBackgroundResource(R.drawable.bg_channel);
        }
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    /**
     * 我的频道  标题部分
     */
    class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBtnEdit;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            tvBtnEdit = (TextView) itemView.findViewById(R.id.tv_btn_edit);
        }
    }

    /**
     * 确定按钮
     */
    class ChannelSureViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnSure;

        public ChannelSureViewHolder(View itemView) {
            super(itemView);
            btnSure = itemView.findViewById(R.id.btn_sure);
        }
    }


    /**
     * 年纪选择部分
     */
    class SelectMyGradeHeaderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;

        public SelectMyGradeHeaderViewHolder(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.grade_recycler);

            RecyclerView.LayoutManager manager = new GridLayoutManager(itemView.getContext(), 4);
            // 需要注意的是GridLayoutManager要设置setAutoMeasureEnabled(true)成自适应高度
            manager.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(manager);
        }
    }

    public void initGradeDate() {

        gradeDataStringList.add("一年级");
        gradeDataStringList.add("二年级");
        gradeDataStringList.add("三年级");
        gradeDataStringList.add("四年级");
        gradeDataStringList.add("五年级");
        gradeDataStringList.add("六年级");
        gradeDataStringList.add("七年级");
        gradeDataStringList.add("八年级");
        gradeDataStringList.add("九年级");
        gradeDataStringList.add("高中必修");
        gradeDataStringList.add("高中选修");

        for (int i = 0; i < gradeDataStringList.size(); i++) {
            GradeData data = new GradeData();
            data.id = i;
            data.gradeStr = gradeDataStringList.get(i);
            gradeDataList.add(data);
        }
    }

    public class GradeData {
        int id;
        String gradeStr;
    }
}
