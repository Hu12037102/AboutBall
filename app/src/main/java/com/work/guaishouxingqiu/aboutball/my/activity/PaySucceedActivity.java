package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.activity.LoginOrShareActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.PaySucceedContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.PaySucceedPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 10:44
 * 更新时间: 2019/5/22 10:44
 * 描述:支付成功activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_PAY_SUCCEED)
public class PaySucceedActivity extends LoginOrShareActivity<PaySucceedPresenter> implements PaySucceedContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private long mOrderId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_succeed;
    }

    @Override
    protected void initView() {
        mOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (mOrderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
        }
    }

    @Override
    protected void initData() {
        String host = UIUtils.getString(R.string.succeed_pay);
        String content = UIUtils.getString(R.string.pay_succeed_transmit_friend);
        mTvTitle.setText(SpanUtils.getTextSize(17, 0, host.length(), SpanUtils.getTextColor(R.color.color_4, 0, host.length(), content)));
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {

            }

            @Override
            public void onSureClick(@NonNull View view) {
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS,ARouterConfig.Key.ORDER_ID,mOrderId);
                finish();
            }
        });
    }

    @Override
    protected PaySucceedPresenter createPresenter() {
        return new PaySucceedPresenter(this);
    }


    @OnClick(R.id.tv_click)
    public void onViewClicked() {
    }
}
