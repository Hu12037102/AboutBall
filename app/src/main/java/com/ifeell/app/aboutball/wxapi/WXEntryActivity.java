package com.ifeell.app.aboutball.wxapi;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.ifeell.app.aboutball.base.BaseApplication;
import com.ifeell.app.aboutball.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 10:00
 * 更新时间: 2019/4/4 10:00
 * 描述: 微信调用页面
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public  enum WeiChatStatus{
        LOGIN,
        BAND
    }
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
        LogUtils.w("WXEntryActivity--", baseResp.errCode+"--");
        EventBus.getDefault().post(baseResp);
        finish();
    }

}
