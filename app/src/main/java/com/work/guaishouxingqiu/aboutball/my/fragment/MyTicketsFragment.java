package com.work.guaishouxingqiu.aboutball.my.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.activity.MyTicketsAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyTicketsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyTicketsChildContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyTicketsChildPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:38
 * 更新时间: 2019/9/4 17:38
 * 描述:我的门票fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_TICKETS)
public class MyTicketsFragment extends DelayedFragment<MyTicketsChildPresenter> implements MyTicketsChildContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mStatus;
    private List<ResultMyTicketsBean> mData;
    private MyTicketsAdapter mAdapter;

    @Override
    protected void initPermission() {
        //0：未使用门票 1：已使用门票
        mStatus = mBundle.getInt(ARouterConfig.Key.KEY_STATUS, -1);
        if (mStatus == -1) {
            UIUtils.showToast(R.string.not_find_this_tickets);
            getBaseActivity().finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initDelayedView() {
        if (mStatus != 0) {
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
        mPresenter.loadMyTicketsList(mStatus);
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        mAdapter = new MyTicketsAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mStatus == 0) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initData() {
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_tickets_child;
    }

    @Override
    protected MyTicketsChildPresenter createPresenter() {
        return new MyTicketsChildPresenter(this);
    }

    @Override
    public void resultMyTicketList(List<ResultMyTicketsBean> data) {
        if (data.size() > 0) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mData.addAll(data);
        }
        mSrlRefresh.setEnableLoadMore(data.size() == mPresenter.mPageSize);
        mAdapter.notifyData(data.size() == mPresenter.mPageSize);
    }
}
