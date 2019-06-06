package com.work.guaishouxingqiu.aboutball.home.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.fragment.HomeFragment;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.AboutBallDetailsActivity;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 14:32
 * 更新时间: 2019/3/13 14:32
 * 描述: 首页推荐数据,当集合下标为4和9的时候，分别添加场馆和约球item
 */
public class RecommendedAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, List<ResultNewsBean>> {
    //纯文本内容
    public static final int TYPE_TEXT = 1000;
    //单图+文本
    public static final int TYPE_SING_IMAGE = 1001;
    //三图
    public static final int TYPE_THREE_IMAGE = 1003;
    public static final int POSITION_VENUE_ITEM = 4;
    public static final int POSITION_BALL_ITEM = 9;
    public static final int TYPE_VENUE = 0;
    public static final int TYPE_BALL = 1;
    private int mPosition;
    private List<ResultRecommendDataBean.Stadium> mStadiumData;
    private List<ResultRecommendDataBean.AgreeBallMatch> mBallData;
    private Fragment mFragment;

    public RecommendedAdapter(@NonNull List<ResultNewsBean> data) {
        super(data);
        init();
    }

    private void init() {

    }


    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        this.mPosition = position;
        return super.getItemViewType(position);
    }

    public void notifyData(@NonNull Fragment fragment, List<ResultRecommendDataBean.Stadium> stadiumData, List<ResultRecommendDataBean.AgreeBallMatch> ballData) {
        mFragment = fragment;
        this.mStadiumData = stadiumData;
        this.mBallData = ballData;
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (isHaveHeadView) {
            mPosition--;
        }
        LogUtils.w("onCreateDataViewHolder--", mPosition + "--");
        // LogUtils.w("onCreateViewHolder---", mPosition + "--" + mData.get(mPosition).coverImgType + "--" + mData.get(mPosition).typeId);
        if (mPosition == RecommendedAdapter.POSITION_VENUE_ITEM && mData.get(mPosition) == null) {
            //场馆ViewHolder
            viewHolder = new HomeVenueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_venues_home_view, viewGroup, false), mStadiumData);
        } else if (mPosition == RecommendedAdapter.POSITION_BALL_ITEM && mData.get(mPosition) == null) {
            //约球ViewHolder 记得删除item_test_view
            viewHolder = new HomeBallHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_ball_view, viewGroup, false), mBallData, mFragment);
        } else {
            if (mData.get(mPosition).typeId.equals(Contast.VIDEO_RECOMMENDED_TYPE)) {
                viewHolder = new VideoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_video_view, viewGroup, false));

            } else {
                String imageType = mData.get(mPosition).coverImgType;
                switch (imageType) {
                    case "0":
                        viewHolder = new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_text_view, viewGroup, false));
                        break;
                    case "1":
                        viewHolder = new SingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_sing_image, viewGroup, false));
                        break;
                    case "3":
                        viewHolder = new ThreeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_three_image, viewGroup, false));
                        break;
                    default:
                        break;
                }
            }
        }

        return viewHolder;
    }


    private void addTop(ResultNewsBean bean, TextView textView) {
        if (bean == null) {
            return;
        }
        bean.isRead = DataUtils.isReadNews(bean.newsId);
        if (bean.isRead) {
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorFF777777));
        } else {
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_4));
        }
        if (bean.onTop == 1) {
            String content = "A ".concat(bean.title);
            textView.setText(SpanUtils.getTextDrawable(R.mipmap.icon_top, 0, 1, content));
        } else {
            textView.setText(bean.title);
        }
    }

    private void showHotView(ResultNewsBean bean, TextView view) {
        if (Integer.valueOf(bean.commentCount) >= 10) {
            view.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_hot, 0, 0, 0);
        } else {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    protected void onBindViewDataHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ResultNewsBean bean = mData.get(i);
        viewHolder.setIsRecyclable(false);
        if (viewHolder instanceof TextViewHolder) {
            TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
            // textViewHolder.mTvData.setText(mData.get(i).title);
            addTop(bean, textViewHolder.mTvData);
            textViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
            setViewLine(textViewHolder.mLine, i);
            this.showHotView(bean, textViewHolder.mTvFrom);

        } else if (viewHolder instanceof SingViewHolder) {
            SingViewHolder singViewHolder = (SingViewHolder) viewHolder;
            //singViewHolder.mTvData.setText(mData.get(i).title);
            addTop(bean, singViewHolder.mTvData);
            singViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source == null ? "" : mData.get(i).source, mData.get(i).releaseTime));
            if (!DataUtils.isEmpty(mData.get(i).coverUrl)) {
                String imagePath;
                if (DataUtils.splitImagePathCount(mData.get(i).coverUrl) > 0) {
                    String[] imagePathArray = mData.get(i).coverUrl.split(",");
                    imagePath = imagePathArray[0];
                } else {
                    imagePath = mData.get(i).coverUrl;
                }
                GlideManger.get().loadImage(viewHolder.itemView.getContext(), imagePath,
                        R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, singViewHolder.mIvData);
            }
            setViewLine(singViewHolder.mLine, i);
            this.showHotView(bean, singViewHolder.mTvFrom);
        } else if (viewHolder instanceof ThreeViewHolder) {
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
            //threeViewHolder.mFtvData.setText(mData.get(i).title);
            addTop(bean, threeViewHolder.mTvData);
            threeViewHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, mData.get(i).releaseTime));
            if (mData.get(i).coverUrl != null) {
                String[] images = mData.get(i).coverUrl.split(",");
                for (int j = 0; j < images.length; j++) {
                    switch (j) {
                        case 0:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[0],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv1);
                            break;
                        case 1:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[1],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv2);
                            break;
                        case 2:
                            GlideManger.get().loadImage(viewHolder.itemView.getContext(), images[2],
                                    R.drawable.shape_item_recommend_preview_item, R.drawable.shape_item_recommend_preview_item, threeViewHolder.mIv3);
                            break;
                    }
                }
            }

            setViewLine(threeViewHolder.mLine, i);
            this.showHotView(bean, threeViewHolder.mTvFrom);

        } else if (viewHolder instanceof VideoHolder) {
            VideoHolder videoHolder = (VideoHolder) viewHolder;
            // videoHolder.mTvContent.setText(mData.get(i).title);
            videoHolder.mTvFrom.setText(UIUtils.getString(R.string.from_data, mData.get(i).source, DataUtils.getNotNullData(mData.get(i).releaseTime)));
            addTop(bean, videoHolder.mTvContent);
           /* GlideManger.get().loadImageDrawable(mData.get(i).coverUrl, new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    LogUtils.w("loadImageDrawable--", resource + "--");
                    mVideoHolder.mTvVideo.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    mVideoHolder.mTvVideo.setBackground(placeholder);
                }
            });*/
            GlideManger.get().loadImage(mContext, mData.get(i).coverUrl, R.mipmap.icon_default_banner,
                    R.mipmap.icon_default_banner, videoHolder.mTvVideo);
            this.showHotView(bean, videoHolder.mTvFrom);
            setViewLine(videoHolder.mLine, i);
        }

    }

    private void setViewLine(View line, int position) {
        if (POSITION_VENUE_ITEM >= mData.size() || POSITION_BALL_ITEM >= mData.size()) {
            return;
        }
        if ((position == POSITION_VENUE_ITEM - 1 || position == POSITION_BALL_ITEM - 1)
                && (mData.get(POSITION_VENUE_ITEM) == null || mData.get(POSITION_BALL_ITEM) == null)) {
            line.setVisibility(View.INVISIBLE);
        } else {
            line.setVisibility(View.VISIBLE);
        }
    }


    static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvData, mTvFrom;
        private View mLine;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvData = itemView.findViewById(R.id.tv_data);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
        }
    }

    static class SingViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvData;
        private TextView mTvData;
        private TextView mTvFrom;
        private View mLine;

        public SingViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIvData = itemView.findViewById(R.id.iv_data);
            mTvData = itemView.findViewById(R.id.tv_data);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
        }
    }

    static class ThreeViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIv1;
        private ImageView mIv2;
        private ImageView mIv3;
        private TextView mTvFrom;
        private View mLine;
        private TextView mTvData;

        public ThreeViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mIv1 = itemView.findViewById(R.id.iv_1);
            mIv2 = itemView.findViewById(R.id.iv_2);
            mIv3 = itemView.findViewById(R.id.iv_3);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
            mTvData = itemView.findViewById(R.id.tv_content);
        }
    }

    static class VideoHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private RoundedImageView mTvVideo;
        private ImageView mIvPlay;
        private TextView mTvFrom;
        private View mLine;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvVideo = itemView.findViewById(R.id.tx_video);
            mIvPlay = itemView.findViewById(R.id.iv_play);
            mTvFrom = itemView.findViewById(R.id.tv_from);
            mLine = itemView.findViewById(R.id.line);
        }
    }

    static class HomeVenueViewHolder extends RecyclerView.ViewHolder {

        private View mRlMore;
        private RecyclerView mRvVenue;
        private HomeVenueAdapter mAdapter;

        public HomeVenueViewHolder(@NonNull View itemView, List<ResultRecommendDataBean.Stadium> data) {
            super(itemView);
            initView(itemView, data);
        }

        private void initView(View itemView, List<ResultRecommendDataBean.Stadium> data) {
            mRlMore = itemView.findViewById(R.id.rl_venue_more);
            mRvVenue = itemView.findViewById(R.id.rv_home_venue);
            mRvVenue.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            if (mAdapter == null) {
                mAdapter = new HomeVenueAdapter(data);
                mAdapter.setHasStableIds(true);
                mRvVenue.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
            mRlMore.setOnClickListener(v -> EventBus.getDefault().post(new HomeFragment.Message(TYPE_VENUE, 2)));
        }
    }

    static class HomeVenueAdapter extends RecyclerView.Adapter<HomeVenueAdapter.ViewHolder> {
        private List<ResultRecommendDataBean.Stadium> mData;

        public HomeVenueAdapter(List<ResultRecommendDataBean.Stadium> data) {
            mData = new ArrayList<>();
            this.mData = data;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venues_home_child_view, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            ResultRecommendDataBean.Stadium bean = mData.get(i);
            GlideManger.get().loadDefaultImage(viewHolder.itemView.getContext(), bean.photoUrl, viewHolder.mRivVenue);
            viewHolder.mTvContent.setText(bean.stadiumName);
            viewHolder.mTvLong.setText("距离：" + bean.distance + "km");

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtils.dp2px(viewHolder.itemView.getContext(), 120),
                    ScreenUtils.dp2px(viewHolder.itemView.getContext(), 160));

            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 10);
            layoutParams.leftMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 0);
            if (i == 0) {
                layoutParams.leftMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
            } else if (i == mData.size() - 1) {
                layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
            }
            viewHolder.mCardItem.setLayoutParams(layoutParams);
            viewHolder.itemView.setOnClickListener(v -> {
                        //Toasts.with().showToast(R.string.pleases_next_open);
                        LogUtils.w("viewHolder--", bean.stadiumId + "--");
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_DETAILS, ARouterConfig.Key.STADIUM_ID, bean.stadiumId);

                    }
            );
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private RoundedImageView mRivVenue;
            private TextView mTvContent;
            private TextView mTvLong;
            private CardView mCardItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                initItemChildView(itemView);
            }

            private void initItemChildView(View itemView) {
                mRivVenue = itemView.findViewById(R.id.riv_venue);
                mTvContent = itemView.findViewById(R.id.tv_content);
                mTvLong = itemView.findViewById(R.id.tv_long);
                mCardItem = itemView.findViewById(R.id.cv_item);

            }
        }
    }

    static class HomeBallHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlMore;
        private RecyclerView mRvBallData;
        private HomeBallAdapter mHomeBallAdapter;
        private List<ResultRecommendDataBean.AgreeBallMatch> mAgreeBallMatchData;

        public HomeBallHolder(@NonNull View itemView, List<ResultRecommendDataBean.AgreeBallMatch> data, Fragment fragment) {
            super(itemView);
            initChildView(itemView);
            if (mAgreeBallMatchData == null) {
                mAgreeBallMatchData = new ArrayList<>();
            }
            mAgreeBallMatchData.clear();
            if (data != null) {
                mAgreeBallMatchData.addAll(data);
            }
            if (mHomeBallAdapter == null) {
                mHomeBallAdapter = new HomeBallAdapter(mAgreeBallMatchData, fragment);
                mRvBallData.setAdapter(mHomeBallAdapter);
            } else {
                mHomeBallAdapter.notifyDataSetChanged();
            }

            mRlMore.setOnClickListener(v -> EventBus.getDefault().post(new HomeFragment.Message(TYPE_BALL, 2)));
        }

        private void initChildView(View itemView) {
            mRlMore = itemView.findViewById(R.id.rl_ball_more);
            mRvBallData = itemView.findViewById(R.id.rv_ball_data);
            mRvBallData.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        }


    }

    static class HomeBallAdapter extends RecyclerView.Adapter<HomeBallAdapter.ViewHolder> {
        private List<ResultRecommendDataBean.AgreeBallMatch> mData;
        private Fragment mFragment;

        public HomeBallAdapter(List<ResultRecommendDataBean.AgreeBallMatch> data, Fragment fragment) {
            this.mData = data;
            mFragment = fragment;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_ball_child_view, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            ResultRecommendDataBean.AgreeBallMatch bean = mData.get(i);
            GlideManger.get().loadHeadImage(viewHolder.itemView.getContext(), bean.hostTeamLogo, viewHolder.mCivHead);
            viewHolder.mTvContent.setText(bean.stadiumName + "\n" + bean.areaName);
            viewHolder.mTvTime.setText(bean.startTime);
            viewHolder.mTvName.setText(bean.hostTeamName);
            CardView.LayoutParams layoutParams = new FrameLayout.LayoutParams(ScreenUtils.dp2px(viewHolder.itemView.getContext(), 190),
                    ScreenUtils.dp2px(viewHolder.itemView.getContext(), 135));
            layoutParams.leftMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 0);
            layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 10);
            if (i == 0) {
                layoutParams.leftMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
            } else if (i == mData.size() - 1) {
                layoutParams.rightMargin = ScreenUtils.dp2px(viewHolder.itemView.getContext(), 20);
            }
            viewHolder.itemView.setLayoutParams(layoutParams);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toasts.with().showToast(R.string.pleases_next_open);
                    Bundle bundle = new Bundle();
                    bundle.putLong(ARouterConfig.Key.OFFER_ID, bean.agreeId);
                    bundle.putInt(ARouterConfig.Key.ABOUT_BALL_FLAG, 0);
                    bundle.putInt(ARouterConfig.Key.REFEREE_STATUS, bean.hasReferee);
                    bundle.putInt(ARouterConfig.Key.TEAM_STATUS, bean.hasOpponent);
                    ARouterIntent.startActivityForResult(mFragment, AboutBallDetailsActivity.class, bundle, AboutBallDetailsActivity.REQUEST_CODE);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView mCivHead;
            private TextView mTvContent;
            private TextView mTvTime;
            private TextView mTvName;
            private CardView mCvItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                initChildView(itemView);
            }

            private void initChildView(View itemView) {
                mCivHead = itemView.findViewById(R.id.civ_include_head);
                mTvContent = itemView.findViewById(R.id.tv_include_content);
                mTvTime = itemView.findViewById(R.id.tv_include_time);
                mTvName = itemView.findViewById(R.id.tv_include_name);
                mCvItem = itemView.findViewById(R.id.cv_item);
            }
        }
    }


}
