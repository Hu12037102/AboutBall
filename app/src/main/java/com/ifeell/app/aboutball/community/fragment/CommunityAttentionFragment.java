package com.ifeell.app.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.commonality.fragment.LoginOrShareFragment;
import com.ifeell.app.aboutball.community.activity.DynamicEditActivity;
import com.ifeell.app.aboutball.community.adapter.CommunityDataAdapter;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.contract.CommunityAttentionContract;
import com.ifeell.app.aboutball.community.presenter.CommunityAttentionPresenter;
import com.ifeell.app.aboutball.my.bean.ResultAttentionFanBean;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:45
 * 更新时间: 2019/3/19 13:45
 * 描述:社区-关注Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_ATTENTION)
public class CommunityAttentionFragment extends LoginOrShareFragment<CommunityAttentionPresenter> implements
        CommunityAttentionContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    private CommunityDataAdapter mAdapter;
    private List<ResultCommunityDataBean> mData;
    private static final int REQUEST_CODE_PUBLISH_DYNAMIC = 122;
    private static final int REQUEST_CODE_TOPIC = 321;
    private int mSharePosition;
    private static final int REQUEST_CODE_USER_DYNAMIC = 92;

    public void setOnUpdateCommunity(OnUpdateCommunity onUpdateCommunity) {
        this.onUpdateCommunity = onUpdateCommunity;
    }

    private OnUpdateCommunity onUpdateCommunity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_attention;
    }

    @Override
    protected void initDelayedView() {

        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData, false);
        mAdapter.setNotDataContentRes(R.string.not_follow_content);
        mRvData.setAdapter(mAdapter);
        mSrlLayout.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
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
                DataUtils.addSellingPoint(mContext, SellingPointsEvent.Key.A0405);
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
                DataUtils.addSellingPoint(mContext, SellingPointsEvent.Key.A0408);
                ResultCommunityDataBean bean = mData.get(position);
                if (bean.hasPraise == 1) {
                    mPresenter.dynamicsCancelDianZan(bean.tweetId, position);
                } else if (bean.hasPraise == 0) {
                    mPresenter.dynamicsDianZan(bean.tweetId, position);
                }
            }

            @Override
            public void onClickShare(View view, int position) {
                mSharePosition = position;
                showShareDialog(mViewModel.getCommunityShare(mData.get(position)));
            }

            @Override
            public void onClickHead(View view, int position) {
                DataUtils.addSellingPoint(mContext, SellingPointsEvent.Key.A0404);
                mViewModel.startActivityToUserDynamicForResult(CommunityAttentionFragment.this, mData.get(position).userId, CommunityAttentionFragment.REQUEST_CODE_USER_DYNAMIC);
            }
        });
    }

    @Override
    protected void initView() {
        registerEventBus();
        /*mRvData.setLayoutManager(new LinearLayoutManager(getContext()));*/
    }

    @Override
    protected void initData() {
       /* mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData, false);
        mAdapter.setNotDataContentRes(R.string.not_follow_content);
        mRvData.setAdapter(mAdapter);
        mSrlLayout.autoRefresh();*/
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

    public void notifyData(ResultCommunityDataBean bean) {
        if (bean != null) {
            mViewModel.resultCommunityData(mAdapter, bean, mData, true);
        } else {
            autoRefresh();
        }
    }

    public void autoRefresh() {
        if (mSrlLayout != null) {
            mSrlLayout.autoRefresh();
        }

    }

    @Override
    protected void initEvent() {
       /* mSrlLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
                if (bean.isFollow == 0) {
                    mPresenter.getAttentionTweet(position, mData.get(position).userId);
                } else if (bean.isFollow == 1) {
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
                mSharePosition = position;
                showShareDialog(mViewModel.getCommunityShare(mData.get(position)));
            }
        });*/
    }

    @Override
    public void resultShareWeiChat() {
        super.resultShareWeiChat();
        mPresenter.shareCommunityDynamic(mData.get(mSharePosition).tweetId);
    }

    @Override
    public void resultShareCommunityDynamic() {
        mData.get(mSharePosition).shareCount += 1;
        onEventUpdate(mData.get(mSharePosition));
        mAdapter.notifyDataSetChanged();
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
                    mViewModel.resultCommunityData(mAdapter, bean, mData, true);
                    onEventUpdate(bean);
                    break;
                case REQUEST_CODE_TOPIC:
                    mSrlLayout.autoRefresh();
                    break;
                //点击头像跳转页面回调
                case CommunityAttentionFragment.REQUEST_CODE_USER_DYNAMIC:
                    mSrlLayout.autoRefresh();
                    onEventUpdate(null);
                    break;
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
                if (UserManger.get().isLogin()) {
                    ARouterIntent.startActivityForResult(this, DynamicEditActivity.class, CommunityAttentionFragment.REQUEST_CODE_PUBLISH_DYNAMIC);
                } else {
                    mViewModel.showLoginDialog();
                }
                break;

            default:
                break;
        }
    }

    public interface OnUpdateCommunity {
        void updateRecommended(ResultCommunityDataBean bean);

        void updateNew(ResultCommunityDataBean bean);
    }


    @Override
    public void onDestroyView() {
        unRegisterEventBus();
        super.onDestroyView();
    }

    /**
     * 我的粉丝和我的关注发送消息
     *
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attentionFansMessage(ResultAttentionFanBean message) {
        mViewModel.resultCommunityData(mAdapter, message, mData, true);
    }
}
