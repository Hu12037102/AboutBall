package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.bean.RequestSureOrderBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultConfirmOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.SureOrderContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.SureOrderPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.InputNumberView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/5 9:38
 * 更新时间: 2019/9/5 9:38
 * 描述: 确认订单Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SURE_ORDER)
public class SureOrderActivity extends BaseActivity<SureOrderPresenter> implements SureOrderContract.View {

    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_value)
    LinearLayout mLlValue;
    @BindView(R.id.inv_count)
    InputNumberView mInvCount;
    @BindView(R.id.tv_count_title)
    TextView mTvCountTitle;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    private RequestSureOrderBean mIntentBean;
    private ResultConfirmOrderBean mResultBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sure_order;
    }

    @Override
    public void initPermission() {
        mIntentBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mIntentBean == null) {
            UIUtils.showToast(R.string.create_order_fail);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        UIUtils.setText(mTvMoney, UIUtils.getString(R.string.sum_price_s, "--"));
        mInvCount.setAddClickable(false);
        mPresenter.loadConfirmOrder(mIntentBean.spuId, mIntentBean.params, mIntentBean.num);
        mTvCommit.setEnabled(false);
        mTvCommit.setBackgroundResource(R.drawable.shape_default_button);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mInvCount.setOnClickInputClickListener(new InputNumberView.OnClickInputClickListener() {
            @Override
            public void onClickSubtract(View view, int num) {
                setPrice(mResultBean.unitPrice * num);
            }

            @Override
            public void onClickAdd(View view, int num) {
                setPrice(mResultBean.unitPrice * num);
            }
        });
    }

    private void setPrice(double priceNumber) {
        String price = DataUtils.getMoneyFormat(priceNumber);
        String content = UIUtils.getString(R.string.sum_price_s, price);
        if (!DataUtils.isEmpty(price) && !DataUtils.isEmpty(content) && content.length() > 0) {
            SpannableString ss = SpanUtils.getTextColor(R.color.colorFFF53133, content.length() - price.length(), content.length(), content);
            UIUtils.setText(mTvMoney, SpanUtils.getTextSize(22, content.length() - price.length(), content.length(), ss));
        } else {
            UIUtils.setText(mTvMoney, content);
        }

    }

    @Override
    protected SureOrderPresenter createPresenter() {
        return new SureOrderPresenter(this);
    }

    @Override
    public void resultConfirmOrder(@NonNull ResultConfirmOrderBean bean) {
        mResultBean = bean;
        mInvCount.setAddClickable(true);
        mTvCommit.setEnabled(true);
        mTvCommit.setBackgroundResource(R.drawable.shape_click_button);
        UIUtils.setText(mTvName, bean.title);
        mInvCount.setInputNum(bean.num);
        mInvCount.setMaxNum(bean.maxMum);
        //UIUtils.setText(mTvMoney, UIUtils.getString(R.string.sum_price_s, DataUtils.getMoneyFormat(bean.price)));
        setPrice(DataUtils.getDoubleFormat(bean.price));
        if (bean.paramList != null && bean.paramList.size() > 0) {
            mLlValue.removeAllViews();
            for (int i = 0; i < bean.paramList.size(); i++) {
                View valuesView = LayoutInflater.from(this).inflate(R.layout.item_sure_order_text_view, mLlValue, false);
                TextView tvContent = valuesView.findViewById(R.id.tv_content);
                mLlValue.addView(valuesView, i);
                ResultConfirmOrderBean.Param param = bean.paramList.get(i);
                UIUtils.setText(tvContent, param.name + "：" + param.value);
            }

        }

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        mIntentBean.num = mInvCount.getNum();
        mIntentBean.params = mResultBean.params;
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ORDER_DETAILS,ARouterConfig.Key.PARCELABLE,mIntentBean);
        finish();
    }
}
