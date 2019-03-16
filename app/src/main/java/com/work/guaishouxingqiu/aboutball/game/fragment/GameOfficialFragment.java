package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameListAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameOfficialContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameOfficialPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:20
 * 更新时间: 2019/3/15 9:20
 * 描述: 比赛官方fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_OFFICIAL)
public class GameOfficialFragment extends BaseFragment<GameOfficialPresenter> implements GameOfficialContract.View {
    @BindView(R.id.rv_recommend)
    RecyclerView mRvData;
    @BindView(R.id.srl_recommend)
    SmartRefreshLayout mSrlData;
    private GameListAdapter mAdapter;
    private List<ResultGameBean> mData;
    private List<ResultGameBean> mGameData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_officia;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mGameData = new ArrayList<>();
        mAdapter = new GameListAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mPresenter.loadGameData(Contast.TYPE_GAME_OFFICIAL);
    }

    private void loadGameList(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (mGameData != null && mGameData.size() > 0) {
            if (mPresenter.isRefresh){
                mPresenter.loadGameRefreshOrMoreData(Contast.TYPE_GAME_OFFICIAL, mGameData.get(0).endTime);
            }else {
                mPresenter.loadGameRefreshOrMoreData(Contast.TYPE_GAME_OFFICIAL, mGameData.get(mGameData.size() - 1).endTime);
            }
        } else {
            Toasts.with().showToast(R.string.there_are_no_related_events);
        }
    }

    @Override
    protected void initEvent() {
        mSrlData.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadGameList(false);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadGameList(true);
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected GameOfficialPresenter createPresenter() {
        return new GameOfficialPresenter(this);
    }


    @Override
    public void resultGameData(@NonNull BaseBean<List<ResultGameBean>> bean) {
        if (DataUtils.isResultSure(bean) && bean.result.size() > 0) {

            mGameData.addAll(bean.result);
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resultGameRefreshOrMoreData(@NonNull BaseBean<List<ResultGameBean>> bean) {
        if (DataUtils.isResultSure(bean) && bean.result.size() > 0) {
            mData.clear();
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }
}
