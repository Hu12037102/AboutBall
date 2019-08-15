package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
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

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/6 16:35
 * 更新时间: 2019/8/6 16:35
 * 描述:场馆列表Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_LIST)
public class VenueListActivity extends BaseActivity<VenueListPresenter> implements VenueListContract.View {
    @BindView(R.id.vs_data)
    ViewStub mVsNoData;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlRefresh;
    private View mNoTypeInflateView;
    private RequestVenueListBean mRequestBean;
    private List<ResultVenueData> mListData;
    private VenueListAdapter mListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_list;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        if (mNoTypeInflateView == null) {
            mNoTypeInflateView = mVsNoData.inflate();
            mNoTypeInflateView.findViewById(R.id.iv_not_data).setOnClickListener(v -> mPresenter.start());
        }
        mNoTypeInflateView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        mRequestBean = new RequestVenueListBean();
        mRequestBean.longitude = String.valueOf(getSPLongitude());
        mRequestBean.latitude = String.valueOf(getSPLatitude());
        mPresenter.start();
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
    }

    @Override
    protected VenueListPresenter createPresenter() {
        return new VenueListPresenter(this);
    }

    @Override
    public void resultBallTypeList(List<ResultTypeBean> data) {
        mNoTypeInflateView.setVisibility(View.GONE);
        mSrlRefresh.setVisibility(View.VISIBLE);
        if (data.size() > 0) {
            mRequestBean.typeId = data.get(0).typeId;
        }

        if (mListAdapter == null) {
            mListData = new ArrayList<>();
            mListAdapter = new VenueListAdapter(mListData);
            mRvData.setAdapter(mListAdapter);
            mListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
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
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_DETAILS, ARouterConfig.Key.STADIUM_ID, mListData.get(position).stadiumId);


                }
            });
        } else {
            mListAdapter.notifyDataSetChanged();
        }
        mSrlRefresh.autoRefresh();
    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        if (mRequestBean != null) {
            mPresenter.isRefresh = isRefresh;
            mPresenter.loadCanUserVenueList(mRequestBean);
            if (isRefresh) {
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void resultVenueList(List<ResultVenueData> data) {
        if (mPresenter.isRefresh) {
            mListData.clear();
        }
        mListData.addAll(data);
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultCanUserVenueList(List<ResultVenueData> data) {
        if (mPresenter.isRefresh) {
            mListData.clear();
        }
        mListData.addAll(data);
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultBallTypeError() {
        mNoTypeInflateView.setVisibility(View.VISIBLE);
        mSrlRefresh.setVisibility(View.GONE);
    }

}
