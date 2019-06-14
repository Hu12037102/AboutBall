package com.work.guaishouxingqiu.aboutball.community.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityDetailsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.home.adapter.NewsMessageAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:32
 * 更新时间: 2019/6/13 18:32
 * 描述:动态详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_COMMUNITY_DETAILS)
public class CommunityDetailsActivity extends BaseActivity<CommunityDetailsPresenter> implements
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
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        initHeadView();
    }

    private void initHeadView() {
        mHeadInflateView = LayoutInflater.from(this).inflate(R.layout.item_community_data_view, mRvData, false);
        CircleImageView civHead = mHeadInflateView.findViewById(R.id.civ_head);
        GlideManger.get().loadHeadImage(this, mIntentBean.headImg, civHead);
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
                    mTvHeadContent.setClickable(false);

                }
            }));

        } else {
            UIUtils.setText(mTvHeadContent, mIntentBean.tweetContent);
        }
        LinearLayout mLlHeadLike = mHeadInflateView.findViewById(R.id.ll_like);
        TextView mTvHeadLikeNum = mHeadInflateView.findViewById(R.id.tv_like_num);
        UIUtils.setCommunityCount(mTvHeadLikeNum, mIntentBean.praiseCount);
        LinearLayout mLlHeadComment = mHeadInflateView.findViewById(R.id.ll_comment);
        TextView mTvHeadCommentNum = mHeadInflateView.findViewById(R.id.tv_comment);
        UIUtils.setCommunityCount(mTvHeadCommentNum, mIntentBean.commentCount);
        LinearLayout mLlHeadShare = mHeadInflateView.findViewById(R.id.ll_share);
        TextView mTvHeadShareNum = mHeadInflateView.findViewById(R.id.tv_share);
        UIUtils.setCommunityCount(mTvHeadShareNum, mIntentBean.shareCount);
        LinearLayout mLlHeadCommentBottom = mHeadInflateView.findViewById(R.id.ll_comment_bottom);
        mLlHeadCommentBottom.setVisibility(View.VISIBLE);
        View mLineHeadBottom = mHeadInflateView.findViewById(R.id.line_bottom);
        mLineHeadBottom.setVisibility(View.GONE);
        ImageView mIvHeadMore = mHeadInflateView.findViewById(R.id.iv_more);
        TextView mTvHeadAttention = mHeadInflateView.findViewById(R.id.tv_attention);
        if (mIntentBean.hasPraise == 1) {
            mTvHeadLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_like, 0, 0, 0);
        } else {
            mTvHeadLikeNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_no_like, 0, 0, 0);
        }
        if (mIntentBean.hasFollow == 1) {
            mTvHeadAttention.setVisibility(View.GONE);
        } else {
            mTvHeadAttention.setVisibility(View.VISIBLE);
        }

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
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_1_1);
                    mGroupImageData.addView(oneImageInflate);
                    break;
                case 2:
                    View twoImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_2, mGroupImageData, false);
                    RoundedImageView civ_2_1 = twoImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_2_2 = twoImageInflate.findViewById(R.id.riv_child_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_2_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_2_2);
                    mGroupImageData.addView(twoImageInflate);
                    break;
                case 3:
                    View threeImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_3, mGroupImageData, false);
                    RoundedImageView civ_3_1 = threeImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_3_2 = threeImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_3_3 = threeImageInflate.findViewById(R.id.riv_child_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_3_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_3_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_3_3);
                    mGroupImageData.addView(threeImageInflate);
                    break;
                case 4:
                    View fourImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_4, mGroupImageData, false);
                    RoundedImageView civ_4_1 = fourImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_4_2 = fourImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_4_3 = fourImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_4_4 = fourImageInflate.findViewById(R.id.riv_child_4);
                    GlideManger.get().loadBannerImage(this, imagePathArray[0], civ_4_1);
                    GlideManger.get().loadBannerImage(this, imagePathArray[1], civ_4_2);
                    GlideManger.get().loadBannerImage(this, imagePathArray[2], civ_4_3);
                    GlideManger.get().loadBannerImage(this, imagePathArray[3], civ_4_4);
                    mGroupImageData.addView(fourImageInflate);
                    break;
                case 5:
                    View fiveImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_5, mGroupImageData, false);
                    RoundedImageView civ_5_1 = fiveImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_5_2 = fiveImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_5_3 = fiveImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_5_4 = fiveImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_5_5 = fiveImageInflate.findViewById(R.id.riv_child_5);
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
                    RoundedImageView civ_6_2 = sixImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_6_3 = sixImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_6_4 = sixImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_6_5 = sixImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_6_6 = sixImageInflate.findViewById(R.id.riv_child_6);
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
                    RoundedImageView civ_7_2 = sevenImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_7_3 = sevenImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_7_4 = sevenImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_7_5 = sevenImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_7_6 = sevenImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_7_7 = sevenImageInflate.findViewById(R.id.riv_child_7);
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
                    RoundedImageView civ_8_2 = eightImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_8_3 = eightImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_8_4 = eightImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_8_5 = eightImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_8_6 = eightImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_8_7 = eightImageInflate.findViewById(R.id.riv_child_7);
                    RoundedImageView civ_8_8 = eightImageInflate.findViewById(R.id.riv_child_8);
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
                    View nineImageInflate = LayoutInflater.from(this).inflate(R.layout.item_community_child_image_8, mGroupImageData, false);
                    RoundedImageView civ_9_1 = nineImageInflate.findViewById(R.id.riv_child_1);
                    RoundedImageView civ_9_2 = nineImageInflate.findViewById(R.id.riv_child_2);
                    RoundedImageView civ_9_3 = nineImageInflate.findViewById(R.id.riv_child_3);
                    RoundedImageView civ_9_4 = nineImageInflate.findViewById(R.id.riv_child_4);
                    RoundedImageView civ_9_5 = nineImageInflate.findViewById(R.id.riv_child_5);
                    RoundedImageView civ_9_6 = nineImageInflate.findViewById(R.id.riv_child_6);
                    RoundedImageView civ_9_7 = nineImageInflate.findViewById(R.id.riv_child_7);
                    RoundedImageView civ_9_8 = nineImageInflate.findViewById(R.id.riv_child_8);
                    RoundedImageView civ_9_9 = nineImageInflate.findViewById(R.id.riv_child_9);
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

    @Override
    protected void initData() {
        mCommentData = new ArrayList<>();
        mCommentAdapter = new NewsMessageAdapter(mCommentData);
        mCommentAdapter.addHeadView(mHeadInflateView);
        mRvData.setAdapter(mCommentAdapter);
        mSrlRefresh.autoRefresh();
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
                break;
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
}
