package com.work.guaishouxingqiu.aboutball.my.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.BallDetailsChildAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsChildContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamDetailsChildPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:34
 * 更新时间: 2019/4/25 16:34
 * 描述:球队信息fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_BALL_TEAM_DETAILS)
public class BallTeamDetailsFragment extends BaseFragment<BallTeamDetailsChildPresenter> implements BallTeamDetailsChildContract.View {
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
    private ResultMyBallBean mBallBean;
    private TextView mTvEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ball_team_details;
    }

    @Override
    protected void initView() {
        mBallBean = mBundle.getParcelable(ARouterConfig.Key.PARCELABLE);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        initHeadView();
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
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new BallDetailsChildAdapter(mData);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mSrlRefresh.autoRefresh();
    }

    private void loadRefreshData(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        mPresenter.loadDetails(mBallBean.teamId);
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(this::loadRefreshData);
        mAdapter.setOnBallDetailsClickListener(new BallDetailsChildAdapter.OnBallDetailsClickListener() {
            @Override
            public void onClickEvaluate(View view, int position) {
                ResultBallDetailsBean.MatchBean bean = mData.get(position);
                if (bean.hostTeamId == mBallBean.teamId) {
                    mViewModel.startActivityToPostEvaluation(bean.hostTeamId, bean.guestTeamId, bean.agreeId, bean.refereeId);
                } else {
                    mViewModel.startActivityToPostEvaluation(bean.guestTeamId, bean.hostTeamId, bean.agreeId, bean.refereeId);
                }
            }

            @Override
            public void onClickRecord(View view, int position) {
                mViewModel.startActivityToTeamMatchResult(mData.get(position));
            }

        });
    }

    @Override
    protected BallTeamDetailsChildPresenter createPresenter() {
        return new BallTeamDetailsChildPresenter(this);
    }

    @Override
    public void resultDetails(ResultBallDetailsBean bean) {
        if (mBallBean.isLeader == Contast.LEADER) {
            mTvEdit.setVisibility(View.VISIBLE);
            mTvEdit.setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM, ARouterConfig.Key.PARCELABLE, bean));
        } else if (mBallBean.isLeader == Contast.MEMBER) {
            mTvEdit.setVisibility(View.GONE);
        }
        mTvTeamType.setVisibility(View.VISIBLE);
        GlideManger.get().loadLogoImage(mContext, bean.teamLogo, mCivLogo);
        mTvTeamName.setText(bean.teamName);
        mTvTeamType.setText(bean.teamType);
        mItemMember.setContentText(bean.playerCount + "人");
        mItemColor.setContentText(bean.shirtColor);
        mData.clear();
        if (bean.matchForRecnetList != null) {
            mData.addAll(bean.matchForRecnetList);
        }
        mAdapter.notifyDataSetChanged();
    }
}
