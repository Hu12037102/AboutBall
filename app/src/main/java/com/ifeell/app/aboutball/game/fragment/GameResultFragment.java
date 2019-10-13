package com.ifeell.app.aboutball.game.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.game.adapter.GameResultAdapter;
import com.ifeell.app.aboutball.game.bean.ResultGameDataBean;
import com.ifeell.app.aboutball.game.bean.ResultGameSimpleBean;
import com.ifeell.app.aboutball.game.contract.MatchResultContract;
import com.ifeell.app.aboutball.game.presenter.MatchResultPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:35
 * 更新时间: 2019/3/25 9:35
 * 描述: 赛况Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_RESULT)
public class GameResultFragment extends DelayedFragment<MatchResultPresenter> implements MatchResultContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private GameResultAdapter mAdapter;
    private List<ResultGameDataBean.Bean> mData;
    private View mHeadView;
    private ResultGameSimpleBean mBean;
    private TextView mTvGrade;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_result;
    }

    @Override
    protected void initDelayedView() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new GameResultAdapter(mData);
        mHeadView = LayoutInflater.from(mRvData.getContext()).inflate(R.layout.item_game_result_head_view, mRvData, false);
        mHeadView.setVisibility(View.GONE);
        mTvGrade = mHeadView.findViewById(R.id.tv_grade);
        mTvGrade.setText(mBean.hostScore.concat(" - ").concat(mBean.guestScore));
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
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
    protected void initView() {
      /*  mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));*/
    }


    @Override
    protected void initData() {
      /*  mData = new ArrayList<>();
        mAdapter = new GameResultAdapter(mData);
        mHeadView = LayoutInflater.from(mRvData.getContext()).inflate(R.layout.item_game_result_head_view, mRvData, false);
        mHeadView.setVisibility(View.GONE);
        TextView mTvGrade =mHeadView.findViewById(R.id.tv_grade);
        mTvGrade.setText(mBean.hostScore.concat(" - ").concat(mBean.guestScore));
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();*/
    }

    @Override
    protected void initEvent() {
       /* mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(false, refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(true, refreshLayout);
            }
        });*/
    }

    private void loadData(boolean isRefresh, RefreshLayout refreshLayout) {
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.loadData(mBean.matchId);
    }

    @Override
    protected MatchResultPresenter createPresenter() {
        return new MatchResultPresenter(this);
    }

    @Override
    public void resultData(ResultGameDataBean bean) {
        mTvGrade.setText(bean.hostScore + " - " + bean.guestScore);
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        if (bean.matchBroadcastList != null) {
            mData.addAll(bean.matchBroadcastList);
            mSrlRefresh.setNoMoreData(bean.matchBroadcastList.size() < mPresenter.mPageSize);
        }

        if (mData.size() > 0) {
            mHeadView.setVisibility(View.VISIBLE);
        } else {
            mHeadView.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }


}
