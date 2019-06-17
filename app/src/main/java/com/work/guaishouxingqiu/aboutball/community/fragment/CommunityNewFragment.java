package com.work.guaishouxingqiu.aboutball.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.community.activity.DynamicEditActivity;
import com.work.guaishouxingqiu.aboutball.community.adapter.CommunityDataAdapter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityNewsContract;
import com.work.guaishouxingqiu.aboutball.community.presenter.CommunityNewsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 14:06
 * 更新时间: 2019/3/19 14:06
 * 描述:社区-最新fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_COMMUNITY_NEWS)
public class CommunityNewFragment extends DelayedFragment<CommunityNewsPresenter>
        implements CommunityNewsContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrlLayout;
    private CommunityDataAdapter mAdapter;
    private List<ResultCommunityDataBean> mData;
    private static final int REQUEST_CODE_PUBLISH_DYNAMIC=145;

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
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
    }

    @OnClick(R.id.iv_add_community)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_add_community:
                ARouterIntent.startActivityForResult(this, DynamicEditActivity.class, CommunityNewFragment.REQUEST_CODE_PUBLISH_DYNAMIC);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_news;
    }

    @Override
    protected CommunityNewsPresenter createPresenter() {
        return new CommunityNewsPresenter(this);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CommunityNewFragment.REQUEST_CODE_PUBLISH_DYNAMIC:
                    mSrlLayout.autoRefresh();
                    break;
                default:
                    break;
            }
        }

    }
}
