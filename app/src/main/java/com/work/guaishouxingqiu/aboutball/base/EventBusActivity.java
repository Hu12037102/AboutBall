package com.work.guaishouxingqiu.aboutball.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 15:47
 * 更新时间: 2019/3/8 15:47
 * 描述:
 */
public abstract class EventBusActivity<P extends BasePresenter> extends BaseActivity<P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    @Subscribe
    public void requestEventResult(BaseBean baseBean) {
        switch (baseBean.code) {
            case IApi.Code.MESSAGES_CODE_ERROR:
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.USER_NO_EXIST:
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.SUCCEED:
                Toasts.with().showToast(baseBean.message);
                break;
            default:
                break;
        }
    }
}
