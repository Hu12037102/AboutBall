package com.work.guaishouxingqiu.aboutball.my.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.fragment.BasePayFragment;
import com.work.guaishouxingqiu.aboutball.my.activity.WaitUserOrderDetailsActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.MyOrderAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderFragmentContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyOrderFragmentPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.WaitPayOrderDetailsActivity;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.PayDialog;

import org.greenrobot.eventbus.Subscribe;

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
public class MyOrderFragment extends BasePayFragment<MyOrderFragmentPresenter> implements MyOrderFragmentContract.View {

    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private int mOrderStatus;
    private List<ResultMyOrderBean> mData;
    private MyOrderAdapter mAdapter;
    private PayDialog mPayDialog;
    private long mPayOrderId = -1;

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
    public void onStart() {
        super.onStart();

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
        initAdapter();
        if (mOrderStatus == Contast.ORDER_STATUS.ALL) {
            mSrlRefresh.autoRefresh();
        }
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
                        mViewModel.startActivityForResultToOrderPay(bean.orderId, Contast.PAY_ORDER_FLAG.PAY_MY_ORDER, MyOrderFragment.this, Contast.ORDER_STATUS.WAIT_PAY);
                        break;
                    //已取消
                    case Contast.ORDER_STATUS.CANCELED:
                        toCompleteOrCancelActivity(bean.orderId, bean.stateId);
                        break;
                    //待使用
                    case Contast.ORDER_STATUS.WAIT_USER:
                         ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS, ARouterConfig.Key.ORDER_ID, bean.orderId);
                       /* ARouterIntent.startActivityForResult(MyOrderFragment.this, WaitUserOrderDetailsActivity.class,
                                ARouterConfig.Key.ORDER_ID, bean.orderId, Contast.ORDER_STATUS.WAIT_USER);*/
                        break;
                    //待评价
                    case Contast.ORDER_STATUS.WAIT_EVALUATE:
                        toCompleteOrCancelActivity(bean.orderId, bean.stateId);
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
                        toCompleteOrCancelActivity(bean.orderId, bean.stateId);
                        break;
                    default:
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListenerTv1(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                ResultMyOrderBean bean = mData.get(position);
                switch (bean.stateId) {
                    //待付款
                    case Contast.ORDER_STATUS.WAIT_PAY:
                        mPayOrderId = bean.orderId;
                        clickPay(bean.totalPrice, bean.orderId);
                        break;
                    //已取消
                    case Contast.ORDER_STATUS.CANCELED:
                        break;
                    //待使用
                    case Contast.ORDER_STATUS.WAIT_USER:

                        break;
                    //待评价
                    case Contast.ORDER_STATUS.WAIT_EVALUATE:
                        String orderTime = DataUtils.getNotNullData(bean.orderTime).concat("（").concat(DateUtils.getWeek(bean.orderTime)).concat("）");
                        mViewModel.startActivityToEvaluate(bean.stadiumName, orderTime,
                                getOrderSiteContent(bean.orderDetailForOrders), bean.orderId, Contast.ORDER_STATUS.WAIT_EVALUATE, false, MyOrderFragment.this);
                        break;
                    //已完成
                    case Contast.ORDER_STATUS.COMPLETING:
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
        mAdapter.setOnItemClickListenerTv2((view, position) -> {
            ResultMyOrderBean bean = mData.get(position);
            if (bean.stateId == Contast.ORDER_STATUS.WAIT_USER) {
                clickCancelOrder(bean.orderId);
            }
        });
    }

    private void clickPay(double money, long orderId) {
        if (mPayDialog == null) {
            mPayDialog = new PayDialog(mContext);

        }
        mPayDialog.setMoney(DataUtils.getMoneyFormat(money));
        if (!mPayDialog.isShowing()) {
            mPayDialog.show();
        }
        mPayDialog.setOnPayDialogClickListener(new PayDialog.OnPayDialogClickListener() {
            @Override
            public void onWeiChat(@NonNull View view) {
                mPresenter.payWeiChatSing(orderId);
            }
        });
    }

    private void clickCancelOrder(long orderId) {
        mViewModel.showCancelOrderDialog(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mPresenter.cancelOrder(orderId);
            }

            @Override
            public void onClickCancel(@NonNull View view) {

            }
        });
    }

    public static String getOrderSiteContent(List<ResultMyOrderBean.DetailsOrder> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            ResultMyOrderBean.DetailsOrder bean = data.get(i);
            sb = sb.append(bean.areaName).append("  ").append(bean.calendar).append("  ").append("￥").append(bean.price).append(i == data.size() - 1 ? "" : "\n");
        }
        return sb.toString();
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
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ORDER_COMPLETE_EVALUATE_CANCEL, bundle);
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
        mSrlRefresh.setNoMoreData(data.size() < mPresenter.mPageSize);
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resultCancelOrderSucceed() {
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Contast.ORDER_STATUS.WAIT_EVALUATE:
                case Contast.ORDER_STATUS.WAIT_PAY:
                    mSrlRefresh.autoRefresh();
                    break;
                default:
                    break;
            }
        }

    }



    @Override
    public void paySucceed() {
        super.paySucceed();
        if (mPayOrderId == -1)
            return;
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_PAY_SUCCEED, ARouterConfig.Key.ORDER_ID, mPayOrderId);
        mSrlRefresh.autoRefresh();
    }
    @Subscribe
    public void resultCancelOrder(WaitUserOrderDetailsActivity.ResultPayBean bean){
        if (bean!= null && bean.isUpdateResult){
            mSrlRefresh.autoRefresh();
        }
    }
}
