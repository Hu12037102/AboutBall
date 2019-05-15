package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderCompleteEvaluateCancelContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.OrderCompleteEvaluateCancelPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 18:15
 * 更新时间: 2019/5/14 18:15
 * 描述:订单完成、取消、评价订单详情activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_COMPLETE_EVALUATE_CANCEL)
public class OrderCompleteEvaluateCancelActivity extends BaseActivity<OrderCompleteEvaluateCancelPresenter> implements
        OrderCompleteEvaluateCancelContract.View {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_reserve_content)
    TextView mTvReserveContent;
    @BindView(R.id.rb_grade)
    RatingBar mRbGrade;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.rl_grade)
    RelativeLayout mRlGrade;
    @BindView(R.id.line_bottom)
    View mLineBottom;
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
    @BindView(R.id.tv_practical_prices)
    TextView mTvPracticalPrices;
    @BindView(R.id.tv_judge)
    TextView mTvJudge;
    private ResultOrderDetailsBean mOrderDetailsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_complete_and_cancel;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        int orderStatus = bundle.getInt(ARouterConfig.Key.ORDER_STATUS, -1);
        long orderId = bundle.getLong(ARouterConfig.Key.ORDER_ID, -1);
        if (orderStatus == -1 || orderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLineBottom.getLayoutParams();
        if (orderStatus == Contast.ORDER_STATUS.COMPLETING) {
            mRlGrade.setVisibility(View.VISIBLE);
            mTvStatus.setTextColor(ContextCompat.getColor(this, R.color.color_2));
            mTvPayTime.setVisibility(View.VISIBLE);
            layoutParams.topMargin = 0;
        } else if (orderStatus == Contast.ORDER_STATUS.CANCELED) {
            mRlGrade.setVisibility(View.GONE);
            mTvStatus.setTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
            layoutParams.topMargin = ScreenUtils.dp2px(this, 20);
            mTvPayTime.setVisibility(View.GONE);
        } else if (orderStatus == Contast.ORDER_STATUS.WAIT_EVALUATE) {
            mTvStatus.setTextColor(ContextCompat.getColor(this, R.color.color_2));
            mTvPayTime.setVisibility(View.VISIBLE);
            mRlGrade.setVisibility(View.VISIBLE);
            layoutParams.topMargin = 0;
        } else {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mLineBottom.setLayoutParams(layoutParams);

        mPresenter.loadOrderDetails(orderId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected OrderCompleteEvaluateCancelPresenter createPresenter() {
        return new OrderCompleteEvaluateCancelPresenter(this);
    }

    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {
        this.mOrderDetailsBean = bean;
        if (bean.stateId == Contast.ORDER_STATUS.WAIT_EVALUATE) {
            mTvJudge.setVisibility(View.VISIBLE);
        } else {
            mTvJudge.setVisibility(View.GONE);
        }
        UIUtils.setText(mTvName, bean.stadiumName);
        UIUtils.setText(mTvAddress, bean.address);
        UIUtils.setText(mTvStatus, bean.stateName);
        UIUtils.setOrderDetailsItemSpan(mTvTime, UIUtils.getString(R.string.order_item_time_host), DataUtils.getNotNullData(mOrderDetailsBean.orderTime).concat("（").concat(DateUtils.getWeek(bean.orderTime)).concat("）"));
        UIUtils.setText(mTvReserveContent, DataUtils.getOrderSiteContent(bean.orderDetailForOrders));
        UIUtils.setOrderDetailsItemSpan(mTvOrderNumber, UIUtils.getString(R.string.order_host), bean.orderNo);
        UIUtils.setOrderDetailsItemSpan(mTvPhoneNumber, UIUtils.getString(R.string.phone_number_host), bean.phoneNum);
        UIUtils.setOrderDetailsItemSpan(mTvOrderTime, UIUtils.getString(R.string.order_time_host), bean.createOrderTime);
        UIUtils.setOrderDetailsItemSpan(mTvPayTime, UIUtils.getString(R.string.pay_order_time_host), bean.payTime);
        UIUtils.setOrderDetailsItemSpan(mTvTotalPrices, UIUtils.getString(R.string.order_total_prices_host), "￥" + bean.totalPrice);
        UIUtils.setOrderDetailsItemSpan(mTvPracticalPrices, UIUtils.getString(R.string.order_practical_prices_host), bean.realPrice > 0 ? "￥" + bean.realPrice : "￥" + bean.totalPrice);
    }

    @Override
    public void resultBandPhoneNumber() {

    }


    @OnClick({R.id.iv_address, R.id.tv_order_number, R.id.tv_phone_number, R.id.tv_judge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_address:
                break;
            case R.id.tv_order_number:
                break;
            case R.id.tv_phone_number:
                break;
            case R.id.tv_judge:
                clickJudge();
                break;
        }
    }

    private void clickJudge() {
        //ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ORDER_EVALUATE, ARouterConfig.Key.ORDER_DETAILS, mOrderDetailsBean);
        mViewModel.startActivityToEvaluate(mOrderDetailsBean.stadiumName,
                DataUtils.getNotNullData(mOrderDetailsBean.orderTime).concat("（").concat(DateUtils.getWeek(mOrderDetailsBean.orderTime)).concat("）"),
                DataUtils.getOrderSiteContent(mOrderDetailsBean.orderDetailForOrders));

    }
}
