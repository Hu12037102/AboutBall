package com.ifeell.app.aboutball.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.ifeell.app.aboutball.base.BaseApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 9:47
 * 更新时间: 2019/5/21 9:47
 * 描述:
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ((BaseApplication) getApplication()).getWeiChatApi();
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        EventBus.getDefault().post(baseResp);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
