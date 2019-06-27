package com.work.guaishouxingqiu.aboutball.game.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.game.adapter.MatchReviewAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultReviewBean;
import com.work.guaishouxingqiu.aboutball.game.contract.MatchReviewContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.MatchReviewPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

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

    private void loadData() {
        mSrlRefresh.finishRefresh();
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
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
        if (mData.size() > 0) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
    }
}
