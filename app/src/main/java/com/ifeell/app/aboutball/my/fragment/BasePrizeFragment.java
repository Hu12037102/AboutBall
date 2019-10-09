package com.ifeell.app.aboutball.my.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseFragment;
import com.ifeell.app.aboutball.my.adapter.BasePrizeAdapter;
import com.ifeell.app.aboutball.my.bean.ResultPrizeBean;
import com.ifeell.app.aboutball.my.contract.BasePrizeContract;
import com.ifeell.app.aboutball.my.presenter.BasePrizePresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    private int mStatus;
    private List<ResultPrizeBean.DataBean> mData;
    private BasePrizeAdapter mAdapter;

    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.rl_bottom)
    View mRlBottom;

    private boolean isFirstLoad = true;

    public void setOnHasAddressResult(OnHasAddressResult onHasAddressResult) {
        this.onHasAddressResult = onHasAddressResult;
    }

    private OnHasAddressResult onHasAddressResult;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_prize;
    }

    @Override
    protected void initView() {
        mSrlRefresh.setEnableLoadMore(false);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mStatus = mBundle.getInt(ARouterConfig.Key.KEY_STATUS, 0);
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
        mTvAddress.setOnClickListener(v -> {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS);
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
        mData.clear();
        mData.addAll(data.prizeForSimples);
        mAdapter.notifyDataSetChanged();
        if (isFirstLoad) {
            if (mStatus == Contast.PRIZE_WAIT) {
                mRlBottom.setVisibility(View.VISIBLE);
                mPresenter.start();
            } else {
                mRlBottom.setVisibility(View.GONE);
            }
            isFirstLoad = false;
        }

    }

    @Override
    public void resultHasAddress(int type) {
        if (type == 1) {
            mTvAddress.setText(R.string.modify_shipping_address);
        } else {
            mTvAddress.setText(R.string.add_shipping_address);
        }
        if (onHasAddressResult != null) {
            onHasAddressResult.onResult(type);
        }
    }

    public interface OnHasAddressResult {
        void onResult(int type);
    }

}
