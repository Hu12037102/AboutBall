package com.work.guaishouxingqiu.aboutball.good.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.commonality.activity.BasePayActivity;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultOrderDetailsBean;
import com.work.guaishouxingqiu.aboutball.good.contract.GoodDetailsContract;
import com.work.guaishouxingqiu.aboutball.good.presenter.GoodDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 14:04
 * 更新时间: 2019/9/12 14:04
 * 描述:  订单详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ORDER_DETAILS)
public class GoodDetailsActivity extends BasePayActivity<GoodDetailsPresenter> implements GoodDetailsContract.View {
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
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private RequestSureOrderBean mIntentBean;
    private ResultOrderDetailsBean mResultBean;
    private HintDialog mHintDialog;
    private long mIntentOrderId;
    private static final int REQUEST_CODE_REFUND_DETAIL = 895;

    //"订单状态：1.待付款，2.已付款，4，已完成，5.已取消 8.退款中 9.已退款",
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        mLlBottom.setVisibility(View.GONE);
        mIvAddress.setVisibility(View.GONE);
        mSrlRefresh.autoRefresh();
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        mIntentOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);

        if (mIntentBean == null && mIntentOrderId <= 0) {
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
        mTitleView.setOnBackViewClickListener(view -> clickBack());
    }

    private void clickBack() {
        if (mResultBean != null && mResultBean.status == Contast.MyGoodStatus.WAIT_PAY) {
            showBackHintDialog();
        } else {
            mViewModel.clickBackForResult();
        }
    }

    private void loadData() {
        mSrlRefresh.finishRefresh();
        if (mIntentBean != null) {
            mPresenter.loadOrderDetails(mIntentBean);
        } else {
            mPresenter.loadGoodDetails(mIntentOrderId);
        }

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
                    switch (mResultBean.status) {
                        //待支付
                        case Contast.MyGoodStatus.WAIT_PAY:
                            mViewModel.showPayDialog(DataUtils.getMoneyFormat(mResultBean.amount), view1 -> mPresenter.payWeiChatSing(mResultBean.id));
                            break;
                        //已付款
                        case Contast.MyGoodStatus.PAYING:
                            mViewModel.startGoodRefundDetailActivityForResult(null, GoodDetailsActivity.REQUEST_CODE_REFUND_DETAIL, mResultBean.id);
                            break;
                        //已完成
                        case Contast.MyGoodStatus.COMPLETE:
                            break;
                        //已取消
                        case Contast.MyGoodStatus.CANCEL:
                            break;
                        //退款中
                        case Contast.MyGoodStatus.REFUNDING:
                            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ORDER_REFUND_DETAILS, ARouterConfig.Key.ORDER_ID, mResultBean.id);
                            break;
                        //已退款
                        case Contast.MyGoodStatus.REFUNDED:
                            break;
                        default:
                            break;
                    }

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
        clickBack();
    }

    @Override
    protected GoodDetailsPresenter createPresenter() {
        return new GoodDetailsPresenter(this);
    }

    @Override
    public void resultOrderDetails(@NonNull ResultOrderDetailsBean bean) {
        mResultBean = bean;
        mIvAddress.setVisibility(View.VISIBLE);
        UIUtils.setText(mTvName, bean.title);
        UIUtils.setText(mTvAddress, bean.subTitle);
        mTvCommit.setEnabled(true);
        switch (bean.status) {
            //待支付
            case Contast.MyGoodStatus.WAIT_PAY:
                mLlBottom.setVisibility(View.VISIBLE);
                mTvHint.setVisibility(View.VISIBLE);
                String minuteValues = UIUtils.getString(R.string.default_minute_values_cancel);
                String minuteContent = UIUtils.getString(R.string.order_ten_minute_cancel, minuteValues);
                UIUtils.setText(mTvHint, SpanUtils.getTextColor(R.color.color_4, minuteContent.indexOf(minuteValues), minuteContent.indexOf(minuteValues) + minuteValues.length(), minuteContent));
                UIUtils.setText(mTvCommit, UIUtils.getString(R.string.how_money_please_pay, DataUtils.getMoneyFormat(mResultBean.amount)));
                break;
            //已付款
            case Contast.MyGoodStatus.PAYING:
                mLlBottom.setVisibility(View.VISIBLE);
                UIUtils.setText(mTvCommit, R.string.application_for_drawback);
                break;
            //已完成
            case Contast.MyGoodStatus.COMPLETE:
                break;
            //已取消
            case Contast.MyGoodStatus.CANCEL:
                break;
            //退款中
            case Contast.MyGoodStatus.REFUNDING:
                mLlBottom.setVisibility(View.VISIBLE);
                UIUtils.setText(mTvCommit, R.string.refund_schedule);
                break;
            //已退款
            case Contast.MyGoodStatus.REFUNDED:
                break;
            default:
                mTvHint.setVisibility(View.GONE);
                break;
        }
        mTvCommit.setBackgroundResource(R.drawable.shape_click_button);
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
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    mViewModel.clickBackForResult();
                    break;
                case REQUEST_CODE_REFUND_DETAIL:
                    mSrlRefresh.autoRefresh();
                    break;
            }
        }

    }

    @Override
    public long getOrderId() {
        return mResultBean.id;
    }
}
