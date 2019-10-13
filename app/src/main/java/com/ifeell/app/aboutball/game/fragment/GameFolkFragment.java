package com.ifeell.app.aboutball.game.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.game.adapter.GameListAdapter;
import com.ifeell.app.aboutball.game.bean.ResultGameBean;
import com.ifeell.app.aboutball.game.contract.GameFolkContract;
import com.ifeell.app.aboutball.game.presenter.GameFolkPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.weight.Toasts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 10:26
 * 更新时间: 2019/3/15 10:26
 * 描述: 比赛- 民间Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_FOLK)
public class GameFolkFragment extends BaseFragment<GameFolkPresenter> implements GameFolkContract.View {
    @BindView(R.id.rv_recommend)
    RecyclerView mRvData;
    @BindView(R.id.srl_recommend)
    SmartRefreshLayout mSrlData;
    private GameListAdapter mAdapter;
    private List<ResultGameBean> mData;
    private List<ResultGameBean> mGameData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_folk;
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new GameListAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mPresenter.loadGameData(Contast.TYPE_GAME_FOLK);
    }

    private void loadGameList(boolean isRefresh) {
        mPresenter.isRefresh = isRefresh;
        if (mGameData != null && mGameData.size() > 0) {
            if (mPresenter.isRefresh) {
                mPresenter.loadGameRefreshOrMoreData(Contast.TYPE_GAME_FOLK, mGameData.get(0).endTime);
            } else {
                mPresenter.loadGameRefreshOrMoreData(Contast.TYPE_GAME_FOLK, mGameData.get(mGameData.size() - 1).endTime);
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
    protected GameFolkPresenter createPresenter() {
        return new GameFolkPresenter(this);
    }


    @Override
    public void resultGameData(@NonNull BaseBean<List<ResultGameBean>> bean) {
        if (DataUtils.isResultSure(bean) && bean.result.size() > 0) {
            if (mGameData == null) {
                mGameData = new ArrayList<>();
            }
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
