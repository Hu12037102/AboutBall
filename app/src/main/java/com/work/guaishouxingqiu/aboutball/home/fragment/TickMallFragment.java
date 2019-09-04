package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.home.activity.TicketMallDetailsActivity;
import com.work.guaishouxingqiu.aboutball.home.adapter.TicketMallAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultTicketMallBean;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallChildContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.TicketMallChildPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/30 9:43
 * 更新时间: 2019/8/30 9:43
 * 描述:售票商城Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_TICK_MALL)
public class TickMallFragment extends DelayedFragment<TicketMallChildPresenter> implements TicketMallChildContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mStatusId;
    private TicketMallAdapter mAdapter;
    private List<ResultTicketMallBean> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tick_mall;
    }

    @Override
    protected void initDelayedView() {
        if (mStatusId != 0) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected void initPermission() {
        mStatusId = mBundle.getInt(ARouterConfig.Key.KEY_STATUS);
        if (mStatusId < 0) {
            UIUtils.showToast(R.string.not_find_tick_mall_message);
            getActivity().finish();
            return;
        }
        super.initPermission();
    }

    private void loadData(boolean isRefresh) {
        if (isRefresh) {
            mSrlRefresh.finishRefresh();
        } else {
            mSrlRefresh.finishLoadMore();
        }
        mPresenter.isRefresh = isRefresh;
        mPresenter.loadTickMallList(mStatusId);
    }

    @Override
    protected void initView() {
        mSrlRefresh.setEnableLoadMore(false);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        mAdapter = new TicketMallAdapter(mData);
        mRvData.setAdapter(mAdapter);
        if (mStatusId == 0) {
            mSrlRefresh.autoRefresh();
        }

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
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
                LogUtils.w("initEvent--", "我被点击了");
            }
        });
        mAdapter.setOnCardViewClickListener((view, position) -> {
            ARouterIntent.startActivityForResult(TickMallFragment.this, TicketMallDetailsActivity.class, ARouterConfig.Key.PARCELABLE, mData.get(position));
            LogUtils.w("initEvent---", "我被点击了");
        });
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
    }

    @Override
    protected TicketMallChildPresenter createPresenter() {
        return new TicketMallChildPresenter(this);
    }

    @Override
    public void resultTickMallData(List<ResultTicketMallBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        if (data.size() > 0) {
            mData.addAll(data);
        }
        mAdapter.notifyData(!(data.size() > 0));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mSrlRefresh.autoRefresh();
        }
    }
}
