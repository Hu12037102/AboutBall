package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityRecommendPagerAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityRecommendContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityRecommendPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:58
 * 更新时间: 2019/3/19 13:58
 * 描述:社区-推荐Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_RECOMMEND)
public class CommunityRecommendFragment extends DelayedFragment<CommunityRecommendPresenter>
        implements CommunityRecommendContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private CommunityDataAdapter mAdapter;
    private List<ResultCommunityDataBean> mData;
    private View mHeadView;
    private static final int WHAT = 100;
    private boolean mIsSendMessage;
    private Handler mPagerHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT:
                    mBvpContent.setCurrentItem(mBvpContent.getCurrentItem() + 1, true);
                    sendMessage();
                    break;
            }

            return true;
        }
    });
    private List<ResultRecommendHotBean> mHeadData;
    private CommunityRecommendPagerAdapter mHeadAdapter;
    private BaseViewPager mBvpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_recommend;
    }

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        initHeadView();
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_community_recommend_view, mRvData, false);
        mBvpContent = mHeadView.findViewById(R.id.bvp_content);
    }

    @Override
    protected void initDelayedData() {
        mHeadData = new ArrayList<>();


        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mPresenter.loadHeadData();
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.loadData();
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(false, refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(true, refreshLayout);
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onNotDataClick(View view) {
                mSrlRefresh.autoRefresh();
            }

            @Override
            public void onItemClick(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), CommunityRecommendFragment.this);
            }
        });
        mAdapter.setOnTextContentClickListener(new CommunityDataAdapter.OnTextContentClickListener() {
            @Override
            public void onClickContent(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), CommunityRecommendFragment.this);
            }

            @Override
            public void onClickTopic(View view, int position) {
            }

            @Override
            public void onClickReport(View view, int position) {

            }

            @Override
            public void onClickAttention(View view, int position) {
                ResultCommunityDataBean bean = mData.get(position);
                if (bean.hasFollow == 0) {
                    mPresenter.getAttentionTweet(position, mData.get(position).userId);
                } else if (bean.hasFollow == 1) {
                    mPresenter.getCancelAttentionTweet(position, mData.get(position).userId);
                }
            }

            @Override
            public void onClickDelete(View view, int position) {
                ResultCommunityDataBean bean = mData.get(position);
                mPresenter.deleteDynamics(bean.tweetId, position);
            }

            @Override
            public void onClickDianZan(View view, int position) {
                ResultCommunityDataBean bean = mData.get(position);
                if (bean.hasPraise == 1) {
                    mPresenter.dynamicsCancelDianZan(bean.tweetId, position);
                } else if (bean.hasPraise == 0) {
                    mPresenter.dynamicsDianZan(bean.tweetId, position);
                }
            }

            @Override
            public void onClickShare(View view, int position) {

            }
        });
    }

    @Override
    public void resultDianZanStatus(int position) {
        mViewModel.updateDianZan(mAdapter, mData, position);
    }

    @Override
    public void resultAttentionTweetStatus(int position) {
        mViewModel.updateAttention(mAdapter, mData, position);
    }

    @Override
    protected CommunityRecommendPresenter createPresenter() {
        return new CommunityRecommendPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsSendMessage) {
            this.sendMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIsSendMessage) {
            this.removeMessage();
        }
    }

    @Override
    public void resultDeleteDynamicSucceed(int position) {
        mData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override

    public void resultHeadData(List<ResultRecommendHotBean> data) {
        if (mHeadData.size() > 0) {
            mHeadData.clear();
        }
        mHeadData.addAll(data);
        if (mHeadAdapter == null) {
            mHeadAdapter = new CommunityRecommendPagerAdapter(mContext, mHeadData);
            mBvpContent.setPageMargin(ScreenUtils.dp2px(DataUtils.checkData(getContext()), 10));
            mBvpContent.setOffscreenPageLimit(3);
            mBvpContent.setAdapter(mHeadAdapter);
            this.sendMessage();
        } else {
            mHeadAdapter.notifyDataSetChanged();
        }
    }

    private void sendMessage() {
        if (mPagerHandler == null) {
            return;
        }
        mIsSendMessage = mPagerHandler.sendEmptyMessageDelayed(WHAT, 4000);
    }

    private void removeMessage() {
        if (mPagerHandler == null) {
            return;
        }
        mPagerHandler.removeMessages(WHAT, null);
    }

    @Override
    public void resultData(List<ResultCommunityDataBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    ResultCommunityDataBean bean = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    mViewModel.resultCommunityData(mAdapter, bean, mData);
                    break;
                default:
                    break;
            }
        }

    }
}
