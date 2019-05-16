package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.venue.activity.AboutBallDetailsActivity;
import com.work.guaishouxingqiu.aboutball.venue.adapter.AboutBallAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.AboutBallPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 10:54
 * 更新时间: 2019/3/18 10:54
 * 描述: 场馆约球Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_ABOUT_BALL)
public class AboutBallFragment extends DelayedFragment<AboutBallPresenter> implements AboutBallContract.View {
    public static final String KEY_RULE_STATUS = "key_rule_status";
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_data)
    SmartRefreshLayout mSrlData;
    @BindView(R.id.vs_rule)
    ViewStub mVsRule;
    private AboutBallAdapter mAdapter;
    private List<ResultAboutBallBean> mData;
    private View mInflateRuleView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_ball;
    }

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));

        initRuleView();
    }

    private void initRuleView() {
        SharedPreferencesHelp sph = new SharedPreferencesHelp();
        boolean ruleStatus = sph.getBoolean(KEY_RULE_STATUS, true);
        if (mInflateRuleView == null) {
            mInflateRuleView = mVsRule.inflate();
            ImageView mIvClose = mInflateRuleView.findViewById(R.id.iv_close);
            mIvClose.setOnClickListener(v -> {
                sph.putObject(KEY_RULE_STATUS, false);
                mInflateRuleView.setVisibility(View.GONE);
            });
        }
        if (ruleStatus) {
            mInflateRuleView.setVisibility(View.VISIBLE);
        } else {
            mInflateRuleView.setVisibility(View.GONE);
        }


    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new AboutBallAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlData.autoRefresh();
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

    private void startActivityToAboutBallDetails(ResultAboutBallBean bean) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.REFEREE_STATUS, bean.hasReferee);
        bundle.putInt(ARouterConfig.Key.TEAM_STATUS, bean.hasOpponent);
        bundle.putLong(ARouterConfig.Key.OFFER_ID, bean.offerId);
        ARouterIntent.startActivityForResult(this, AboutBallDetailsActivity.class, bundle);
    }

    @Override
    protected void initDelayedEvent() {
        mSrlData.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(false, refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(true, refreshLayout);
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onNotNetClick(View view) {
                loadData(true, mSrlData);
            }

            @Override
            public void onNotDataClick(View view) {
                loadData(true, mSrlData);
            }

            @Override
            public void onItemClick(View view, int position) {
                ResultAboutBallBean bean = mData.get(position);
                startActivityToAboutBallDetails(bean);
            }
        });
    }

    @Override
    protected AboutBallPresenter createPresenter() {
        return new AboutBallPresenter(this);
    }


    @Override
    public void resultAboutBallData(List<ResultAboutBallBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mSrlData.setNoMoreData(data.size() < mPresenter.mPageSize);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadData(true, mSrlData);
        }
    }
}
