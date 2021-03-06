package com.ifeell.app.aboutball.venue.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.adapter.BallTypeAdapter;
import com.ifeell.app.aboutball.venue.adapter.VenueListAdapter;
import com.ifeell.app.aboutball.venue.bean.RequestVenueListBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.contract.VenueListContract;
import com.ifeell.app.aboutball.venue.presenter.VenueListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.vs_data)
    ViewStub mVsNoData;
    private View mNoTypeInflateView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_venue_list;
    }

    @Override
    protected void initView() {
        mRvBallType.setVisibility(View.GONE);
        mRvBallType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mNoTypeInflateView == null) {
            mNoTypeInflateView = mVsNoData.inflate();
            mNoTypeInflateView.findViewById(R.id.iv_not_data).setOnClickListener(v -> mPresenter.start());
        }
        mNoTypeInflateView.setVisibility(View.GONE);
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

    private void initLocation() {
       /* Location location = PhoneUtils.getGPSLocation(this);
        if (location != null) {
            LogUtils.w("location--", mRequestBean.latitude + "--" + mRequestBean.longitude);
            mRequestBean.latitude = String.valueOf(location.getLatitude());
            mRequestBean.longitude = String.valueOf(location.getLongitude());
        }*/
        mRequestBean.longitude = String.valueOf(getSPLongitude());
        mRequestBean.latitude = String.valueOf(getSPLatitude());
        mSrlRefresh.autoRefresh();
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
              /*  if (mSrlRefresh.isRefreshing()) {
                    mPresenter.isRefresh = true;
                    mPresenter.loadVenueList(mRequestBean);
                } else {
                    mSrlRefresh.autoRefresh();
                }*/
                mPresenter.isRefresh = true;
                mPresenter.loadVenueList(mRequestBean);

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
                    DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0302);
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_VENUE_DETAILS, ARouterConfig.Key.STADIUM_ID, mListData.get(position).stadiumId);
                    //Toasts.with().showToast(R.string.pleases_next_open);
                }
            });
            mRequestBean = new RequestVenueListBean();
            mRequestBean.typeId = data.get(0).typeId;
            initLocation();
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
    public void resultBallTypeError() {
        mNoTypeInflateView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contast.REQUEST_CODE && resultCode == Contast.REQUEST_CODE) {
            initLocation();
        }
    }
}
