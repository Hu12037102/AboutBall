package com.ifeell.app.aboutball.game.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.game.adapter.MatchReviewAdapter;
import com.ifeell.app.aboutball.game.bean.ResultReviewBean;
import com.ifeell.app.aboutball.game.contract.MatchReviewContract;
import com.ifeell.app.aboutball.game.presenter.MatchReviewPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 11:25
 * 更新时间: 2019/6/27 11:25
 * 描述:赛事回顾Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MATCH_REVIEW)
public class MatchReviewActivity extends BaseActivity<MatchReviewPresenter> implements MatchReviewContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<ResultReviewBean> mData;
    private MatchReviewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_review;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new MatchReviewAdapter(mData);
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
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_DETAILS, ARouterConfig.Key.GAME_ID, (int) mData.get(position).matchId);
            }
        });
    }

    @Override
    protected MatchReviewPresenter createPresenter() {
        return new MatchReviewPresenter(this);
    }


    @Override
    public void resultReviewData(List<ResultReviewBean> data) {
        if (mData.size() > 0 && mPresenter.isRefresh) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
    }
}
