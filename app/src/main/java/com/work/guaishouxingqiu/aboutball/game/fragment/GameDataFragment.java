package com.work.guaishouxingqiu.aboutball.game.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.game.adapter.GameDataAdapter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDataContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDataPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:43
 * 更新时间: 2019/3/25 9:43
 * 描述:比赛数据fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_GAME_DATA)
public class GameDataFragment extends BaseFragment<GameDataPresenter> implements GameDataContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private ResultGameSimpleBean mBean;
    private List<ResultGameDetailsBean.Bean> mData;
    private GameDataAdapter mAdapter;
    private View mHeadView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_data;
    }

    @Override
    protected void initView() {
        mBean = mBundle.getParcelable(ARouterConfig.Key.GAME_DETAILS_BEAN);
        mRvData.setLayoutManager(new LinearLayoutManager(getContext()));
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_game_data_head_view, mRvData, false);
        CircleImageView cIvLeft = mHeadView.findViewById(R.id.civ_left);
        GlideManger.get().loadImage(DataUtils.checkData(getContext()), mBean.hostLogoUrl, cIvLeft);
        CircleImageView cIvRight = mHeadView.findViewById(R.id.civ_right);
        GlideManger.get().loadImage(getContext(), mBean.guestLogoUrl, cIvRight);
        mHeadView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new GameDataAdapter(mData);
        mAdapter.setHasStableIds(true);
        mAdapter.addHeadView(mHeadView);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadGameDetails(mBean.matchId);
            refreshLayout.finishRefresh();
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
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
    protected GameDataPresenter createPresenter() {
        return new GameDataPresenter(this);
    }




    @Override
    public void resultGameDetails(List<ResultGameDetailsBean.Bean> data) {
        mData.clear();
        mData.addAll(data);
        if (mData.size() > 0){
            mHeadView.setVisibility(View.VISIBLE);
        }else {
            mHeadView.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }
}
