package com.ifeell.app.aboutball.home.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.home.adapter.RecommendedAdapter;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.home.contract.HighlightsContract;
import com.ifeell.app.aboutball.home.presenter.HighlightsPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:57
 * 更新时间: 2019/3/12 17:57
 * 描述:赛事Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_HIGHLIGHTS)
public class HighlightsFragment extends DelayedFragment<HighlightsPresenter>
        implements HighlightsContract.View {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSrLayout;
    Unbinder unbinder;
    private List<ResultNewsBean> mData;
    private RecommendedAdapter mAdapter;
    private int mTypId;

    public static HighlightsFragment newInstance() {
        return new HighlightsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_highlights;

    }

    @Override
    protected void initPermission() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mTypId = bundle.getInt(ARouterConfig.Key.TAB_TYPE_ID);
        super.initPermission();
    }

    @Override
    protected HighlightsPresenter createPresenter() {
        return new HighlightsPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        mRvList.setLayoutManager(new LinearLayoutManager(DataUtils.checkData(getContext())));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new RecommendedAdapter(mData);
        mAdapter.setHasStableIds(true);
        mRvList.setAdapter(mAdapter);
        mSrLayout.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = false;
                mPresenter.loadData(mTypId);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.isRefresh = true;
                mPresenter.loadData(mTypId);
                refreshLayout.finishRefresh();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {

            }

            @Override
            public void onNotDataClick(View view) {

            }

            @Override
            public void onItemClick(View view, int position) {
                ResultNewsBean bean = mData.get(position);
                if (!bean.isRead) {
                    DataUtils.putNewsKey(bean.newsId);
                    mAdapter.notifyDataSetChanged();
                }
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEW_DETAILS,
                        ARouterConfig.Key.NEW_DETAILS_ID, mData.get(position).newsId);
            }
        });
    }

    @Override
    public void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean) {
        if (DataUtils.isResultSure(bean)) {
            if (mPresenter.isRefresh) {
                mData.clear();
            }
            mSrLayout.setNoMoreData(bean.result.size() < Contast.DEFAULT_PAGE_SIZE);
            mData.addAll(bean.result);
            mAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.tv_schedule, R.id.tv_playback, R.id.tv_data, R.id.tv_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_schedule:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_SCHEDULE);
                break;
            case R.id.tv_playback:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MATCH_REVIEW);
                break;
            case R.id.tv_data:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_INFO);
                break;
            case R.id.tv_ticket:
                break;
        }
    }
}
