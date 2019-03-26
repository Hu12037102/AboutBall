package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameCollectionAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCollectionContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameCollectionPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:22
 * 更新时间: 2019/3/25 10:22
 * 描述:比赛-集锦fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_COLLECTION)
public class GameCollectionFragment extends BaseFragment<GameCollectionPresenter> implements
        GameCollectionContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private ResultGameSimpleBean mBean;
    private GameCollectionAdapter mAdapter;
    private List<ResultGameCollectionBean> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_collection;
    }

    @Override
    protected void initView() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvData.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new GameCollectionAdapter(mData);
        mAdapter.setHasStableIds(true);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadData(mBean.matchId);
            refreshLayout.finishRefresh();
        });
    }


    @Override
    protected GameCollectionPresenter createPresenter() {
        return new GameCollectionPresenter(this);
    }

    @Override
    public void resultData(List<ResultGameCollectionBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSrlRefresh.setNoMoreData(true);
    }
}
