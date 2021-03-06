package com.ifeell.app.aboutball.commonality.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.util.UIUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 14:19
 * 更新时间: 2019/5/22 14:19
 * 描述:
 */
public abstract class BasePayFragment<P extends BasePresenter> extends DelayedFragment<P> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();

    }


    @Override
    public void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();

    }

    @Subscribe
    public void resultWeiChatPayForFragment(BaseResp baseResp) {
        switch (baseResp.errCode) {
            //成功
            case 0:
                UIUtils.showToast(R.string.succeed_pay);
                paySucceed();
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


    public void paySucceed() {

    }
}
