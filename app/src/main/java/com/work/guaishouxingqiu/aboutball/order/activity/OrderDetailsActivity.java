package com.work.guaishouxingqiu.aboutball.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.order.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.order.contract.OrderDetailsContract;
import com.work.guaishouxingqiu.aboutball.order.presenter.OrderDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.PayDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:04
 * 更新时间: 2019/9/12 14:04
 * 描述:  订单详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_DETAILS)
public class OrderDetailsActivity extends BaseActivity<OrderDetailsPresenter> implements OrderDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_address)
    ImageView mIvAddress;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    @BindView(R.id.ll_body)
    LinearLayout mLlBody;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    private RequestSureOrderBean mIntentBean;
    private ResultOrderDetailsBean mResultBean;
    private HintDialog mHintDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        mIvAddress.setVisibility(View.GONE);
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mSrlRefresh.setOnRefreshListener(refreshLayout -> loadData());
        mTitleView.setOnBackViewClickListener(view -> showBackHintDialog());
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        mPresenter.loadOrderDetails(mIntentBean);
    }

    @OnClick({R.id.iv_address, R.id.tv_commit})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_address:
                if (mResultBean != null) {
                    mViewModel.startActivityToMap(mResultBean.longitude, mResultBean.latitude, mResultBean.locationName);
                }
                break;
            case R.id.tv_commit:
                if (mResultBean != null) {
                    mViewModel.showPayDialog(DataUtils.getMoneyFormat(mResultBean.amount), view1 -> mPresenter.payWeiChatSing(mResultBean.id));
                }
                break;
            default:
                break;
        }
    }

    private void showBackHintDialog() {
        if (mHintDialog == null) {
            mHintDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.pay_order_hint_back_body)
                    .setShowSingButton(false)
                    .setCancel(R.string.cancel)
                    .setSures(R.string.sure)
                    .builder();
        }
        if (!mHintDialog.isShowing()) {
            mHintDialog.show();
        }
        mHintDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                clickBackForResult();
            }

            @Override
            public void onClickCancel(@NonNull View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        showBackHintDialog();
    }

    @Override
    protected OrderDetailsPresenter createPresenter() {
        return new OrderDetailsPresenter(this);
    }

    @Override
    public void resultOrderDetails(@NonNull ResultOrderDetailsBean bean) {
        mResultBean = bean;
        mIvAddress.setVisibility(View.VISIBLE);
        UIUtils.setText(mTvName, bean.title);
        UIUtils.setText(mTvAddress, bean.subTitle);
        mTvCommit.setEnabled(true);
        String minuteValues = UIUtils.getString(R.string.default_minute_values_cancel);
        String minuteContent = UIUtils.getString(R.string.order_ten_minute_cancel, minuteValues);
        UIUtils.setText(mTvHint, SpanUtils.getTextColor(R.color.color_4, minuteContent.indexOf(minuteValues), minuteContent.indexOf(minuteValues) + minuteValues.length(), minuteContent));
        mTvCommit.setBackgroundResource(R.drawable.shape_click_button);
        UIUtils.setText(mTvCommit, UIUtils.getString(R.string.how_money_please_pay, DataUtils.getMoneyFormat(mResultBean.amount)));
        mLlBody.removeAllViews();
        List<ResultOrderDetailsBean.ModuleBean> moduleBeanData = bean.moduleList;
        if (moduleBeanData != null && moduleBeanData.size() > 0) {
            for (int i = 0; i < moduleBeanData.size(); i++) {
                View moduleView = LayoutInflater.from(this).inflate(R.layout.item_order_details_view, mLlBody, false);
                mLlBody.addView(moduleView, i);
                TextView tvContent = moduleView.findViewById(R.id.tv_content);
                UIUtils.setText(tvContent, moduleBeanData.get(i).label);
                LinearLayout llData = moduleView.findViewById(R.id.ll_data);
                List<ResultOrderDetailsBean.ModuleChildBean> moduleChildBeanData = moduleBeanData.get(i).paramValue;
                if (moduleChildBeanData != null && moduleChildBeanData.size() > 0) {
                    for (int j = 0; j < moduleChildBeanData.size(); j++) {
                        View moduleChildView = LayoutInflater.from(this).inflate(R.layout.item_order_details_child_view, llData, false);
                        llData.addView(moduleChildView);
                        TextView tvKey = moduleChildView.findViewById(R.id.tv_key);
                        UIUtils.setText(tvKey, moduleChildBeanData.get(j).name + "：");
                        TextView tvValues = moduleChildView.findViewById(R.id.tv_values);
                        UIUtils.setText(tvValues, moduleChildBeanData.get(j).value);
                    }
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ARouterIntent.REQUEST_CODE) {
            mViewModel.clickBackForResult();
        }
    }

}
