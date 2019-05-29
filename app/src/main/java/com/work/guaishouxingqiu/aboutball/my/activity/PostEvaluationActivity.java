package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.PostEvaluationAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.PostEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.PostEvaluationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 11:45
 * 更新时间: 2019/5/27 11:45
 * 描述:发布评论Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_POST_EVALUATION)
public class PostEvaluationActivity extends BaseActivity<PostEvaluationPresenter> implements PostEvaluationContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private PostEvaluationAdapter mAdapter;
    private List<ResultInputEvaluationBean> mData;
    private long mRefereeId;
    private long mTeamId;
    private int mFlag;
    private long mAgreeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_evaluation;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        mFlag = bundle.getInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, -1);
        mRefereeId = bundle.getLong(ARouterConfig.Key.REFEREE_ID, -1);
        mTeamId = bundle.getLong(ARouterConfig.Key.TEAM_ID, -1);
        mAgreeId = bundle.getLong(ARouterConfig.Key.AGREE_ID, -1);
        if (mFlag == -1) {
            UIUtils.showToast(R.string.not_find_evaluation);
            finish();
            return;
        }
        switch (mFlag) {
            case Contast.InputEvaluationType.REFEREE:
                mTvCommit.setVisibility(View.VISIBLE);
                mTvCommit.setText(R.string.please_post_evaluation);
                UIUtils.setText(mTitleView.mTvCenter, R.string.referee_evaluation);
                break;
            case Contast.InputEvaluationType.OPPONENT:
                mTvCommit.setVisibility(View.VISIBLE);
                mTvCommit.setText(R.string.please_post_evaluation);
                UIUtils.setText(mTitleView.mTvCenter, R.string.opponent_evaluation);
                break;
            case Contast.InputEvaluationType.TEAMMATE:
                mTvCommit.setVisibility(View.GONE);
                UIUtils.setText(mTitleView.mTvCenter, R.string.team_evaluation);
                break;
        }

        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mAdapter = new PostEvaluationAdapter(mData, mFlag);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh();
            switch (mFlag) {
                case Contast.InputEvaluationType.REFEREE:
                    mPresenter.loadRefereeEvaluation(mRefereeId);
                    break;
                case Contast.InputEvaluationType.OPPONENT:
                case Contast.InputEvaluationType.TEAMMATE:
                    mPresenter.loadTeamEvaluation(mTeamId);
                    break;
            }
        });
    }

    @Override
    protected PostEvaluationPresenter createPresenter() {
        return new PostEvaluationPresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, mFlag);
        bundle.putLong(ARouterConfig.Key.AGREE_ID,mAgreeId);
        if (mFlag == Contast.InputEvaluationType.REFEREE) {
            bundle.putLong(ARouterConfig.Key.REFEREE_ID, mRefereeId);
        } else if (mFlag == Contast.InputEvaluationType.OPPONENT) {
            bundle.putLong(ARouterConfig.Key.TEAM_ID, mTeamId);
        }
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_INPUT_EVALUATION, this, bundle);
    }

    @Override
    public void resultEvaluation(List<ResultInputEvaluationBean> data) {
        if (mData.size() > 0) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == RESULT_OK) {
            mSrlRefresh.autoRefresh();
        }
    }
}