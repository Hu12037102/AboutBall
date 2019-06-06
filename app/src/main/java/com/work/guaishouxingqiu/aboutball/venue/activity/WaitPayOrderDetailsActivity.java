package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.activity.BasePayActivity;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.WaitPayOrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.WaitPayOrderDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.InputDialog;
import com.work.guaishouxingqiu.aboutball.weight.PayDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 13:08
 * 更新时间: 2019/4/22 13:08
 * 描述:订单详情
 */
@Route(path = ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS)
public class WaitPayOrderDetailsActivity extends BasePayActivity<WaitPayOrderDetailsPresenter>
        implements WaitPayOrderDetailsContract.View {
    @BindView(R.id.tv_agreement)
    TextView mTvAgreement;
    @BindView(R.id.tv_pay)
    TextView mTvPay;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_order_content)
    TextView mTvOrderContent;
    @BindView(R.id.tv_project_content)
    TextView mTvProjectContent;
    @BindView(R.id.tv_date_content)
    TextView mTvDateContent;
    @BindView(R.id.tv_address_content)
    TextView mTvAddressContent;
    @BindView(R.id.tv_phone_content)
    TextView mTvPhoneContent;
    @BindView(R.id.tv_pay_content)
    TextView mTvPayContent;
    @BindView(R.id.tv_original_content)
    TextView mTvOriginalContent;
    @BindView(R.id.tv_practical_content)
    TextView mTvPracticalContent;
    @BindView(R.id.tv_status_content)
    TextView mTvStatusContent;
    @BindView(R.id.ll_top)
    LinearLayout mLlTop;
    @BindView(R.id.tv_top_hint)
    TextView mTvTopHint;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.iv_address)
    ImageView mIvAddress;
    private ResultOrderDetailsBean mResultBean;
    private boolean mIsCheckAgreement;
    private long mOrderId;
    private PayDialog mPayDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_order_details;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
            return;
        }
        mOrderId = bundle.getLong(ARouterConfig.Key.ORDER_ID, -1);
        //0：我的订单页面；1：包场订单；2：发起约球订单；3：待约球订单
        int orderFlag = bundle.getInt(ARouterConfig.Key.ORDER_FLAG, -1);
        if (mOrderId == -1 || orderFlag == -1) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
            return;
        }

        switch (orderFlag) {
            case Contast.PayOrderFlag.PAY_MY_ORDER:
                mTvTopHint.setText(R.string.please_check_the_information_carefully);
                break;
            case Contast.PayOrderFlag.PAY_VENUE_BOOK:
                mTvTopHint.setText(R.string.please_add_up_to_order);
                break;
            case Contast.PayOrderFlag.PAY_LAUNCHER_ORDER:
                mLlTop.setVisibility(View.VISIBLE);
                break;
            case Contast.PayOrderFlag.PAY_WAIT_ORDER:
                mLlTop.setVisibility(View.VISIBLE);
                break;
        }
        mTvPay.setVisibility(View.GONE);
        mPresenter.loadOrderDetails(mOrderId);
    }

    @Override
    protected void initData() {
        String agreement = mTvAgreement.getText().toString();
        mTvAgreement.setText(SpanUtils.getClickText(mTvAgreement, R.color.color_2, 5, agreement.length(), view -> {
            view.setEnabled(false);
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_USER_AGREEMENT);
            view.setEnabled(true);
        }));
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                mViewModel.clickBackForResult();
            }
        });
    }

    @Override
    protected WaitPayOrderDetailsPresenter createPresenter() {
        return new WaitPayOrderDetailsPresenter(this);
    }

    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {
        mTvPay.setVisibility(View.VISIBLE);
        mIvAddress.setVisibility(View.VISIBLE);
        this.mResultBean = bean;
        mTvTitle.setText(bean.stadiumName);
        mTvAddress.setText(bean.address);
        List<ResultOrderDetailsBean.OrderPeopleCountBean> countData = bean.orderDetailForOrders;
        if (countData != null && countData.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < countData.size(); i++) {
                ResultOrderDetailsBean.OrderPeopleCountBean countBean = countData.get(i);
                sb.append(countBean.areaName).append("  ").append(countBean.calendar).append("  ¥").append(String.valueOf(countBean.price)).append(i == countData.size() - 1 ? "" : "\n");
            }
            mTvAddressContent.setText(sb.toString());
        }
        mTvPhoneContent.setText(DataUtils.isEmpty(bean.phoneNum) ? UserManger.get().getPhone() : bean.phoneNum);
        mTvPayContent.setText("微信支付");
        mTvOriginalContent.setText("¥" + bean.totalPrice);
        mTvPracticalContent.setText("¥" + bean.realPrice);
        mTvStatusContent.setText(bean.stateName);
        mTvDateContent.setText(bean.orderTime);
        mTvPay.setText(UIUtils.getString(R.string.add_up_to_money_please_pay, bean.totalPrice));

    }

    @Override
    public void resultBandPhoneNumber() {
        if (mPayDialog == null) {
            mPayDialog = new PayDialog(this)
                    .setMoney(DataUtils.getMoneyFormat(mResultBean.totalPrice));
        }

        if (!mPayDialog.isShowing()) {
            mPayDialog.show();
        }
        mPayDialog.setOnPayDialogClickListener(new PayDialog.OnPayDialogClickListener() {
            @Override
            public void onWeiChat(@NonNull View view) {
                mPresenter.payWeiChatSing(mOrderId);
            }
        });
      /*  mPayDialog.setOnClickPayListener(v -> {
            mPayDialog.dismiss();
            //调起微信支付
        });*/

    }


    @OnClick({R.id.tv_agreement, R.id.tv_pay, R.id.iv_address, R.id.iv_phone_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                clickAgreement();
                break;
            case R.id.tv_pay:
                clickPay();
                break;
            case R.id.iv_phone_content:
                clickContentPhone();
                break;
            case R.id.iv_address:
                mViewModel.startActivityToMap(mResultBean.longitude, mResultBean.latitude, mResultBean.address);
                break;
        }
    }

    /**
     * 先绑定手机号，再去支付
     */
    private void clickPay() {
        if (mIsCheckAgreement) {
            if (DataUtils.isEmpty(mTvPhoneContent.getText())) {
                showBandPhoneNumberDialog();
            } else {
                mPresenter.bandOrderPhoneNumber(mOrderId, mTvPhoneContent.getText().toString());
            }
        } else {
            UIUtils.showToast(R.string.please_sure_and_read_agreement);
        }

    }

    private void clickAgreement() {
        mIsCheckAgreement = !mIsCheckAgreement;
        mTvAgreement.setCompoundDrawablesWithIntrinsicBounds(mIsCheckAgreement ? R.mipmap.icon_square_check :
                R.mipmap.icon_square_default, 0, 0, 0);
    }

    private void clickContentPhone() {
        showBandPhoneNumberDialog();
    }

    private void showBandPhoneNumberDialog() {
        InputDialog inputDialog = new InputDialog.Build(this)
                .setTitle(R.string.update_phone_number)
                .setContentHint(mTvPhoneContent.getText() == null ? R.string.band_phone_number : R.string.please_input_you_phone_number)
                .setContent(mTvPhoneContent.getText() == null ? null : mTvPhoneContent.getText().toString())
                .setInputType(InputType.TYPE_CLASS_PHONE)
                .setContentLength(11)
                .build();
        inputDialog.show();
        inputDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                if (DataUtils.isPhoneNumber(inputDialog.getContent())) {
                    inputDialog.dismiss();
                    mTvPhoneContent.setText(inputDialog.getContent());
                } else {
                    UIUtils.showToast(R.string.please_input_sure_phone_number);
                }

            }

            @Override
            public void onClickCancel(@NonNull View view) {
                inputDialog.dismiss();
            }
        });
    }

    @Override
    public long getOrderId() {
        return mOrderId;
    }

    @Override
    public void onBackPressed() {
        mViewModel.clickBackForResult();
    }
}
