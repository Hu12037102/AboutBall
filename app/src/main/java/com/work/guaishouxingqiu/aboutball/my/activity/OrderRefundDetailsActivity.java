package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.adapter.OrderRefundDetailsAdapter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderRefundDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.OrderRefundDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/24 10:17
 * 更新时间: 2019/5/24 10:17
 * 描述:退款进度详情activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_REFUND_DETAILS)
public class OrderRefundDetailsActivity extends BaseActivity<OrderRefundDetailsPresenter>
        implements OrderRefundDetailsContract.View {
    @BindView(R.id.rv_schedule)
    RecyclerView mRvSchedule;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_order_time)
    TextView mTvOrderTime;
    @BindView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.tv_total_prices)
    TextView mTvTotalPrices;
    @BindView(R.id.tv_refund_cause)
    TextView mTvRefundCause;
    @BindView(R.id.tv_practical_prices)
    TextView mTvPracticalPrices;
    private List<ResultRefundDetailsBean.RefundDetailList> mData;
    private OrderRefundDetailsAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_refund_details;
    }

    @Override
    protected void initView() {
      long orderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID,-1);
        if (orderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mRvSchedule.setLayoutManager(new LinearLayoutManager(this));



        mPresenter.checkRefundDetails(orderId);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new OrderRefundDetailsAdapter(this, mData);
        mRvSchedule.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected OrderRefundDetailsPresenter createPresenter() {
        return new OrderRefundDetailsPresenter(this);
    }

    @Override
    public void resultRefundDetails(ResultRefundDetailsBean bean) {
        UIUtils.setOrderDetailsItemSpan(mTvRefundCause, UIUtils.getString(R.string.refund_cause), bean.refundReason);
        UIUtils.setOrderDetailsItemSpan(mTvOrderNumber, UIUtils.getString(R.string.order_host), bean.orderNo);
        UIUtils.setOrderDetailsItemSpan(mTvPhoneNumber, UIUtils.getString(R.string.phone_number_host), bean.telephone);
        UIUtils.setOrderDetailsItemSpan(mTvOrderTime, UIUtils.getString(R.string.order_time_host), bean.orderTime);
        UIUtils.setOrderDetailsItemSpan(mTvPayTime, UIUtils.getString(R.string.pay_order_time_host), bean.payTime);
        UIUtils.setOrderDetailsItemSpan(mTvTotalPrices, UIUtils.getString(R.string.order_total_prices_host), "￥" + bean.totalPrice);
        UIUtils.setOrderDetailsItemSpan(mTvPracticalPrices, UIUtils.getString(R.string.order_practical_prices_host), bean.realPrice > 0 ? "￥" + bean.realPrice : "￥" + bean.totalPrice);
        if (bean.orderRefundDetailList != null) {
            if (mData.size() > 0) {
                mData.clear();
            }
            mData.addAll(bean.orderRefundDetailList);
            mAdapter.notifyDataSetChanged();
        }
    }

}
