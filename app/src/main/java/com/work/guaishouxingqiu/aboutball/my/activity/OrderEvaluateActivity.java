package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.OrderEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.OrderEvaluatePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 13:11
 * 更新时间: 2019/5/15 13:11
 * 描述:订单评价Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_EVALUATE)
public class OrderEvaluateActivity extends BaseActivity<OrderEvaluatePresenter> implements OrderEvaluateContract.View {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_reserve_content)
    TextView mTvReserveContent;
    @BindView(R.id.rb_grade)
    RatingBar mRbGrade;
    @BindView(R.id.acet_content)
    AppCompatEditText mAcetContent;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        String venueName = bundle.getString(ARouterConfig.Key.VENUE_NAME);
        UIUtils.setText(mTvName,venueName);
        String orderTime = bundle.getString(ARouterConfig.Key.TARGET_DATE);
        UIUtils.setOrderDetailsItemSpan(mTvTime,UIUtils.getString(R.string.order_item_time_host),orderTime);
        String siteContent = bundle.getString(ARouterConfig.Key.TARGET_SITE);
        UIUtils.setText(mTvReserveContent, siteContent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected OrderEvaluatePresenter createPresenter() {
        return new OrderEvaluatePresenter(this);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
    }
}
