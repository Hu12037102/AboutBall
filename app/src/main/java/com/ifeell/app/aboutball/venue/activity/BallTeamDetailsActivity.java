package com.ifeell.app.aboutball.venue.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.adapter.BallDetailsChildAdapter;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.other.GlideManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.contract.BallTeamDetailsContract;
import com.ifeell.app.aboutball.venue.presenter.BallTeamDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/17 9:55
 * 更新时间: 2019/5/17 9:55
 * 描述:球队详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS_VENUE)
public class BallTeamDetailsActivity extends BaseActivity<BallTeamDetailsPresenter> implements BallTeamDetailsContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private View mHeadView;
    private CircleImageView mCivLogo;
    private TextView mTvTeamName;
    private TextView mTvTeamType;
    private ItemView mItemMember;
    private ItemView mItemColor;
    private List<ResultBallDetailsBean.MatchBean> mData;
    private BallDetailsChildAdapter mAdapter;
    // private ResultMyBallBean mBallBean;
    private TextView mTvEdit;
    private long mTeamId;
    private String mShirtColor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_venue_details;
    }

    @Override
    protected void initView() {
     /*   Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }*/
        mTeamId = mIntent.getLongExtra(ARouterConfig.Key.TEAM_ID, -1);
        mShirtColor = mIntent.getStringExtra(ARouterConfig.Key.SHIRT_COLOR);
        if (mTeamId == -1) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        initHeadView();

        mData = new ArrayList<>();
        mAdapter = new BallDetailsChildAdapter(mData,false);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initData() {

    }

    private void initHeadView() {
        mHeadView = getLayoutInflater().inflate(R.layout.item_head_ball_team_details_view, mRvData, false);
        mCivLogo = mHeadView.findViewById(R.id.civ_logo);
        mTvTeamName = mHeadView.findViewById(R.id.tv_team_name);
        mTvTeamType = mHeadView.findViewById(R.id.tv_team_type);
        mTvTeamType.setVisibility(View.GONE);
        mItemMember = mHeadView.findViewById(R.id.item_member);
        mItemColor = mHeadView.findViewById(R.id.item_color);
        mTvEdit = mHeadView.findViewById(R.id.tv_edit);
        mTvEdit.setVisibility(View.GONE);
    }

    private void loadRefreshData(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.loadDetails(mTeamId);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(this::loadRefreshData);
        mAdapter.setOnBallDetailsClickListener(new BallDetailsChildAdapter.OnBallDetailsClickListener() {
            @Override
            public void onClickEvaluate(View view, int position) {
               /* ResultBallDetailsBean.MatchBean bean = mData.get(position);
                if (bean.hostTeamId == mTeamId) {
                    mViewModel.startActivityToPostEvaluation(bean.hostTeamId, bean.guestTeamId, bean.agreeId, bean.refereeId);
                } else {
                    mViewModel.startActivityToPostEvaluation(bean.guestTeamId, bean.guestTeamId, bean.agreeId, bean.refereeId);
                }*/
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MY_TEAM_EVALUATE,ARouterConfig.Key.ID,mTeamId);
            }

            @Override
            public void onClickRecord(View view, int position) {
               // mViewModel.startActivityToTeamMatchResult(mData.get(position));

            }

        });
    }

    @Override
    protected BallTeamDetailsPresenter createPresenter() {
        return new BallTeamDetailsPresenter(this);
    }


    @Override
    public void resultDetails(ResultBallDetailsBean bean) {
       /* if (mBallBean.isLeader == Contast.LEADER) {
            mTvEdit.setVisibility(View.VISIBLE);
            mTvEdit.setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM,ARouterConfig.Key.PARCELABLE,bean));
        } else if (mBallBean.isLeader == Contast.MEMBER) {
            mTvEdit.setVisibility(View.GONE);
        }*/
        mTvTeamType.setVisibility(View.VISIBLE);
        GlideManger.get().loadLogoImage(this, bean.teamLogo, mCivLogo);
        mTvTeamName.setText(bean.teamName);
        mTvTeamType.setText(bean.teamType);
        mItemMember.setContentText(bean.playerCount + "人");
        mItemColor.setContentText(DataUtils.isEmpty(mShirtColor) ? bean.shirtColor : mShirtColor);
        mData.clear();
        if (bean.matchForRecnetList != null) {
            mData.addAll(bean.matchForRecnetList);
        }
        mAdapter.notifyDataSetChanged();
    }
}
