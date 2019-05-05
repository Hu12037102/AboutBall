package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.adapter.SelectorBallTeamAdapter;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyBallTeamBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.SelectorBallTeamContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.SelectorBallTeamPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/5 14:31
 * 更新时间: 2019/5/5 14:31
 * 描述:选择球队Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM)
public class SelectorBallTeamActivity extends BaseActivity<SelectorBallTeamPresenter> implements SelectorBallTeamContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.tv_sures)
    TextView mTvSure;
    @BindView(R.id.rl_bottom)
    View mRlBottom;
    private SelectorBallTeamAdapter mAdapter;
    private List<ResultMyBallTeamBean> mData;
    private ResultMyBallTeamBean mMyBallTeamBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selector_ball_team;
    }

    @Override
    protected void initView() {
        mMyBallTeamBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mMyBallTeamBean != null) {
            mRlBottom.setVisibility(View.VISIBLE);
        }
        mRvData.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new SelectorBallTeamAdapter(mData);
        mRvData.setAdapter(mAdapter);
        mPresenter.start();
    }

    @Override
    protected void initEvent() {
        mTvSure.setOnClickListener(v -> {
            if (mAdapter.getCheckTeam() != null) {
                Intent intent = new Intent();
                intent.putExtra(ARouterConfig.Key.PARCELABLE, mAdapter.getCheckTeam());
                setResult(RESULT_OK, intent);
            }
            finish();
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
                mRlBottom.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected SelectorBallTeamPresenter createPresenter() {
        return new SelectorBallTeamPresenter(this);
    }


    @Override
    public void resultMyBallTeam(List<ResultMyBallTeamBean> data) {
        mData.clear();
        Iterator<ResultMyBallTeamBean> iterator = data.iterator();
        while (iterator.hasNext()) {
            ResultMyBallTeamBean bean = iterator.next();
            if (bean.isLeader != 1) {
                iterator.remove();
            }
        }
        mData.addAll(data);
        for (ResultMyBallTeamBean bean : mData) {
            if (mMyBallTeamBean != null && bean.teamId == mMyBallTeamBean.teamId) {
                bean.isCheck = true;
            }
        }
        mAdapter.notifyItemRangeChanged(0, mData.size());

    }


}
