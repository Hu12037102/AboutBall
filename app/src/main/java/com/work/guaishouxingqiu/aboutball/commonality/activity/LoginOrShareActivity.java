package com.work.guaishouxingqiu.aboutball.commonality.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.CameraActivity;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ShareWebBean;
import com.work.guaishouxingqiu.aboutball.login.bean.ResultThreeLoginBean;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.weight.ShareDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.work.guaishouxingqiu.aboutball.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 9:33
 * 更新时间: 2019/4/4 9:33
 * 描述: 登录或者分享Activity
 */
public abstract class LoginOrShareActivity<P extends LoginOrSharePresenter> extends CameraActivity<P> implements
        LoginOrShareContract.View {


    private ShareDialog mShareDialog;

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

    public void loginWeiChat(WXEntryActivity.WeiChatStatus status) {
        mPresenter.setWeiChatStatus(status);
        if (this.getBaseApplication().getWeiChatApi().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "aboutball_wx_login";
            //  req.state = "wechat_sdk_demo_test";
            //  req.state = getPackageName() + (Math.random() * 100);
            this.getBaseApplication().getWeiChatApi().sendReq(req);

        } else {
            mViewModel.showInstallWeiChatDialog();
        }
    }

    public void shareWebToWeiChat(ShareWebBean bean) {
        IWXAPI weiChatApi = this.getBaseApplication().getWeiChatApi();
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = bean.webUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.setThumbImage(BitmapFactory.decodeResource(getResources(), bean.iconRes));
        msg.title = bean.title;
        msg.description = bean.description;
        msg.mediaObject = webObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
       // req.openId = UserManger.get().getUser().weChatOpenId;
       // req.openId = "oBCsk5v5Y6I400ORD8DjinwvZWAA";
        req.scene = bean.scene;
        weiChatApi.sendReq(req);
    }

    @Subscribe
    public void resultWeiChatDataToActivity(BaseResp baseResp) {
        if (PhoneUtils.isTopActivity(this, this.getClass().getName())) {
            LogUtils.w("resultWeiChatDataToActivity--", "我是顶部的Activity--" + this.getClass().getName());
            if (baseResp.getType() == LoginOrSharePresenter.WEICHAT_LOGIN_TYPE) {
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
            } else if (baseResp.getType() == LoginOrSharePresenter.WEICHAT_SHARE_TYPE) {
                resultShareWeiChat();
            }
        }

    }

    public void resultShareWeiChat() {
    }

    @Override
    public void resultOtherLogin(ResultThreeLoginBean bean, String signCode) {

    }

    @Override
    public void sendMessageCodeSucceedResult() {

    }

    @Override
    public void countDownTimeUpdate(long time) {

    }

    @Override
    public void countDownTimeComplete() {

    }

    @Override
    public void resultMessageCode() {

    }

    public void showShareDialog(ShareWebBean bean) {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(this);
        }
        if (!mShareDialog.isShowing()) {
            mShareDialog.show();
        }
        mShareDialog.setWeichatClicklistener(v -> {
            shareWebToWeiChat(bean);
            mShareDialog.dismiss();
        });
        mShareDialog.setWeichatFriendClickListener(v -> {
            bean.scene = SendMessageToWX.Req.WXSceneTimeline;
            shareWebToWeiChat(bean);
            mShareDialog.dismiss();
        });
    }

    @Override
    public void resultBandOtherAccount(String signCode) {
        UserManger.get().putWeiChatOpenId(signCode);
    }

}
