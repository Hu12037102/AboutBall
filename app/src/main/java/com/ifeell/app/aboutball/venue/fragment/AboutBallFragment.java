package com.ifeell.app.aboutball.venue.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.venue.activity.AboutBallDetailsActivity;
import com.ifeell.app.aboutball.venue.activity.CreateBallActivity;
import com.ifeell.app.aboutball.venue.adapter.AboutBallAdapter;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallBean;
import com.ifeell.app.aboutball.venue.contract.AboutBallContract;
import com.ifeell.app.aboutball.venue.presenter.AboutBallPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.include_rule)
    View mViewIncludeRule;
    private AboutBallAdapter mAdapter;
    private List<ResultAboutBallBean> mData;
    private int mClickPosition = -1;
    private static final int REQUEST_CODE_CREATE_BALL = 245;

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

        mViewIncludeRule.setBackgroundResource(R.color.colorFFEBF0FF);
        ImageView mIvClose = mViewIncludeRule.findViewById(R.id.iv_close);
        TextView mTvRule = mViewIncludeRule.findViewById(R.id.tv_rule);
        mTvRule.setTextColor(ContextCompat.getColor(mContext, R.color.color_2));
        mIvClose.setImageResource(R.mipmap.icon_item_right);
            /*mIvClose.setOnClickListener(v -> {
                sph.putObject(KEY_RULE_STATUS, false);
                mInflateRuleView.setVisibility(View.GONE);
            });*/
        mTvRule.setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ABOUT_RULE));


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

    @OnClick(R.id.iv_create)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_create:
                ARouterIntent.startActivityForResult(this, CreateBallActivity.class, REQUEST_CODE_CREATE_BALL);
                break;
            default:
                break;
        }
    }

    private void startActivityToAboutBallDetails(ResultAboutBallBean bean) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.REFEREE_STATUS, bean.hasReferee);
        bundle.putInt(ARouterConfig.Key.TEAM_STATUS, bean.hasOpponent);
        bundle.putLong(ARouterConfig.Key.OFFER_ID, bean.agreeId);
        bundle.putInt(ARouterConfig.Key.ABOUT_BALL_FLAG, 0);
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
                if (UserManger.get().isLogin()) {
                    mClickPosition = position;
                    ResultAboutBallBean bean = mData.get(position);
                    startActivityToAboutBallDetails(bean);
                } else {
                    mViewModel.showLoginDialog();
                }

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
            if (mClickPosition != -1 && data != null) {
                int refereeStatus = data.getIntExtra(ARouterConfig.Key.REFEREE_STATUS, -1);
                int teamStatus = data.getIntExtra(ARouterConfig.Key.TEAM_STATUS, -1);
                if (refereeStatus != -1 && teamStatus != -1) {
                    mData.get(mClickPosition).hasReferee = refereeStatus;
                    mData.get(mClickPosition).hasOpponent = teamStatus;
                }
            }
            mAdapter.notifyDataSetChanged();
            mSrlData.autoRefresh();
        } else if (requestCode == REQUEST_CODE_CREATE_BALL && resultCode == Activity.RESULT_OK) {
            mSrlData.autoRefresh();
        }
    }
}
