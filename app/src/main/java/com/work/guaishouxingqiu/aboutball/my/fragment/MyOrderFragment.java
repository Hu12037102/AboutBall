package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.adapter.MyOrderAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderFragmentContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyOrderFragmentPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:31
 * 更新时间: 2019/5/14 10:31
 * 描述:
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_ORDER)
public class MyOrderFragment extends DelayedFragment<MyOrderFragmentPresenter> implements MyOrderFragmentContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mOrderStatus;
    private List<ResultMyOrderBean> mData;
    private MyOrderAdapter mAdapter;

    @Override
    protected void initDelayedView() {


    }

    @Override
    protected void initDelayedData() {
        if (mOrderStatus != Contast.ORDER_STATUS.ALL) {
            mSrlRefresh.autoRefresh();
        }
    }

    @Override
    protected void initDelayedEvent() {

    }

    @Override
    protected void initView() {
        mOrderStatus = mBundle.getInt(ARouterConfig.Key.ORDER_STATUS, -1);
        if (mOrderStatus == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            DataUtils.checkData(getActivity()).finish();
        }
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        if (mOrderStatus == Contast.ORDER_STATUS.ALL) {
            mSrlRefresh.autoRefresh();
        }
        initAdapter();
    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mAdapter = new MyOrderAdapter(mData);
        mRvData.setAdapter(mAdapter);
    }


    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadOrder(false, refreshLayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadOrder(true, refreshLayout);
            }
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
                ResultMyOrderBean bean = mData.get(position);
                switch (bean.stateId) {
                    //待付款
                    case Contast.ORDER_STATUS.WAIT_PAY:
                        mViewModel.startActivityToOrderPay(bean.orderId, Contast.PAY_ORDER_FLAG.PAY_MY_ORDER);
                        break;
                    //已取消
                    case Contast.ORDER_STATUS.CANCELED:
                        toCompleteOrCancelActivity(bean.orderId, bean.stateId);
                        break;
                    //待使用
                    case Contast.ORDER_STATUS.WAIT_USER:
                        break;
                    //待评价
                    case Contast.ORDER_STATUS.WAIT_EVALUATE:
                        break;
                    //已完成
                    case Contast.ORDER_STATUS.COMPLETING:
                        toCompleteOrCancelActivity(bean.orderId, bean.stateId);
                        break;
                    //退款中
                    case Contast.ORDER_STATUS.REFUNDING:
                        break;
                    //已退款
                    case Contast.ORDER_STATUS.REFUNDED:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 订单详情取消或者已完成
     *
     * @param orderId     订单id
     * @param orderStatus 订单状态
     */
    private void toCompleteOrCancelActivity(long orderId, int orderStatus) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_STATUS, orderStatus);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ORDER_COMPLETE_AND_CANCEL, bundle);
    }

    private void loadOrder(boolean isRefresh, RefreshLayout refreshLayout) {
        mPresenter.isRefresh = isRefresh;
        if (isRefresh) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadMore();
        }
        mPresenter.lordMyOrder(mOrderStatus);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected MyOrderFragmentPresenter createPresenter() {
        return new MyOrderFragmentPresenter(this);
    }


    @Override
    public void resultMyOrderData(List<ResultMyOrderBean> data) {
        if (mPresenter.isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyItemRangeChanged(0, mData.size());
    }
}
