package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.BaseOrderActivity;
import com.work.guaishouxingqiu.aboutball.venue.presenter.BaseOrderPresenter;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 9:58
 * 更新时间: 2019/5/22 9:58
 * 描述:支付相关的activity
 */
public abstract class BasePayActivity<P extends BaseOrderPresenter> extends BaseOrderActivity<P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }
    public String getActivityPath(){
        return ARouterConfig.Path.ACTIVITY_PAY_SUCCEED;
    }

    @Subscribe
    public void resultWeiChatPay(BaseResp baseResp) {
        switch (baseResp.errCode) {
            //成功
            case 0:
                UIUtils.showToast(R.string.succeed_pay);
                ARouterIntent.startActivity(getActivityPath(), ARouterConfig.Key.ORDER_ID, getOrderId());
                setResult(Activity.RESULT_OK);
                finish();
                break;
            //签名错误、appId错误等其他异常
            case -1:
                UIUtils.showToast(R.string.sing_error);
                break;
            //用户取消
            case -2:
                UIUtils.showToast(R.string.cancel_pay);
                break;
            default:
                break;
        }

    }

    public abstract long getOrderId();
}
