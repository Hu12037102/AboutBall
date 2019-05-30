package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.PostEvaluationAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.PostEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.PostEvaluationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/29 16:36
 * 更新时间: 2019/5/29 16:36
 * 描述:评价fragment（裁判）
 */
@Route(path = ARouterConfig.Path.FRAGMENT_POST_EVALUATION)
public class PostEvaluationFragment extends DelayedFragment<PostEvaluationPresenter> implements PostEvaluationContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private long mRefereeId;
    private int mFlag;
    private List<ResultInputEvaluationBean> mData;
    private PostEvaluationAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_evaluation;
    }

    /**
     * 根据flag判断是别的裁判记录还是自己的裁判记录
     */
    @Override
    protected void initPermission() {
        mFlag = mBundle.getInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, -1);
        mRefereeId = mBundle.getLong(ARouterConfig.Key.REFEREE_ID, -1);
        if (mFlag == -1 || (mFlag == Contast.InputEvaluationType.REFEREE && mRefereeId == -1)) {
            UIUtils.showToast(R.string.not_find_this_referee);
            DataUtils.checkData(getActivity()).finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initDelayedView() {
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initDelayedData() {
        mData = new ArrayList<>();
        mAdapter = new PostEvaluationAdapter(mData, mFlag);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            if (mFlag == Contast.InputEvaluationType.REFEREE) {
                mPresenter.loadRefereeEvaluation(mRefereeId);
            } else {
                mPresenter.loadMyRefereeEvaluation();
            }
            refreshLayout.finishRefresh();
        });
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
    protected PostEvaluationPresenter createPresenter() {
        return new PostEvaluationPresenter(this);
    }

    @Override
    public void resultEvaluation(List<ResultInputEvaluationBean> data) {
        if (mData.size() > 0) {
            mData.clear();
        }
        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        mSrlRefresh.setNoMoreData(true);
        mSrlRefresh.setEnableLoadMore(false);
        mAdapter.notifyDataSetChanged();
    }

}
