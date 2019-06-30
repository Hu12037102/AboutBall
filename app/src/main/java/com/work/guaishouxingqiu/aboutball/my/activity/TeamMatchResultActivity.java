package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameDataAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.TeamMatchResultContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.TeamMatchResultPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况fragment
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TEAM_MATCH_RESULT)
public class TeamMatchResultActivity extends BaseActivity<TeamMatchResultPresenter> implements
        TeamMatchResultContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_vs)
    TextView tvVs;
    @BindView(R.id.civ_left)
    CircleImageView mCivLeft;
    @BindView(R.id.tv_host)
    TextView mTvHost;
    @BindView(R.id.civ_right)
    CircleImageView mCivRight;
    @BindView(R.id.tv_guest)
    TextView mTvGuest;
    private ResultBallDetailsBean.MatchBean mIntentBean;
    private List<ResultGameDataResultBean> mData;
    private GameDataAdapter mResultAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_match_result;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_match);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        UIUtils.setText(mTvHost, mIntentBean.hostTeamName);
        UIUtils.setText(mTvGuest, mIntentBean.guestTeamName);
        GlideManger.get().loadLogoImage(this, mIntentBean.hostTeamLogo, mCivLeft);
        GlideManger.get().loadLogoImage(this, mIntentBean.guestTeamLogo, mCivRight);

    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mResultAdapter = new GameDataAdapter(mData);
        mRvData.setAdapter(mResultAdapter);
        mSrlRefresh.autoRefresh();
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        mPresenter.loadGameResultDetails(mIntentBean.matchId);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        mResultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
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

            }
        });
    }

    @Override
    protected TeamMatchResultPresenter createPresenter() {
        return new TeamMatchResultPresenter(this);
    }

    @Override
    public void resultGameResultDetails(List<ResultGameDataResultBean> data) {
        mData.clear();
        mData.addAll(data);
        mResultAdapter.notifyDataSetChanged();
    }


}
