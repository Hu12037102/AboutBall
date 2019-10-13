package com.ifeell.app.aboutball.commonality.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.ifeell.app.aboutball.commonality.bean.ShareWebBean;
import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.login.bean.ResultThreeLoginBean;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.LogUtils;
import com.ifeell.app.aboutball.util.PhoneUtils;
import com.ifeell.app.aboutball.weight.ShareDialog;
import com.ifeell.app.aboutball.weight.Toasts;
import com.ifeell.app.aboutball.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 10:49
 * 更新时间: 2019/5/7 10:49
 * 描述:
 */
public abstract class LoginOrShareFragment<P extends LoginOrSharePresenter> extends DelayedFragment<P> implements
        LoginOrShareContract.View {
    private ShareDialog mShareDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }


    public void loginWeiChat(WXEntryActivity.WeiChatStatus status) {
        mPresenter.setWeiChatStatus(status);
        if (this.getBaseActivity().getBaseApplication().getWeiChatApi().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "aboutball_wx_login";
            //  req.state = "wechat_sdk_demo_test";
            //  req.state = getPackageName() + (Math.random() * 100);
            this.getBaseActivity().getBaseApplication().getWeiChatApi().sendReq(req);

        } else {
            mViewModel.showInstallWeiChatDialog();
        }
    }

    private void shareWebToWeiChat(ShareWebBean bean) {
        IWXAPI weiChatApi = this.getBaseActivity().getBaseApplication().getWeiChatApi();
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = bean.webUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.setThumbImage(BitmapFactory.decodeResource(getResources(), bean.iconRes));
        msg.title = bean.title;
        msg.description = bean.description;
        msg.mediaObject = webObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        //req.openId = UserManger.get().getUser().weChatOpenId;
        req.scene = bean.scene;
        weiChatApi.sendReq(req);
    }

    @Subscribe
    public void resultWeiChatDataToFragment(BaseResp baseResp) {
        LogUtils.w("resultWeiChatDataToFragment--", baseResp.getType() + "--" + baseResp.errCode);
        if (PhoneUtils.isTopActivity(mContext, DataUtils.checkData(getActivity()).getClass().getName())) {
            LogUtils.w("resultWeiChatDataToActivity---", "我是顶部的Fragment--" + DataUtils.checkData(getActivity()).getClass().getName());
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
            mShareDialog = new ShareDialog(getContext());
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
