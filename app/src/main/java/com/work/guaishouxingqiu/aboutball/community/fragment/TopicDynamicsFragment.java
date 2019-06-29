package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.commonality.fragment.LoginOrShareFragment;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.TopicDynamicsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.TopicDynamicsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/20 14:12
 * 更新时间: 2019/6/20 14:12
 * 描述:话题动态fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_TOPIC_DYNAMICS)
public class TopicDynamicsFragment extends LoginOrShareFragment<TopicDynamicsPresenter> implements TopicDynamicsContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mTopicStatus;
    private long mTopicId;
    private List<ResultCommunityDataBean> mData;
    private CommunityDataAdapter mAdapter;
    private int mSharePosition;
    private boolean mIsShare;

    public void setOnUpdateDataChangListener(OnUpdateDataChangListener onUpdateDataChangListener) {
        this.onUpdateDataChangListener = onUpdateDataChangListener;
    }

    private OnUpdateDataChangListener onUpdateDataChangListener;

    @Override
    protected void initPermission() {
        mTopicStatus = mBundle.getInt(ARouterConfig.Key.TOPIC_STATUS, -1);
        mTopicId = mBundle.getLong(ARouterConfig.Key.TOPIC_ID, -1);
        super.initPermission();
    }

    public void refreshData() {
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_dynamics;
    }

    @Override
    protected void initDelayedView() {
        if (mTopicStatus == Contast.Topic.NEW) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    public void autoRefresh(ResultCommunityDataBean bean) {
        mViewModel.resultCommunityData(mAdapter, bean, mData);
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mTopicStatus == Contast.Topic.RECOMMENDED) {
            mSrlRefresh.autoRefresh();
        }
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        if (mTopicStatus == Contast.Topic.RECOMMENDED) {
            mPresenter.loadRecommendedTopic(mTopicId);
        } else if (mTopicStatus == Contast.Topic.NEW) {
            mPresenter.loadNewTopic(mTopicId);
        }
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
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), TopicDynamicsFragment.this);
            }
        });
        mAdapter.setOnTextContentClickListener(new CommunityDataAdapter.OnTextContentClickListener() {
            @Override
            public void onClickContent(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), TopicDynamicsFragment.this);
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
                mIsShare = true;
                mSharePosition = position;
                showShareDialog(mViewModel.getCommunityShare(mData.get(position)));
                LogUtils.w("onClickShare--", "我点击了--");
            }
        });
    }

    @Override
    public void resultShareWeiChat() {
        super.resultShareWeiChat();
        if (mIsShare) {
            mPresenter.shareCommunityDynamic(mData.get(mSharePosition).tweetId);
        }
    }

    @Override
    public void resultShareCommunityDynamic() {
        mData.get(mSharePosition).shareCount += 1;
        onEventUpdate(mData.get(mSharePosition));
        mAdapter.notifyDataSetChanged();
        mIsShare = false;
    }

    @Override
    public void resultDeleteDynamicSucceed(int position) {
        ResultCommunityDataBean bean = mData.get(position);
        bean.isDelete = true;
        onEventUpdate(bean);
        mData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultDianZanStatus(int position) {
        mViewModel.updateDianZan(mAdapter, mData, position);
        onEventUpdate(mData.get(position));
    }

    @Override
    public void resultAttentionTweetStatus(int position) {
        mViewModel.updateAttention(mAdapter, mData, position);
        onEventUpdate(mData.get(position));
    }

    @Override
    protected TopicDynamicsPresenter createPresenter() {
        return new TopicDynamicsPresenter(this);
    }


    @Override
    public void resultTopicData(List<ResultCommunityDataBean> data) {
        if (mPresenter.isRefresh && mData.size() > 0) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
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
                    onEventUpdate(bean);
                    break;
                default:
                    break;
            }
        }
    }

    private void onEventUpdate(ResultCommunityDataBean bean) {
        if (onUpdateDataChangListener != null) {
            onUpdateDataChangListener.onDataChang(bean);
        }
    }

    public interface OnUpdateDataChangListener {
        void onDataChang(ResultCommunityDataBean bean);
    }
}
