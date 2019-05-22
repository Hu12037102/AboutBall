package com.work.guaishouxingqiu.aboutball.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BaseApplication;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

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
        mApi.handleIntent(intent,this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        EventBus.getDefault().post(baseResp);
        finish();
    }
}
