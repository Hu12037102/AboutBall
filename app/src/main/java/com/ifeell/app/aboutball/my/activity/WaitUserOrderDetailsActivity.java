package com.ifeell.app.aboutball.my.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.my.contract.WaitUserOrderDetailsContract;
import com.ifeell.app.aboutball.my.presenter.WaitUserOrderDetailsPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.DateUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.activity.BaseOrderActivity;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.weight.HintDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 16:48
 * 更新时间: 2019/5/15 16:48
 * 描述:等待使用订单详情
 */
@Route(path = ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS)
public class WaitUserOrderDetailsActivity extends BaseOrderActivity<WaitUserOrderDetailsPresenter> implements
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
    @BindView(R.id.iv_address)
    ImageView mIvAddress;
    @BindView(R.id.tv_bottom_left)
    TextView mTvLeft;
    private long mOrderId;
    private HintDialog mCancelOrderDialog;
    private ResultOrderDetailsBean mResultBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_user_order_details;
    }

    @Override
    protected void initView() {
        mTvOrderStatus.setVisibility(View.GONE);
        mLlBottomRoot.setVisibility(View.GONE);
        mOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (mOrderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mTvStatus.setTextColor(ContextCompat.getColor(this, R.color.color_2));
        mPresenter.loadOrderDetails(mOrderId);
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
        this.mResultBean = bean;
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
        mLlBottomRoot.setVisibility(View.VISIBLE);
        if (bean.orderType == Contast.OrderStatus.ORDER_STATUS_NOT_CANCEL){
            mTvLeft.setVisibility(View.GONE);
        }else {
            mTvLeft.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right, R.id.iv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //orderType==2的时候不能取消订单
            case R.id.tv_bottom_left:
                if (Contast.OrderStatus.ORDER_STATUS_NOT_CANCEL != mResultBean.orderType) {
                    clickCancelOrder();
                } else {
                    UIUtils.showToast(R.string.this_order_not_cancel);
                }
                break;
            case R.id.tv_bottom_right:
                mPresenter.sureUserOrder(mOrderId);
                break;
            case R.id.iv_address:
                mViewModel.startActivityToMap(mResultBean.longitude, mResultBean.latitude, mResultBean.address);
                break;
        }
    }

    /**
     * 确认使用变成待评价状态
     *
     * @param orderId
     */
    @Override
    public void resultSureUseOrder(long orderId) {
        mViewModel.startActivityToOrderEvaluate(orderId, Contast.OrderStatus.WAIT_EVALUATE, null);
    }


    private void clickCancelOrder() {
        // EventBus.getDefault().post(new WaitUserOrderDetailsActivity.ResultPayBean(true));
        mViewModel.toRefundActivityToResult(mResultBean.stadiumName,
                DataUtils.getNotNullData(mResultBean.orderTime).concat("（").concat(DateUtils.getWeek(mResultBean.orderTime)).concat("）"),
                getSiteContent(mResultBean.orderDetailForOrders), mOrderId, DataUtils.getMoneyFormat(mResultBean.totalPrice), null);
      /*  mViewModel.showCancelOrderDialog(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {

              //  mPresenter.cancelOrder(mOrderId);
            }

            @Override
            public void onClickCancel(@NonNull View view) {
            }
        });*/
    }

    private String getSiteContent(List<ResultOrderDetailsBean.OrderPeopleCountBean> data) {
        String content = "";
        if (data == null || data.size() == 0) {
            return content;
        }
        for (int i = 0; i < data.size(); i++) {
            ResultOrderDetailsBean.OrderPeopleCountBean bean = data.get(i);
            content = content.concat(bean.areaName).concat(" ").concat(bean.calendar).concat(" ")
                    .concat(DataUtils.getMoneyFormat(bean.price)).concat(i == data.size() - 1 ? "" : "\n");
        }
        return content;
    }


    public static class ResultPayBean {
        public boolean isUpdateResult;

        public ResultPayBean(boolean isUpdateResult) {
            this.isUpdateResult = isUpdateResult;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    EventBus.getDefault().post(new WaitUserOrderDetailsActivity.ResultPayBean(true));
                    finish();
                    break;
                case Contast.OrderStatus.WAIT_EVALUATE:
                    clickBackForResult();
                    break;
            }
        }
     /*   if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            EventBus.getDefault().post(new WaitUserOrderDetailsActivity.ResultPayBean(true));
            finish();
        }*/
    }
}
