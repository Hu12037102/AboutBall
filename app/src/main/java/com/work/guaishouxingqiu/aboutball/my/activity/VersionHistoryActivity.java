package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.VersionHistoryAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultVersionHistoryBean;
import com.work.guaishouxingqiu.aboutball.my.contract.VersionHistoryContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.VersionHistoryPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/4 10:59
 * 更新时间: 2019/7/4 10:59
 * 描述:；历史版本Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VERSION_HISTORY)
public class VersionHistoryActivity extends BaseActivity<VersionHistoryPresenter> implements VersionHistoryContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<ResultVersionHistoryBean> mData;
    private VersionHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version_history;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new VersionHistoryAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        mPresenter.start();
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

            }
        });
    }

    @Override
    protected VersionHistoryPresenter createPresenter() {
        return new VersionHistoryPresenter(this);
    }


    @Override
    public void resultVersionHistoryData(List<ResultVersionHistoryBean> data) {
        if (mData.size() > 0 && mPresenter.isRefresh) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mSrlRefresh.setEnableLoadMore(data.size() == mPresenter.mPageSize);
        mAdapter.notifyData(data.size() == mPresenter.mPageSize);
    }
}
