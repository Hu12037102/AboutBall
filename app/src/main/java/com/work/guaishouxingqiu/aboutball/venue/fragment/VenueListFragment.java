package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.venue.adapter.BallTypeAdapter;
import com.work.guaishouxingqiu.aboutball.venue.adapter.VenueListAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestVenueListBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueListContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:44
 * 更新时间: 2019/3/18 10:44
 * 描述: 场馆列表Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VENUE_LIST)
public class VenueListFragment extends BaseFragment<VenueListPresenter> implements VenueListContract.View {
    @BindView(R.id.rv_ball_type)
    RecyclerView mRvBallType;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<ResultTypeBean> mBallTypeData;
    private BallTypeAdapter mBallTypeAdapter;
    private RequestVenueListBean mRequestBean;
    private List<ResultVenueData> mListData;
    private VenueListAdapter mListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_venue_list;
    }

    @Override
    protected void initView() {
        mRvBallType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        mPresenter.start();
        mBallTypeData = new ArrayList<>();
        mBallTypeAdapter = new BallTypeAdapter(mBallTypeData);
        mRvBallType.setAdapter(mBallTypeAdapter);


    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        if (mRequestBean != null) {
            mPresenter.isRefresh = isRefresh;
            mPresenter.loadVenueList(mRequestBean);
            if (isRefresh) {
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    protected void initEvent() {
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
        mBallTypeAdapter.setOnItemClickListener((view, position) -> {
            RecyclerView.ViewHolder viewHolder = mRvBallType.getChildViewHolder(view);
            //移动到对应的position
            mRvBallType.smoothScrollToPosition(position);
            if (position >= mRvBallType.getChildCount() - 2) {
                mRvBallType.smoothScrollBy(viewHolder.itemView.getMeasuredWidth(), 0, new LinearInterpolator());
            } else if (position <= 1) {
                mRvBallType.smoothScrollBy(-viewHolder.itemView.getMeasuredWidth(), 0, new LinearInterpolator());
            }

            if (mRequestBean != null) {
                mRequestBean.typeId = mBallTypeData.get(position).typeId;
                if (mSrlRefresh.isRefreshing()) {
                    mPresenter.isRefresh = true;
                    mPresenter.loadVenueList(mRequestBean);
                } else {
                    mSrlRefresh.autoRefresh();
                }

            }
        });
    }

    @Override
    protected VenueListPresenter createPresenter() {
        return new VenueListPresenter(this);
    }

    @Override
    public void resultBallTypeList(List<ResultTypeBean> data) {
        if (data.size() > 0) {
            mBallTypeData.addAll(data);
            ResultTypeBean resultTypeBean = mBallTypeData.get(0);
            resultTypeBean.isCheck = true;
            mBallTypeAdapter.notifyDataSetChanged();

            mListData = new ArrayList<>();
            mListAdapter = new VenueListAdapter(mListData);
            mRvData.setAdapter(mListAdapter);

            mRequestBean = new RequestVenueListBean();
            mRequestBean.typeId = data.get(0).typeId;
            mSrlRefresh.autoRefresh();

        }
    }

    @Override
    public void resultVenueList(List<ResultVenueData> data) {
        if (mPresenter.isRefresh) {
            mListData.clear();
        }
        mListData.addAll(data);
        //  mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mListAdapter.notifyDataSetChanged();
    }
}