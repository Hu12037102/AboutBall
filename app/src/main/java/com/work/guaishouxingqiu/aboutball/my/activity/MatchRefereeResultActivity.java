package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MatchRefereeResultPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:39
 * 更新时间: 2019/6/4 13:39
 * 描述:赛况记录Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MATCH_REFEREE_RESULT)
public class MatchRefereeResultActivity extends BaseActivity<MatchRefereeResultPresenter> implements
        MatchRefereeResultContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.item_host_case)
    ItemView mItemHostCase;
    @BindView(R.id.item_guest_case)
    ItemView mItemGuestCase;
    @BindView(R.id.ll_not_join)
    LinearLayout mLlNotJoin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_result;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MatchRefereeResultPresenter createPresenter() {
        return new MatchRefereeResultPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ADD_BALL_PEOPLE_RECORD);
    }
}
