package com.work.guaishouxingqiu.aboutball.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 18:18
 * 更新时间: 2019/5/15 18:18
 * 描述:支付dialog
 */
public class PayDialog extends BaseDialog {

    private TextView mTvPay;
    private TextView mTvMoney;
    private TitleView mTitleView;

    public PayDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(view -> dismiss());
    }

    @Override
    protected void initData() {
        mTvMoney.setText(UIUtils.getString(R.string.money_count, "0.0"));
    }

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_pay_view, null);
        this.setContentView(inflateView);
        mTitleView = findViewById(R.id.title_view);
        mTvMoney = findViewById(R.id.tv_money);
        mTvPay = findViewById(R.id.tv_pay);
    }

    public PayDialog setMoney(double money) {
        mTvMoney.setText(UIUtils.getString(R.string.money_count, money));
        return this;
    }

    public PayDialog setMoney(String money) {
        mTvMoney.setText( money);

        return this;
    }

    public void setOnClickPayListener(View.OnClickListener clickPayListener) {
        if (clickPayListener != null) {
            mTvPay.setOnClickListener(clickPayListener);
           // dismiss();
        }
    }
}
