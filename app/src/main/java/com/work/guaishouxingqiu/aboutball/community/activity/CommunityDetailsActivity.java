package com.work.guaishouxingqiu.aboutball.community.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.activity.LoginOrShareActivity;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityDetailsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsMessageAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.InputMessageDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingPopupWindows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:32
 * 更新时间: 2019/6/13 18:32
 * 描述:动态详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_COMMUNITY_DETAILS)
public class CommunityDetailsActivity extends LoginOrShareActivity<CommunityDetailsPresenter> implements
        CommunityDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.iv_send_message)
    ImageView mIvSendMessage;
    @BindView(R.id.tv_input_message)
    TextView mTvInputMessage;
    private ResultCommunityDataBean mIntentBean;
    private NewsMessageAdapter mCommentAdapter;
    private List<ResultNewsMessageBean> mCommentData;
    private View mHeadInflateView;
    private InputMessageDialog mSendMessageDialog;
    private TextView mTvHeadLikeNum;
    private TextView mTvHeadAttention;
    private TextView mTvHeadCommentNum;
    private LinearLayout mLlHeadLike;
    private LinearLayout mLlHeadComment;
    private ImageView mIvHeadMore;
    private SingPopupWindows mDeleteWindows;
    private List<String> mPreviewData;
    private CircleImageView mCivHead;
    private static final int REQUEST_CODE_TOPIC = 155;
    private TextView mTvHeadShareNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_details;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.no_dynamic_details_were_obtained);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mIvSendMessage.setVisibility(View.GONE);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mPreviewData = new ArrayList<>();
        initHeadView();
    }

    private void initHeadView() {
        mHeadInflateView = LayoutInflater.from(this).inflate(R.layout.item_community_data_view, mRvData, false);
        mCivHead = mHeadInflateView.findViewById(R.id.civ_head);
        GlideManger.get().loadHeadImage(this, mIntentBean.headImg, mCivHead);
        TextView tvHeadName = mHeadInflateView.findViewById(R.id.tv_name);
        UIUtils.setText(tvHeadName, mIntentBean.nickName);
        TextView mTvHeadTime = mHeadInflateView.findViewById(R.id.tv_time);
        UIUtils.setText(mTvHeadTime, DateUtils.getYear2Minutes(mIntentBean.releaseTime));
        TextView mTvHeadContent = mHeadInflateView.findViewById(R.id.tv_data);
        mTvHeadContent.setMaxLines(Integer.MAX_VALUE);
        mTvHeadContent.setEllipsize(null);
        if (!DataUtils.isEmpty(mIntentBean.topicTitle)) {
            String content = mIntentBean.topicTitle.concat(mIntentBean.tweetContent);
            //SpanUtils.getTextColor(R.color.color_4,0,bean.topicTitle.length(),content);
            UIUtils.setText(mTvHeadContent, content);
            mTvHeadContent.setText(SpanUtils.getClickText(mTvHeadContent, content, R.color.color_2, 0, mIntentBean.topicTitle.length(), new SpanUtils.OnClickTextListener() {
                @Override
                public void onClick(View view) {
                    if (mIntentBean != null) {
                        mViewModel.startActivityToTopicForResult(mIntentBean.topic, REQUEST_CODE_TOPIC, null);
                    }
                }
            }));

        } else {
            UIUtils.setText(mTvHeadContent, mIntentBean.tweetContent);
        }
        mLlHeadLike = mHeadInflateView.findViewById(R.id.ll_like);

        mTvHeadLikeNum = mHeadInflateView.findViewById(R.id.tv_like_num);

        mLlHeadComment = mHeadInflateView.findViewById(R.id.ll_comment);

        mTvHeadCommentNum = mHeadInflateView.findViewById(R.id.tv_comment);
        UIUtils.setCommunityCount(mTvHeadCommentNum, mIntentBean.commentCount);
        LinearLayout mLlHeadShare = mHeadInflateView.findViewById(R.id.ll_share);
        mTvHeadShareNum = mHeadInflateView.findViewById(R.id.tv_share);
        UIUtils.setCommunityCount(mTvHeadShareNum, mIntentBean.shareCount);
        LinearLayout mLlHeadCommentBottom = mHeadInflateView.findViewById(R.id.ll_comment_bottom);
        mLlHeadCommentBottom.setVisibility(View.VISIBLE);
        View mLineHeadBottom = mHeadInflateView.findViewById(R.id.line_bottom);
        mLineHeadBottom.setVisibility(View.GONE);
        mIvHeadMore = mHeadInflateView.findViewById(R.id.iv_more);
        mTvHeadAttention = mHeadInflateView.findViewById(R.id.tv_attention);

        notifyLike();
        notifyFollow();
        mLlHeadShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog(mViewModel.getCommunityShare(mIntentBean));
            }
        });

        LinearLayout mGroupImageData = mHeadInflateView.findViewById(R.id.ll_iv_data);
        if (mGroupImageData.getChildCount() > 0) {
            mGroupImageData.removeAllViews();
        }
        if (mIntentBean.imageUrl != null) {
            String[] imagePathArray = mIntentBean.imageUrl.split(",");
            int imageCount = DataUtils.splitImagePathCount(mIntentBean.imageUrl);
            switch (imageCount) {
                case 0:
                    mGroupImageData.setVisibility(View.GONE);
                    break;
                case 1:
                    View oneImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_1, mGroupImageData, false);
                    RoundedImageView civ_1_1 = oneImageInflate.findViewById(R.id.riv_child_1);
                    ImageView ivVideo = oneImageInflate.findViewById(R.id.iv_video);
                    String path = imagePathArray[0];
                    ViewGroup.LayoutParams layoutParams = civ_1_1.getLayoutParams();
                    if (DataUtils.isVideo(path)) {
                        ivVideo.setVisibility(View.VISIBLE);
                        GlideManger.get().loadImageBitmap(path, new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (resource.getWidth() > resource.getHeight()) {
                                    layoutParams.width = ScreenUtils.dp2px(CommunityDetailsActivity.this, 176);
                                    layoutParams.height = ScreenUtils.dp2px(CommunityDetailsActivity.this, 114);
                                } else {
                                    layoutParams.width = ScreenUtils.dp2px(CommunityDetailsActivity.this, 176);
                                    layoutParams.height = ScreenUtils.dp2px(CommunityDetailsActivity.this, 225);
                                }
                                civ_1_1.setLayoutParams(layoutParams);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
                    } else {
                        ivVideo.setVisibility(View.GONE);
                        layoutParams.width = ScreenUtils.dp2px(CommunityDetailsActivity.this, 176);
                        layoutParams.height = ScreenUtils.dp2px(CommunityDetailsActivity.this, 176);
                        civ_1_1.setLayoutParams(layoutParams);
                    }

                    civ_1_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_1_1);
                    mGroupImageData.addView(oneImageInflate);
                    break;
                case 2:
                    View twoImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_2, mGroupImageData, false);
                    RoundedImageView civ_2_1 = twoImageInflate.findViewById(R.id.riv_child_1);
                    civ_2_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_2_2 = twoImageInflate.findViewById(R.id.riv_child_2);
                    civ_2_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_2_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_2_2);
                    mGroupImageData.addView(twoImageInflate);
                    break;
                case 3:
                    View threeImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_3, mGroupImageData, false);
                    RoundedImageView civ_3_1 = threeImageInflate.findViewById(R.id.riv_child_1);
                    civ_3_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_3_2 = threeImageInflate.findViewById(R.id.riv_child_2);
                    civ_3_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_3_3 = threeImageInflate.findViewById(R.id.riv_child_3);
                    civ_3_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_3_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_3_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_3_3);
                    mGroupImageData.addView(threeImageInflate);
                    break;
                case 4:
                    View fourImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_4, mGroupImageData, false);
                    RoundedImageView civ_4_1 = fourImageInflate.findViewById(R.id.riv_child_1);
                    civ_4_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_4_2 = fourImageInflate.findViewById(R.id.riv_child_2);
                    civ_4_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_4_3 = fourImageInflate.findViewById(R.id.riv_child_3);
                    civ_4_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_4_4 = fourImageInflate.findViewById(R.id.riv_child_4);
                    civ_4_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_4_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_4_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_4_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_4_4);
                    mGroupImageData.addView(fourImageInflate);
                    break;
                case 5:
                    View fiveImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_5, mGroupImageData, false);
                    RoundedImageView civ_5_1 = fiveImageInflate.findViewById(R.id.riv_child_1);
                    civ_5_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_5_2 = fiveImageInflate.findViewById(R.id.riv_child_2);
                    civ_5_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_5_3 = fiveImageInflate.findViewById(R.id.riv_child_3);
                    civ_5_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_5_4 = fiveImageInflate.findViewById(R.id.riv_child_4);
                    civ_5_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    RoundedImageView civ_5_5 = fiveImageInflate.findViewById(R.id.riv_child_5);
                    civ_5_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(4);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_5_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_5_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_5_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_5_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[4], civ_5_5);
                    mGroupImageData.addView(fiveImageInflate);
                    break;
                case 6:
                    View sixImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_6, mGroupImageData, false);
                    RoundedImageView civ_6_1 = sixImageInflate.findViewById(R.id.riv_child_1);
                    civ_6_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_6_2 = sixImageInflate.findViewById(R.id.riv_child_2);
                    civ_6_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_6_3 = sixImageInflate.findViewById(R.id.riv_child_3);
                    civ_6_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_6_4 = sixImageInflate.findViewById(R.id.riv_child_4);
                    civ_6_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    RoundedImageView civ_6_5 = sixImageInflate.findViewById(R.id.riv_child_5);
                    civ_6_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(4);
                        }
                    });
                    RoundedImageView civ_6_6 = sixImageInflate.findViewById(R.id.riv_child_6);
                    civ_6_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(5);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_6_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_6_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_6_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_6_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[4], civ_6_5);
                    GlideManger.get().loadBannerImage(this, imagePathArray[5], civ_6_6);
                    mGroupImageData.addView(sixImageInflate);
                    break;
                case 7:
                    View sevenImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_7, mGroupImageData, false);
                    RoundedImageView civ_7_1 = sevenImageInflate.findViewById(R.id.riv_child_1);
                    civ_7_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_7_2 = sevenImageInflate.findViewById(R.id.riv_child_2);
                    civ_7_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_7_3 = sevenImageInflate.findViewById(R.id.riv_child_3);
                    civ_7_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_7_4 = sevenImageInflate.findViewById(R.id.riv_child_4);
                    civ_7_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    RoundedImageView civ_7_5 = sevenImageInflate.findViewById(R.id.riv_child_5);
                    civ_7_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(4);
                        }
                    });
                    RoundedImageView civ_7_6 = sevenImageInflate.findViewById(R.id.riv_child_6);
                    civ_7_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(5);
                        }
                    });
                    RoundedImageView civ_7_7 = sevenImageInflate.findViewById(R.id.riv_child_7);
                    civ_7_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(6);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_7_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_7_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_7_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_7_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[4], civ_7_5);
                    GlideManger.get().loadBannerImage(this, imagePathArray[5], civ_7_6);
                    GlideManger.get().loadBannerImage(this, imagePathArray[6], civ_7_7);
                    mGroupImageData.addView(sevenImageInflate);
                    break;
                case 8:
                    View eightImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_8, mGroupImageData, false);
                    RoundedImageView civ_8_1 = eightImageInflate.findViewById(R.id.riv_child_1);
                    civ_8_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_8_2 = eightImageInflate.findViewById(R.id.riv_child_2);
                    civ_8_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_8_3 = eightImageInflate.findViewById(R.id.riv_child_3);
                    civ_8_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_8_4 = eightImageInflate.findViewById(R.id.riv_child_4);
                    civ_8_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    RoundedImageView civ_8_5 = eightImageInflate.findViewById(R.id.riv_child_5);
                    civ_8_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(4);
                        }
                    });
                    RoundedImageView civ_8_6 = eightImageInflate.findViewById(R.id.riv_child_6);
                    civ_8_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(5);
                        }
                    });
                    RoundedImageView civ_8_7 = eightImageInflate.findViewById(R.id.riv_child_7);
                    civ_8_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(6);
                        }
                    });
                    RoundedImageView civ_8_8 = eightImageInflate.findViewById(R.id.riv_child_8);
                    civ_8_8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(7);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_8_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_8_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_8_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_8_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[4], civ_8_5);
                    GlideManger.get().loadBannerImage(this, imagePathArray[5], civ_8_6);
                    GlideManger.get().loadBannerImage(this, imagePathArray[6], civ_8_7);
                    GlideManger.get().loadBannerImage(this, imagePathArray[7], civ_8_8);
                    mGroupImageData.addView(eightImageInflate);
                    break;
                case 9:
                    View nineImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_9, mGroupImageData, false);
                    RoundedImageView civ_9_1 = nineImageInflate.findViewById(R.id.riv_child_1);
                    civ_9_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(0);
                        }
                    });
                    RoundedImageView civ_9_2 = nineImageInflate.findViewById(R.id.riv_child_2);
                    civ_9_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(1);
                        }
                    });
                    RoundedImageView civ_9_3 = nineImageInflate.findViewById(R.id.riv_child_3);
                    civ_9_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(2);
                        }
                    });
                    RoundedImageView civ_9_4 = nineImageInflate.findViewById(R.id.riv_child_4);
                    civ_9_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(3);
                        }
                    });
                    RoundedImageView civ_9_5 = nineImageInflate.findViewById(R.id.riv_child_5);
                    civ_9_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(4);
                        }
                    });
                    RoundedImageView civ_9_6 = nineImageInflate.findViewById(R.id.riv_child_6);
                    civ_9_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(5);
                        }
                    });
                    RoundedImageView civ_9_7 = nineImageInflate.findViewById(R.id.riv_child_7);
                    civ_9_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(6);
                        }
                    });
                    RoundedImageView civ_9_8 = nineImageInflate.findViewById(R.id.riv_child_8);
                    civ_9_8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(7);
                        }
                    });
                    RoundedImageView civ_9_9 = nineImageInflate.findViewById(R.id.riv_child_9);
                    civ_9_9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPreview(8);
                        }
                    });
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_9_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_9_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_9_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_9_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[4], civ_9_5);
                    GlideManger.get().loadBannerImage(this, imagePathArray[5], civ_9_6);
                    GlideManger.get().loadBannerImage(this, imagePathArray[6], civ_9_7);
                    GlideManger.get().loadBannerImage(this, imagePathArray[7], civ_9_8);
                    GlideManger.get().loadBannerImage(this, imagePathArray[8], civ_9_9);
                    mGroupImageData.addView(nineImageInflate);
                    break;
                default:
                    break;
            }
        }
    }

    private void notifyLike() {
        UIUtils.setCommunityCount(mTvHeadLikeNum, mIntentBean.praiseCount);
        if (mIntentBean.hasPraise == 1) {
            mTvHeadLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like, 0, 0, 0);
            mTvHeadLikeNum.setTextColor(ContextCompat.getColor(this,R.color.color_2));
        } else {
            mTvHeadLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_no_like, 0, 0, 0);
            mTvHeadLikeNum.setTextColor(ContextCompat.getColor(this,R.color.color_3));
        }
    }

    private void startPreview(int imagePosition) {
        mPreviewData.clear();
        String[] imagePathArray = mIntentBean.imageUrl.split(",");
        mPreviewData.addAll(Arrays.asList(imagePathArray));
        mViewModel.startActivityToPreview(imagePosition, (ArrayList<String>) mPreviewData);
    }

    private void notifyFollow() {
        mTvHeadAttention.setVisibility(View.VISIBLE);
        if (mIntentBean.hasFollow == 1) {
            mTvHeadAttention.setText(R.string.attentionning);
        } else {
            mTvHeadAttention.setText(R.string.attention);
            if (mIntentBean.myTweet == 1) {
                mTvHeadAttention.setVisibility(View.GONE);
            } else {
                mTvHeadAttention.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void initData() {
        mCommentData = new ArrayList<>();
        mCommentAdapter = new NewsMessageAdapter(mCommentData);
        mCommentAdapter.addHeadView(mHeadInflateView);
        mCommentAdapter.setNotDataView(R.mipmap.icon_not_data_message);
        mCommentAdapter.setNotDataContentRes(R.string.not_message);
        mRvData.setAdapter(mCommentAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void resultDianZanStatus(int position) {
        if (mIntentBean.hasPraise == 1) {
            mIntentBean.hasPraise = 0;
            mIntentBean.praiseCount = mIntentBean.praiseCount > 0 ? mIntentBean.praiseCount -= 1 : 0;
        } else if (mIntentBean.hasPraise == 0) {
            mIntentBean.hasPraise = 1;
            mIntentBean.praiseCount++;
        }
        notifyLike();
    }


    @Override
    public void resultAttentionTweetStatus(int position) {
        if (mIntentBean.hasFollow == 1) {
            mIntentBean.hasFollow = 0;
        } else if (mIntentBean.hasFollow == 0) {
            mIntentBean.hasFollow = 1;
        }
        notifyFollow();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                onBack();
            }
        });
        //点赞
        mLlHeadLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIntentBean.hasPraise == 1) {
                    mPresenter.dynamicsCancelDianZan(mIntentBean.tweetId, -1);
                } else if (mIntentBean.hasPraise == 0) {
                    mPresenter.dynamicsDianZan(mIntentBean.tweetId, -1);
                }

            }
        });
        //评论
        mLlHeadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSendMessageDialog();
            }
        });
        //关注
        mTvHeadAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIntentBean.hasFollow == 0) {
                    mPresenter.getAttentionTweet(-1, mIntentBean.userId);
                } else if (mIntentBean.hasFollow == 1) {
                    mPresenter.getCancelAttentionTweet(-1, mIntentBean.userId);
                }
            }
        });
        //更多
        mIvHeadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteWindows == null) {
                    mDeleteWindows = new SingPopupWindows(CommunityDetailsActivity.this);
                }
                if (mIntentBean.myTweet == 1) {
                    mDeleteWindows.setContent(R.string.delete);
                    mDeleteWindows.setContentDrawableRes(R.mipmap.icon_delete, 0, 0, 0);
                } else {
                    mDeleteWindows.setContent(R.string.report);
                    mDeleteWindows.setContentDrawableRes(R.mipmap.icon_report, 0, 0, 0);
                }
                mDeleteWindows.setPopupWindowsItemClickListener(view -> {
                    if (mIntentBean.myTweet == 1) {
                        mViewModel.showDeleteCommunityDialog(new BaseDialog.OnItemClickSureAndCancelListener() {
                            @Override
                            public void onClickSure(@NonNull View view) {
                                mPresenter.deleteDynamics(mIntentBean.tweetId, -1);
                            }

                            @Override
                            public void onClickCancel(@NonNull View view) {
                            }
                        });
                    } else {
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_COMMUNITY_REPORT, ARouterConfig.Key.TWEET_ID, mIntentBean.tweetId);
                    }

                    mDeleteWindows.dismiss();
                });
                if (mDeleteWindows != null && !mDeleteWindows.isShowing()) {
                    mDeleteWindows.showAsDropDown(v, -(mDeleteWindows.getWindow().getWidth() - ScreenUtils.dp2px(CommunityDetailsActivity.this, 40)), 0);
                }
            }
        });
        mCivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.startActivityToPreview(0, DataUtils.getOnePreviewData(mIntentBean.headImg));
            }
        });
    }

    @Override
    public void resultShareWeiChat() {
        super.resultShareWeiChat();
        mPresenter.shareCommunityDynamic(mIntentBean.tweetId);
    }

    @Override
    public void resultShareCommunityDynamic() {
        mIntentBean.shareCount += 1;
        UIUtils.setCommunityCount(mTvHeadShareNum, mIntentBean.shareCount);
    }

    private void onBack() {
        Intent intent = new Intent();
        intent.putExtra(ARouterConfig.Key.PARCELABLE, mIntentBean);
        // intent.putExtra(ARouterConfig.Key.DELETE, isDelete);
        mViewModel.clickBackForResult(intent);
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    public void resultDeleteDynamicSucceed(int position) {
        mIntentBean.isDelete = true;
        onBack();
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mPresenter.mPageNum = Contast.DEFAULT_PAGE_NUM;
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        mPresenter.loadCommentData(mIntentBean.tweetId);
    }

    @Override
    protected CommunityDetailsPresenter createPresenter() {
        return new CommunityDetailsPresenter(this);
    }


    @OnClick({R.id.iv_send_message, R.id.tv_input_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_send_message:
                break;
            case R.id.tv_input_message:
                showSendMessageDialog();

                break;
        }
    }

    private void showSendMessageDialog() {
        if (mSendMessageDialog == null) {
            mSendMessageDialog = new InputMessageDialog(this);
            mSendMessageDialog.setOnInputMessageListener(text -> {
                RequestDynamicCommentsBean bean = new RequestDynamicCommentsBean();
                bean.tweetId = mIntentBean.tweetId;
                bean.commentContent = text;
                mPresenter.postDynamicComments(bean);
            });
        }
        if (!mSendMessageDialog.isShowing()) {
            mSendMessageDialog.show();
        }
    }

    @Override
    public void resultCommentData(List<ResultNewsMessageBean> data) {
        if (mPresenter.isRefresh && mCommentData.size() > 0) {
            mCommentData.clear();
        }
        if (data.size() > 0) {
            mCommentData.addAll(data);
        }
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mCommentAdapter.notifyData(NetWorkUtils.isNetCanUse());
    }

    @Override
    public void resultDynamicCommentsSucceed() {
        if (mSendMessageDialog != null) {
            mSendMessageDialog.clearEditData();
        }
        mIntentBean.commentCount++;
        UIUtils.setCommunityCount(mTvHeadCommentNum, mIntentBean.commentCount);
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TOPIC:
                    mSrlRefresh.autoRefresh();
                default:
                    break;
            }
        }
    }
}
