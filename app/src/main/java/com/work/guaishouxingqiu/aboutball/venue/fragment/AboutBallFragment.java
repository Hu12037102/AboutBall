package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.adapter.AboutBallAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.AboutBallPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:54
 * 更新时间: 2019/3/18 10:54
 * 描述: 场馆约球Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_ABOUT_BALL)
public class AboutBallFragment extends DelayedFragment<AboutBallPresenter> implements AboutBallContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_data)
    SmartRefreshLayout mSrlData;
    private AboutBallAdapter mAdapter;
    private List<ResultAboutBallBean> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_ball;
    }

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new AboutBallAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlData.autoRefresh();
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

    @Override
    protected void initDelayedEvent() {
        mSrlData.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    protected AboutBallPresenter createPresenter() {
        return new AboutBallPresenter(this);
    }


    @OnClick(R.id.iv_close)
    public void onViewClicked() {
    }

    @Override
    public void resultAboutBallData(List<ResultAboutBallBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
