package com.work.guaishouxingqiu.aboutball.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.base.BaseApplication;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 10:00
 * 更新时间: 2019/4/4 10:00
 * 描述: 微信调用页面
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandIntent();

    }

    private void initHandIntent() {
        ((BaseApplication) getApplication()).getWeiChatApi().handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.w("WXEntryActivity--", baseReq.transaction + "--" + baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        EventBus.getDefault().post(baseResp);
        finish();
    }

}
