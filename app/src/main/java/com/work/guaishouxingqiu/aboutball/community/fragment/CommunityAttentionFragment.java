package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.community.activity.DynamicEditActivity;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityAttentionContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityAttentionPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:45
 * 更新时间: 2019/3/19 13:45
 * 描述:社区-关注Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_ATTENTION)
public class CommunityAttentionFragment extends BaseFragment<CommunityAttentionPresenter> implements
        CommunityAttentionContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    private CommunityDataAdapter mAdapter;
    private List<ResultCommunityDataBean> mData;
    private static final int REQUEST_CODE_PUBLISH_DYNAMIC = 122;
    private static final int REQUEST_CODE_TOPIC = 321;

    public void setOnUpdateCommunity(OnUpdateCommunity onUpdateCommunity) {
        this.onUpdateCommunity = onUpdateCommunity;
    }

    private OnUpdateCommunity onUpdateCommunity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_attention;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData, false);
        mAdapter.setNotDataContentRes(R.string.not_follow_content);
        mRvData.setAdapter(mAdapter);
        mSrlLayout.autoRefresh();
    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        mPresenter.isRefresh = isRefresh;
        mPresenter.start();
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    public void autoRefresh(ResultCommunityDataBean bean) {
        mViewModel.resultCommunityData(mAdapter, bean, mData);
    }

    @Override
    protected void initEvent() {
        mSrlLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
                mSrlLayout.autoRefresh();
            }

            @Override
            public void onNotDataClick(View view) {
                mSrlLayout.autoRefresh();
            }

            @Override
            public void onItemClick(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), CommunityAttentionFragment.this);
            }
        });
        mAdapter.setOnTextContentClickListener(new CommunityDataAdapter.OnTextContentClickListener() {
            @Override
            public void onClickContent(View view, int position) {
                mViewModel.startActivityToCommunityRecommendDetailsForResult(mData.get(position), CommunityAttentionFragment.this);
            }

            @Override
            public void onClickTopic(View view, int position) {
                ResultCommunityDataBean bean = mData.get(position);
                if (bean != null) {
                    mViewModel.startActivityToTopicForResult(bean.topic, REQUEST_CODE_TOPIC, CommunityAttentionFragment.this);
                }
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
    public void resultDeleteDynamicSucceed(int position) {
        ResultCommunityDataBean bean = mData.get(position);
        bean.isDelete = true;
        onEventUpdate(bean);
        mData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected CommunityAttentionPresenter createPresenter() {
        return new CommunityAttentionPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CommunityAttentionFragment.REQUEST_CODE_PUBLISH_DYNAMIC:
                    mSrlLayout.autoRefresh();
                    break;
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    ResultCommunityDataBean bean = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    // boolean isDelete = data.getBooleanExtra(ARouterConfig.Key.DELETE, false);
                    mViewModel.resultCommunityData(mAdapter, bean, mData);
                    onEventUpdate(bean);
                    break;
                case REQUEST_CODE_TOPIC:
                    mSrlLayout.autoRefresh();
                default:
                    break;
            }
        }

    }

    private void onEventUpdate(ResultCommunityDataBean bean) {
        if (onUpdateCommunity != null) {
            onUpdateCommunity.updateNew(bean);
            onUpdateCommunity.updateRecommended(bean);
        }
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
    public void resultData(List<ResultCommunityDataBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mSrlLayout.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_add_community)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_community:
                ARouterIntent.startActivityForResult(this, DynamicEditActivity.class, CommunityAttentionFragment.REQUEST_CODE_PUBLISH_DYNAMIC);
                break;

            default:
                break;
        }
    }

    public interface OnUpdateCommunity {
        void updateRecommended(ResultCommunityDataBean bean);

        void updateNew(ResultCommunityDataBean bean);
    }
}
