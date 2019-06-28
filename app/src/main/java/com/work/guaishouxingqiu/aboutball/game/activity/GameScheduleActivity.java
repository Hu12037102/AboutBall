package com.work.guaishouxingqiu.aboutball.game.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameScheduleAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameScheduleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameScheduleContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameSchedulePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:35
 * 更新时间: 2019/6/11 9:35
 * 描述:赛程Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SCHEDULE)
public class GameScheduleActivity extends BaseActivity<GameSchedulePresenter> implements GameScheduleContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private String mRequestTime = DateUtils.yearToDate(System.currentTimeMillis());
    private List<ResultGameScheduleBean> mData;
    private GameScheduleAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasMore = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRvData.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new GameScheduleAdapter(mData);

        mRvData.setAdapter(mAdapter);
        mPresenter.loadScheduleData(mRequestTime);
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
        mRvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mData.size() > mLayoutManager.findFirstVisibleItemPosition()) {
                    UIUtils.setText(mTvTime, DateUtils.getDate(mData.get(mLayoutManager.findFirstVisibleItemPosition()).date));
                }
            }
        });
    }

    private void loadData(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            if (mData.size() > 0) {
                mRequestTime = mData.get(0).previousDate;
            }
            mSrlRefresh.finishRefresh();
        } else {
            if (mData.size() > 0) {
                mRequestTime = mData.get(mData.size() - 1).nextDate;
            }
            mSrlRefresh.finishLoadMore();
        }

        mPresenter.loadScheduleData(mRequestTime);
    }

    @Override
    protected GameSchedulePresenter createPresenter() {
        return new GameSchedulePresenter(this);
    }


    @Override
    public void resultScheduleData(List<ResultGameScheduleBean> data) {
        if (data.size() > 0) {
            if (mPresenter.isRefresh) {
                mData.addAll(0, data);
            } else {
                mData.addAll(data);
            }
        }

        mSrlRefresh.setEnableLoadMore(mIsHasMore);
        mAdapter.notifyData(!mIsHasMore);
        UIUtils.setText(mTvTime, DateUtils.getDate(mData.get(mLayoutManager.findFirstVisibleItemPosition()).date));
    }

    @Override
    public void resultNotData() {
        this.mIsHasMore = false;
        mSrlRefresh.setEnableLoadMore(false);
        mAdapter.notifyData(true);
    }
}
