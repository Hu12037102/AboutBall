package com.ifeell.app.aboutball.my.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.my.adapter.PostEvaluationAdapter;
import com.ifeell.app.aboutball.my.bean.ResultInputEvaluationBean;
import com.ifeell.app.aboutball.my.contract.PostEvaluationContract;
import com.ifeell.app.aboutball.my.presenter.PostEvaluationPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
