package com.work.guaishouxingqiu.aboutball.community.fragment;

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
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityAttentionContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityAttentionPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        mAdapter = new CommunityDataAdapter(mData);
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

    @Override
    protected void initEvent() {
        mSrlLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(false,refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(true,refreshLayout);
            }
        });
    }

    @Override
    protected CommunityAttentionPresenter createPresenter() {
        return new CommunityAttentionPresenter(this);
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
}
