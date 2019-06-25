package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
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
        mHeadView.setVisibility(View.VISIBLE);
        mHeadRvData = mHeadView.findViewById(R.id.rv_data);
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
        mHeadAdapter = new GameHeadDataAdapter(mContext,mHeadData);
        mHeadAdapter.setHasStableIds(true);
        mHeadRvData.setAdapter(mHeadAdapter);
    }

    private void initDataAdapter() {
        mData = new ArrayList<>();
        mResultAdapter = new GameDataAdapter(mData);
        mResultAdapter.addHeadView(mHeadView);
       // mResultAdapter.addFootView(LayoutInflater.from(mContext).inflate(R.layout.item_game_data_foot_view, mRvResult,false));
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

        mHeadData.clear();
        if (bean.host == null || bean.guest == null) {
            return;
        }
        ResultGameDetailsBean.Bean detailsAttackBean = new ResultGameDetailsBean.Bean();
        detailsAttackBean.statsName = UIUtils.getString(R.string.attack);
        detailsAttackBean.hostValue = bean.host.attack;
        detailsAttackBean.guestValue = bean.guest.attack;
        mHeadData.add(detailsAttackBean);

        ResultGameDetailsBean.Bean detailsBallPossessionBean = new ResultGameDetailsBean.Bean();
        detailsBallPossessionBean.statsName = UIUtils.getString(R.string.ball_possession);
        detailsBallPossessionBean.hostValue = DataUtils.getPercent2Float(bean.host.ballPossession);
        detailsBallPossessionBean.guestValue = DataUtils.getPercent2Float(bean.guest.ballPossession);
        detailsBallPossessionBean.dataType = 0;
        mHeadData.add(detailsBallPossessionBean);

        ResultGameDetailsBean.Bean detailsCornerKickBean = new ResultGameDetailsBean.Bean();
        detailsCornerKickBean.statsName = UIUtils.getString(R.string.corner_kick);
        detailsCornerKickBean.hostValue = bean.host.cornerKick;
        detailsCornerKickBean.guestValue = bean.guest.cornerKick;
        mHeadData.add(detailsCornerKickBean);

        ResultGameDetailsBean.Bean detailsDangerAttackBean = new ResultGameDetailsBean.Bean();
        detailsDangerAttackBean.statsName = UIUtils.getString(R.string.danger_attack);
        detailsDangerAttackBean.hostValue = bean.host.dangerAttack;
        detailsDangerAttackBean.guestValue = bean.guest.dangerAttack;
        mHeadData.add(detailsDangerAttackBean);

        ResultGameDetailsBean.Bean detailsFoulBean = new ResultGameDetailsBean.Bean();
        detailsFoulBean.statsName = UIUtils.getString(R.string.foul);
        detailsFoulBean.hostValue = bean.host.foul;
        detailsFoulBean.guestValue = bean.guest.foul;
        mHeadData.add(detailsFoulBean);

        ResultGameDetailsBean.Bean detailsFreeKickBean = new ResultGameDetailsBean.Bean();
        detailsFreeKickBean.statsName = UIUtils.getString(R.string.free_kick);
        detailsFreeKickBean.hostValue = bean.host.freeKick;
        detailsFreeKickBean.guestValue = bean.guest.freeKick;
        mHeadData.add(detailsFreeKickBean);

        ResultGameDetailsBean.Bean detailsOffsideBean = new ResultGameDetailsBean.Bean();
        detailsOffsideBean.statsName = UIUtils.getString(R.string.offside);
        detailsOffsideBean.hostValue = bean.host.offside;
        detailsOffsideBean.guestValue = bean.host.offside;
        mHeadData.add(detailsOffsideBean);

        ResultGameDetailsBean.Bean detailsPassingCompletionBean = new ResultGameDetailsBean.Bean();
        detailsPassingCompletionBean.statsName = UIUtils.getString(R.string.passing_completion);
        detailsPassingCompletionBean.hostValue = DataUtils.getPercent2Float(bean.host.passingCompletion);
        detailsPassingCompletionBean.guestValue = DataUtils.getPercent2Float(bean.guest.passingCompletion);
        detailsPassingCompletionBean.dataType = 0;
        mHeadData.add(detailsPassingCompletionBean);

        ResultGameDetailsBean.Bean detailsShootBean = new ResultGameDetailsBean.Bean();
        detailsShootBean.statsName = UIUtils.getString(R.string.shoot);
        detailsShootBean.hostValue = bean.host.shoot;
        detailsShootBean.guestValue = bean.guest.shoot;
        mHeadData.add(detailsShootBean);

        ResultGameDetailsBean.Bean detailsShootOnTargetBean = new ResultGameDetailsBean.Bean();
        detailsShootOnTargetBean.statsName = UIUtils.getString(R.string.shoot_on_target);
        detailsShootOnTargetBean.hostValue = bean.host.shootOnTarget;
        detailsShootOnTargetBean.guestValue = bean.guest.shootOnTarget;
        mHeadData.add(detailsShootOnTargetBean);
        mHeadAdapter.notifyDataSetChanged();
        mPresenter.loadGameResultDetails(1);
    }

    @Override
    public void resultGameResultDetails(List<ResultGameDataResultBean> data) {
        mData.clear();
        mData.addAll(data);
        mResultAdapter.notifyDataSetChanged();
    }
}
