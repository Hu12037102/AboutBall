package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
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
        if (this.getBaseApplication().getWeiChatApi().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "aboutball_wx_login";
            //  req.state = "wechat_sdk_demo_test";
            //  req.state = getPackageName() + (Math.random() * 100);
            this.getBaseApplication().getWeiChatApi().sendReq(req);

        } else {
            HintDialog hintDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_not_installed_weichat)
                    .setSure(R.string.sure)
                    .builder();
            hintDialog.show();
            hintDialog.setOnItemClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mm"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                hintDialog.dismiss();
            });
        }
    }

    @Subscribe
    public void resultWeiChatData(BaseResp baseResp) {
        LogUtils.w("resultWeiChatData--", baseResp.errCode + "--");
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
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    break;
                default:
                    break;
            }
        } else if (baseResp.getType() == WEICHAT_SHARE_TYPE) {

        }

    }


}
