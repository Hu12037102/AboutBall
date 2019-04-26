package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.BallTeamMemberAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMemberContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamMemberPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:23
 * 更新时间: 2019/4/26 9:23
 * 描述:球队成员fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_BALL_TEAM_MEMBER)
public class BallTeamMemberFragment extends DelayedFragment<BallTeamMemberPresenter> implements BallTeamMemberContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private BallTeamMemberAdapter mAdapter;
    private List<ResultTeamDetailsMemberBean> mData;
    private ResultMyBallBean mBallBean;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ball_team_member;
    }

    @Override
    protected void initDelayedView() {
        mBallBean = mBundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new BallTeamMemberAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshListener(this::loadRefresh);
    }

    private void loadRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.loadMemberDetails(mBallBean.teamId);
    }

    @Override
    protected BallTeamMemberPresenter createPresenter() {
        return new BallTeamMemberPresenter(this);
    }


    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_left:
                break;
            case R.id.tv_bottom_right:
                break;
        }
    }

    @Override
    public void resultMemberDetails(List<ResultTeamDetailsMemberBean> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
