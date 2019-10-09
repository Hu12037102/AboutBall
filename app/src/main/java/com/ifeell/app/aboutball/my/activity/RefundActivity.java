package com.ifeell.app.aboutball.my.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.OnItemClickListener;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.adapter.RefundCauseAdapter;
import com.ifeell.app.aboutball.my.bean.RequestRefundBean;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.my.contract.RefundContract;
import com.ifeell.app.aboutball.my.presenter.RefundPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.util.SpanUtils;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/23 13:46
 * 更新时间: 2019/5/23 13:46
 * 描述:申请退款Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_REFUND)
public class RefundActivity extends BaseActivity<RefundPresenter> implements RefundContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_reserve_content)
    TextView mTvReserveContent;
    @BindView(R.id.item_money)
    ItemView mItemMoney;
    @BindView(R.id.rv_cause)
    RecyclerView mRvCause;
    private RefundCauseAdapter mCauseAdapter;
    private List<ResultRefundCauseBean> mCauseData;
    private RequestRefundBean mRequestBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            return;
        }
        String venueName = bundle.getString(ARouterConfig.Key.VENUE_NAME);
        UIUtils.setText(mTvName, venueName);
        String time = bundle.getString(ARouterConfig.Key.TARGET_DATE);
        UIUtils.setOrderDetailsItemSpan(mTvTime, UIUtils.getString(R.string.order_item_time_host), time);
        String site = bundle.getString(ARouterConfig.Key.TARGET_SITE);
        UIUtils.setText(mTvReserveContent, site);
        String money = bundle.getString(ARouterConfig.Key.MONEY);
        String moneyHost=UIUtils.getString(R.string.surrender_value);
        UIUtils.setText(mItemMoney.mTvLeft,  SpanUtils.getTextColor(R.color.colorFFA6A6A6,0,moneyHost.length(),moneyHost+money));
        long orderId = bundle.getLong(ARouterConfig.Key.ORDER_ID, -1);

        mRequestBean = new RequestRefundBean();
        mRequestBean.orderId = orderId;
        if (orderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        mRvCause.setLayoutManager(new LinearLayoutManager(this));
        mCauseData = new ArrayList<>();
        mCauseAdapter = new RefundCauseAdapter(this, mCauseData);
        mRvCause.setAdapter(mCauseAdapter);
        mPresenter.loadRefundCauseList();


    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mCauseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(@NonNull View view, int position) {
                mRequestBean.description = mCauseData.get(position).reasonContent;
                mRequestBean.reasonId = mCauseData.get(position).reasonId;
            }
        });
    }

    @Override
    protected RefundPresenter createPresenter() {
        return new RefundPresenter(this);
    }


    @Override
    public void resultRefundCauseData(List<ResultRefundCauseBean> data) {
        mCauseData.clear();
        if (data!= null && data.size()>0){
            mCauseData.addAll(data);
        }
        mCauseAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (mCauseAdapter.isCheckItem()){
            mPresenter.refundOrder(mRequestBean);
        }else {
            UIUtils.showToast(R.string.please_selector_refund_causes);
        }
    }

    @Override
    public void resultRefundOrder() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
