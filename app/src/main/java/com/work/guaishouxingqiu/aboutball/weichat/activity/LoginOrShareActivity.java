package com.work.guaishouxingqiu.aboutball.weichat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weichat.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.weichat.bean.ResultWeiChatInfo;
import com.work.guaishouxingqiu.aboutball.weichat.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.weichat.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 9:33
 * 更新时间: 2019/4/4 9:33
 * 描述: 登录或者分享Activity
 */
public abstract class LoginOrShareActivity<P extends LoginOrSharePresenter> extends PermissionActivity<P> implements
        LoginOrShareContract.View {
    //微信登录
    public static final int WEICHAT_LOGIN_TYPE = 1;
    //微信分享
    public static final int WEICHAT_SHARE_TYPE = 2;
    public static final String WEICHAT_SECRET = "40a967eb50ce478246b63d8d78525893";


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

    public void loginWeiChat() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "aboutball_wx_login";
        this.getBaseApplication().getWeiChatApi().sendReq(req);
    }

    @Subscribe
    public void resultWeiChatData(BaseResp baseResp) {
        if (baseResp.getType() == WEICHAT_LOGIN_TYPE) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    RequestWeiChatTokenBean bean = new RequestWeiChatTokenBean();
                    SendAuth.Resp req = (SendAuth.Resp) baseResp;
                    bean.code = req.code;
                    mPresenter.getWeiChatToken(bean);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toasts.with().showToast(R.string.cancel_weichat_login);
                    break;
                default:
                    break;
            }
        } else if (baseResp.getType() == WEICHAT_SHARE_TYPE) {

        }

    }


}
