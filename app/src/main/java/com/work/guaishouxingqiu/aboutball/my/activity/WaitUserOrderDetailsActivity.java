package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.WaitUserOrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.WaitUserOrderDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 16:48
 * 更新时间: 2019/5/15 16:48
 * 描述:等待使用订单详情
 */
@Route(path = ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS)
public class WaitUserOrderDetailsActivity extends BaseActivity<WaitUserOrderDetailsPresenter> implements
        WaitUserOrderDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.ll_bottom_root)
    LinearLayout mLlBottomRoot;
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
    @BindView(R.id.tv_pay_way)
    TextView mTvPayWay;
    @BindView(R.id.tv_original_price)
    TextView mTvOriginalPrice;
    @BindView(R.id.tv_practical_price)
    TextView mTvPracticalPrice;
    @BindView(R.id.tv_order_status)
    TextView mTvOrderStatus;
    @BindView(R.id.tv_phone)
    TextView mTvPhoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_user_order_details;
    }

    @Override
    protected void initView() {
        long orderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (orderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mTvStatus.setTextColor(ContextCompat.getColor(this, R.color.color_2));
        mPresenter.loadOrderDetails(orderId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected WaitUserOrderDetailsPresenter createPresenter() {
        return new WaitUserOrderDetailsPresenter(this);
    }

    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {
        UIUtils.setText(mTvName, bean.stadiumName);
        UIUtils.setText(mTvAddress, bean.address);
        UIUtils.setText(mTvStatus, bean.stateName);
        UIUtils.setOrderDetailsItemSpan(mTvTime, UIUtils.getString(R.string.order_item_time_host), DataUtils.getNotNullData(bean.orderTime).concat("（").concat(DateUtils.getWeek(bean.orderTime)).concat("）"));
        UIUtils.setText(mTvReserveContent, DataUtils.getOrderSiteContent(bean.orderDetailForOrders));
        UIUtils.setOrderDetailsItemSpan(mTvPhoneNumber, UIUtils.getString(R.string.due_to_phone), bean.phoneNum);
        UIUtils.setOrderDetailsItemSpan(mTvPayWay, UIUtils.getString(R.string.pay_way), UIUtils.getString(R.string.weichat_pay));
        UIUtils.setOrderDetailsItemSpan(mTvOriginalPrice, UIUtils.getString(R.string.order_original), bean.realPrice > 0 ? "￥" + bean.realPrice : "￥" + bean.totalPrice);
        UIUtils.setOrderDetailsItemSpan(mTvPracticalPrice, UIUtils.getString(R.string.order_practical), "￥" + bean.totalPrice);
        UIUtils.setOrderDetailsItemSpan(mTvOrderStatus, UIUtils.getString(R.string.order_status), UIUtils.getString(R.string.paying));

    }

    @Override
    public void resultBandPhoneNumber() {

    }


    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right, R.id.iv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_left:
                break;
            case R.id.tv_bottom_right:
                break;
            case R.id.iv_address:
                break;
        }
    }
}
