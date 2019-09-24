package com.work.guaishouxingqiu.aboutball.game.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameDataAdapter;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameHeadDataAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataInfoBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDataPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:43
 * 更新时间: 2019/3/25 9:43
 * 描述:比赛数据fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_DATA)
public class GameDataFragment extends DelayedFragment<GameDataPresenter> implements GameDataContract.View {

    @BindView(R.id.rv_result)
    RecyclerView mRvResult;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private ResultGameSimpleBean mBean;
    private List<ResultGameDetailsBean.Bean> mHeadData;
    private GameHeadDataAdapter mHeadAdapter;
    private View mHeadView;
    private RecyclerView mHeadRvData;
    private List<ResultGameDataResultBean> mData;
    private GameDataAdapter mResultAdapter;
    private TextView mHeadTvStart;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_data;
    }

    @Override
    protected void initDelayedView() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_game_data_head_view, mRvResult, false);
        CircleImageView cIvLeft = mHeadView.findViewById(R.id.civ_left);
        GlideManger.get().loadImage(DataUtils.checkData(getContext()), mBean.hostLogoUrl, cIvLeft);
        CircleImageView cIvRight = mHeadView.findViewById(R.id.civ_right);
        GlideManger.get().loadImage(getContext(), mBean.guestLogoUrl, cIvRight);
        mHeadTvStart = mHeadView.findViewById(R.id.tv_start);
        mHeadView.setVisibility(View.VISIBLE);
        mHeadRvData = mHeadView.findViewById(R.id.rv_data);
        LinearLayout llHeadTop = mHeadView.findViewById(R.id.ll_head_top);
        llHeadTop.setVisibility(View.GONE);
        mHeadRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initDelayedData() {
        initHeadAdapter();
        initDataAdapter();
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadGameHeadDetails(mBean.matchId);
            refreshLayout.finishRefresh();
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
    protected void initView() {
       /* mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_game_data_head_view, mRvResult, false);
        CircleImageView cIvLeft = mHeadView.findViewById(R.id.civ_left);
        GlideManger.get().loadImage(DataUtils.checkData(getContext()), mBean.hostLogoUrl, cIvLeft);
        CircleImageView cIvRight = mHeadView.findViewById(R.id.civ_right);
        GlideManger.get().loadImage(getContext(), mBean.guestLogoUrl, cIvRight);
        mHeadView.setVisibility(View.GONE);
        mRvData = mHeadView.findViewById(R.id.rv_data);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));*/
    }

    @Override
    protected void initData() {
        /*initHeadAdapter();
        initDataAdapter();
        mSrlRefresh.autoRefresh();*/
    }


    private void initHeadAdapter() {
        mHeadData = new ArrayList<>();
        mHeadAdapter = new GameHeadDataAdapter(mContext, mHeadData);
        mHeadAdapter.setHasStableIds(true);
        mHeadRvData.setAdapter(mHeadAdapter);
    }

    private void initDataAdapter() {
        mData = new ArrayList<>();
        mResultAdapter = new GameDataAdapter(mData);
        mResultAdapter.addHeadView(mHeadView);

        mRvResult.setAdapter(mResultAdapter);

    }

    @Override
    protected void initEvent() {
        /*mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            // mPresenter.loadGameHeadDetails(mBean.matchId);
            mPresenter.loadGameHeadDetails(1);

            refreshLayout.finishRefresh();
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
        });*/
    }


    @Override
    protected GameDataPresenter createPresenter() {
        return new GameDataPresenter(this);
    }


    @Override
    public void resultHeadGameDetails(ResultGameDataInfoBean bean) {
        if (bean == null) {
            return;
        }
        mHeadData.clear();
        ResultGameDetailsBean.Bean detailsBallPossessionBean = new ResultGameDetailsBean.Bean();
        detailsBallPossessionBean.statsName = UIUtils.getString(R.string.ball_possession);
        detailsBallPossessionBean.hostValue = bean.host == null ? 0 : bean.host.ballPossession;
        detailsBallPossessionBean.guestValue = bean.guest == null ? 0 : bean.guest.ballPossession;
        detailsBallPossessionBean.dataType = 0;
        mHeadData.add(detailsBallPossessionBean);

        ResultGameDetailsBean.Bean goalBean = new ResultGameDetailsBean.Bean();
        goalBean.statsName = UIUtils.getString(R.string.goal);
        goalBean.hostValue = bean.host == null ? 0 : bean.host.goal;
        goalBean.guestValue = bean.guest == null ? 0 : bean.guest.goal;
        mHeadData.add(goalBean);

        ResultGameDetailsBean.Bean detailsPassingCompletionBean = new ResultGameDetailsBean.Bean();
        detailsPassingCompletionBean.statsName = UIUtils.getString(R.string.passing_completion);
        detailsPassingCompletionBean.hostValue = bean.host == null ? 0 : bean.host.passingCompletion;
        detailsPassingCompletionBean.guestValue = bean.guest == null ? 0 : bean.guest.passingCompletion;
        detailsPassingCompletionBean.dataType = 0;
        mHeadData.add(detailsPassingCompletionBean);

        ResultGameDetailsBean.Bean detailsShootBean = new ResultGameDetailsBean.Bean();
        detailsShootBean.statsName = UIUtils.getString(R.string.shoot);
        detailsShootBean.hostValue = bean.host == null ? 0 : bean.host.shoot;
        detailsShootBean.guestValue = bean.guest == null ? 0 : bean.guest.shoot;
        mHeadData.add(detailsShootBean);

        ResultGameDetailsBean.Bean detailsShootOnTargetBean = new ResultGameDetailsBean.Bean();
        detailsShootOnTargetBean.statsName = UIUtils.getString(R.string.shoot_on_target);
        detailsShootOnTargetBean.hostValue = bean.host == null ? 0 : bean.host.shootOnTarget;
        detailsShootOnTargetBean.guestValue = bean.guest == null ? 0 : bean.guest.shootOnTarget;
        mHeadData.add(detailsShootOnTargetBean);

        ResultGameDetailsBean.Bean detailsAttackBean = new ResultGameDetailsBean.Bean();
        detailsAttackBean.statsName = UIUtils.getString(R.string.attack);
        detailsAttackBean.hostValue = bean.host == null ? 0 : bean.host.attack;
        detailsAttackBean.guestValue = bean.guest == null ? 0 : bean.guest.attack;
        mHeadData.add(detailsAttackBean);

        ResultGameDetailsBean.Bean detailsDangerAttackBean = new ResultGameDetailsBean.Bean();
        detailsDangerAttackBean.statsName = UIUtils.getString(R.string.danger_attack);
        detailsDangerAttackBean.hostValue = bean.host == null ? 0 : bean.host.dangerAttack;
        detailsDangerAttackBean.guestValue = bean.guest == null ? 0 : bean.guest.dangerAttack;
        mHeadData.add(detailsDangerAttackBean);

        ResultGameDetailsBean.Bean detailsFreeKickBean = new ResultGameDetailsBean.Bean();
        detailsFreeKickBean.statsName = UIUtils.getString(R.string.free_kick);
        detailsFreeKickBean.hostValue = bean.host == null ? 0 : bean.host.freeKick;
        detailsFreeKickBean.guestValue = bean.guest == null ? 0 : bean.guest.freeKick;
        mHeadData.add(detailsFreeKickBean);

        ResultGameDetailsBean.Bean detailsOffsideBean = new ResultGameDetailsBean.Bean();
        detailsOffsideBean.statsName = UIUtils.getString(R.string.offside);
        detailsOffsideBean.hostValue = bean.host == null ? 0 : bean.host.offside;
        detailsOffsideBean.guestValue = bean.guest == null ? 0 : bean.guest.offside;
        mHeadData.add(detailsOffsideBean);

        ResultGameDetailsBean.Bean detailsFoulBean = new ResultGameDetailsBean.Bean();
        detailsFoulBean.statsName = UIUtils.getString(R.string.foul);
        detailsFoulBean.hostValue = bean.host == null ? 0 : bean.host.foul;
        detailsFoulBean.guestValue = bean.guest == null ? 0 : bean.guest.foul;
        mHeadData.add(detailsFoulBean);

        ResultGameDetailsBean.Bean yellowCardBean = new ResultGameDetailsBean.Bean();
        yellowCardBean.statsName = UIUtils.getString(R.string.yellow_card);
        yellowCardBean.hostValue = bean.host == null ? 0 : bean.host.yellowCard;
        yellowCardBean.guestValue = bean.guest == null ? 0 : bean.guest.yellowCard;
        mHeadData.add(yellowCardBean);

        ResultGameDetailsBean.Bean redCardBean = new ResultGameDetailsBean.Bean();
        redCardBean.statsName = UIUtils.getString(R.string.red_card);
        redCardBean.hostValue = bean.host == null ? 0 : bean.host.redCard;
        redCardBean.guestValue = bean.guest == null ? 0 : bean.guest.redCard;
        mHeadData.add(redCardBean);

        /*ResultGameDetailsBean.Bean detailsCornerKickBean = new ResultGameDetailsBean.Bean();
        detailsCornerKickBean.statsName = UIUtils.getString(R.string.corner_kick);
        detailsCornerKickBean.hostValue = bean.host.cornerKick;
        detailsCornerKickBean.guestValue = bean.guest.cornerKick;
        mHeadData.add(detailsCornerKickBean);*/
        mPresenter.loadGameResultDetails(mBean.matchId);
    }

    @Override
    public void resultGameResultDetails(List<ResultGameDataResultBean> data) {
        mData.clear();
        mData.addAll(data);
        mHeadTvStart.setVisibility(mData.size() == 0 ? View.GONE : View.VISIBLE);
        mHeadAdapter.notifyDataSetChanged();
        mResultAdapter.notifyDataSetChanged();
    }
}
