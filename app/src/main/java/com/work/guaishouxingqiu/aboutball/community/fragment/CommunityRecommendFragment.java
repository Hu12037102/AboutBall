package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.fragment.LoginOrShareFragment;
import com.work.guaishouxingqiu.aboutball.community.activity.DynamicEditActivity;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityRecommendPagerAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityRecommendContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityRecommendPresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultAttentionFanBean;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:58
 * 更新时间: 2019/3/19 13:58
 * 描述:社区-推荐Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_RECOMMEND)
public class CommunityRecommendFragment extends LoginOrShareFragment<CommunityRecommendPresenter>
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
    private int mSharePosition;
    private static final int REQUEST_CODE_PUBLISH_DYNAMIC = 178;
    private static final int REQUEST_CODE_USER_DYNAMIC = 92;

    public void setOnUpdateCommunity(OnUpdateCommunity onUpdateCommunity) {
        this.onUpdateCommunity = onUpdateCommunity;
    }

    private OnUpdateCommunity onUpdateCommunity;

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
    private static final int REQUEST_CODE_TOPIC = 321;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_recommend;
    }

    @Override
    protected void initDelayedView() {
        /*mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        initHeadView();*/
    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_community_recommend_view, mRvData, false);
        mBvpContent = mHeadView.findViewById(R.id.bvp_content);
    }

    @Override
    protected void initView() {
        super.initView();
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        initHeadView();
    }

    @Override
    protected void initData() {
        super.initData();
        mHeadData = new ArrayList<>();
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
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
                ResultCommunityDataBean bean = mData.get(position);
                if (bean != null) {
                    mViewModel.startActivityToTopicForResult(bean.topic, REQUEST_CODE_TOPIC, CommunityRecommendFragment.this);
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
                mSharePosition = position;
                showShareDialog(mViewModel.getCommunityShare(mData.get(position)));
            }

            @Override
            public void onClickHead(View view, int position) {
                mViewModel.startActivityToUserDynamicForResult(CommunityRecommendFragment.this,mData.get(position).userId,CommunityRecommendFragment.REQUEST_CODE_USER_DYNAMIC);
            }
        });
    }

    @Override
    protected void initDelayedData() {
        /*mHeadData = new ArrayList<>();
        mData = new ArrayList<>();
        mAdapter = new CommunityDataAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();*/
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

    public void notifyData(ResultCommunityDataBean bean) {
        if (bean != null) {
            mViewModel.resultCommunityData(mAdapter, bean, mData);
        } else {
            mSrlRefresh.autoRefresh();
        }

    }

    public void autoRefresh() {
        if (mSrlRefresh != null) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedEvent() {
        /*mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
                ResultCommunityDataBean bean = mData.get(position);
                if (bean != null) {
                    mViewModel.startActivityToTopicForResult(bean.topic, REQUEST_CODE_TOPIC, CommunityRecommendFragment.this);
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
        if (mHeadData.size() == 0) {
            mHeadView.setVisibility(View.GONE);
        } else {
            mHeadView.setVisibility(View.VISIBLE);
        }
        mHeadAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mViewModel.startActivityToTopicForResult(mHeadData.get(position), REQUEST_CODE_TOPIC, CommunityRecommendFragment.this);
            }
        });
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
                    //  boolean isDelete = data.getBooleanExtra(ARouterConfig.Key.DELETE, false);
                    mViewModel.resultCommunityData(mAdapter, bean, mData);
                    onEventUpdate(bean);
                    break;
                case REQUEST_CODE_TOPIC:
                case CommunityRecommendFragment.REQUEST_CODE_PUBLISH_DYNAMIC:
                    mSrlRefresh.autoRefresh();
                    break;
                //点击头像跳转页面回调
                case CommunityRecommendFragment.REQUEST_CODE_USER_DYNAMIC:
                    mSrlRefresh.autoRefresh();
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
            onUpdateCommunity.updateAttention(bean);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.iv_add_community)
    public void onViewClicked() {
        if (UserManger.get().isLogin()) {
            ARouterIntent.startActivityForResult(this, DynamicEditActivity.class, CommunityRecommendFragment.REQUEST_CODE_PUBLISH_DYNAMIC);
        } else {
            mViewModel.showLoginDialog();
        }
    }

    public interface OnUpdateCommunity {
        void updateAttention(ResultCommunityDataBean bean);

        void updateNew(ResultCommunityDataBean bean);
    }
    /**
     * 我的粉丝和我的关注发送消息
     *
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attentionFansMessage(ResultAttentionFanBean message) {
        mViewModel.updateAttention(mAdapter,mData,message);
      //  mViewModel.resultCommunityData(mAdapter, message, mData, true);
    }
}
