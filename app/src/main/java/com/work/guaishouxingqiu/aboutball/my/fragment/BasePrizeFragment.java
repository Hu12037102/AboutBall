package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.BasePrizeAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BasePrizeContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.BasePrizePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 16:58
 * 更新时间: 2019/4/11 16:58
 * 描述:呆兑换fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_BASE_PRIZE)
public class BasePrizeFragment extends BaseFragment<BasePrizePresenter> implements BasePrizeContract.View {
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.vs_rule)
    ViewStub mVsHint;
    private int mStatus;
    private List<ResultPrizeBean.DataBean> mData;
    private BasePrizeAdapter mAdapter;
    private View inflateView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_prize;
    }

    @Override
    protected void initView() {
        mSrlRefresh.setEnableLoadMore(false);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mStatus = mBundle.getInt(ARouterConfig.Key.KEY_STATUS, 0);
        initHeadView();
    }

    private void initHeadView() {
        inflateView = mVsHint.inflate();
        TextView tvRule = inflateView.findViewById(R.id.tv_rule);
        ImageView ivClose = inflateView.findViewById(R.id.iv_close);
        tvRule.setText(R.string.line_prize_input_address);
        inflateView.setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS));
        ivClose.setOnClickListener(v -> inflateView.setVisibility(View.GONE));
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new BasePrizeAdapter(mData, mStatus);
        mRvData.setAdapter(mAdapter);
        mSrlRefresh.autoRefresh();
    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(refreshLayout, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData(refreshLayout, true);
            }
        });
    }

    private void loadData(RefreshLayout refreshLayout, boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.loadMyPrize(mStatus);
    }

    @Override
    protected BasePrizePresenter createPresenter() {
        return new BasePrizePresenter(this);
    }

    @Override
    public void resultMyPrize(ResultPrizeBean data) {
        mData.addAll(data.prizeForSimples);
        mAdapter.notifyDataSetChanged();

        if (data.hasAddress == 1) {
            inflateView.setVisibility(View.GONE);
        } else {
            inflateView.setVisibility(View.VISIBLE);
        }
    }


}
