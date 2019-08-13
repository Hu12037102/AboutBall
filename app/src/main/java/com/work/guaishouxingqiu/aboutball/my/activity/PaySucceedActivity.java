package com.work.guaishouxingqiu.aboutball.my.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.commonality.activity.BasePayActivity;
import com.work.guaishouxingqiu.aboutball.commonality.activity.LoginOrShareActivity;
import com.work.guaishouxingqiu.aboutball.media.IntentData;
import com.work.guaishouxingqiu.aboutball.my.contract.PaySucceedContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.PaySucceedPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.CreateBallActivity;
import com.work.guaishouxingqiu.aboutball.venue.activity.VenueDetailsActivity;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.tv_click)
    TextView mTvClick;
    private long mOrderId;
    private HintDialog mAboutDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_succeed;
    }

    @Override
    protected void initView() {
        registerEventBus();
        mOrderId = mIntent.getLongExtra(ARouterConfig.Key.ORDER_ID, -1);
        if (mOrderId == -1) {
            UIUtils.showToast(R.string.not_find_this_order);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeStickyEvent(PaySucceedActivity.Type.class);
        unRegisterEventBus();
        super.onDestroy();
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
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS, ARouterConfig.Key.ORDER_ID, mOrderId);
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
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void sendCreateBallMessage(PaySucceedActivity.Type type) {
        if (type.flag == Contast.PayType.ABOUT_BALL_ORDER) {
            mTvClick.setVisibility(View.VISIBLE);
            String host = UIUtils.getString(R.string.succeed_pay);
            String content = "已经成功预定该场地，可以返回发起约球";
            mTvTitle.setText(SpanUtils.getTextSize(17, 0, host.length(), SpanUtils.getTextColor(R.color.color_4, 0, host.length(), content)));
            mTvClick.setVisibility(View.VISIBLE);
            mTvClick.setText("返回发起约球");
        } else if (type.flag == Contast.PayType.WAIT_NOT_ABOUT_BALL_ORDER) {
            if (mAboutDialog == null) {
                mAboutDialog = new HintDialog.Builder(this)
                        .setTitle(R.string.hint)
                        .setBody(R.string.about_ball_pay_succeed_dialog_body)
                        .setSure(R.string.sure)
                        .setCancelTouchOut(false)
                        .builder();
            }
            mAboutDialog.setOnItemClickListener(new HintDialog.OnItemClickListener() {
                @Override
                public void onClickSure(@NonNull View view) {
                    mAboutDialog.dismiss();
                }
            });
            if (!mAboutDialog.isShowing() && !isFinishing()) {
                mAboutDialog.show();
            }
        }

    }

    public static class Type {
        public int flag;

        public Type(int flag) {
            this.flag = flag;
        }
    }


}
