package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.PostEvaluationAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.PostEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.PostEvaluationPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/29 16:36
 * 更新时间: 2019/5/29 16:36
 * 描述:评价fragment（裁判、队友、对手）
 */
@Route(path = ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION)
public class PostAllEvaluationFragment extends DelayedFragment<PostEvaluationPresenter> implements PostEvaluationContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mFlag;
    private long mId;
    private PostEvaluationAdapter mAdapter;
    private List<ResultInputEvaluationBean> mData;

    @Override
    protected void initPermission() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_evaluation);
            DataUtils.checkData(getActivity()).finish();
            return;
        }
        mFlag = bundle.getInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, -1);
        mId = bundle.getLong(ARouterConfig.Key.ID, -1);
        if (mFlag == -1 || mId == -1) {
            UIUtils.showToast(R.string.not_find_evaluation);
            DataUtils.checkData(getActivity()).finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initDelayedView() {

    }
    public void refresh(){
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initDelayedData() {
        if (mFlag != Contast.InputEvaluationType.TEAMMATE) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected void initView() {
        super.initView();
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        mAdapter = new PostEvaluationAdapter(mData, mFlag);
        mRvData.setAdapter(mAdapter);
        if (mFlag == Contast.InputEvaluationType.TEAMMATE) {
            LogUtils.w("initData--","我刷新了");
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSrlRefresh.setOnRefreshListener(refreshLayout -> {
            loadData();
        });
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        switch (mFlag) {
            case Contast.InputEvaluationType.REFEREE:
                mPresenter.loadRefereeEvaluation(mId);
                break;
            case Contast.InputEvaluationType.OPPONENT:
            case Contast.InputEvaluationType.TEAMMATE:
                LogUtils.w("initData---","我加载数据");
                mPresenter.loadTeamEvaluation(mId);
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post_all_evaluation;
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
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }


}
