package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueOrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenueOrderDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/22 13:08
 * 更新时间: 2019/4/22 13:08
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_VENUE_ORDER_DETAILS)
public class VenueOrderDetailsActivity extends BaseActivity<VenueOrderDetailsPresenter>
        implements VenueOrderDetailsContract.View {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_venue_order_details;
    }

    @Override
    protected void initView() {
        long mOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (mOrderId == -1) {
            UIUtils.showToast(R.string.this_order_not_exist);
            finish();
            return;
        }
        mPresenter.loadOrderDetails(mOrderId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected VenueOrderDetailsPresenter createPresenter() {
        return new VenueOrderDetailsPresenter(this);
    }

    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {

    }



    @OnClick({R.id.tv_agreement, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                break;
            case R.id.tv_pay:
                break;
        }
    }
}
