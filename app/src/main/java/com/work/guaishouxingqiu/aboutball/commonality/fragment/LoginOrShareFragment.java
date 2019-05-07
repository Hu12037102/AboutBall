package com.work.guaishouxingqiu.aboutball.commonality.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionFragment;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 10:49
 * 更新时间: 2019/5/7 10:49
 * 描述:
 */
public abstract class LoginOrShareFragment<P extends LoginOrSharePresenter> extends BaseFragment<P> implements
        LoginOrShareContract.View {


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


    public void loginWeiChat() {
        if (this.getBaseActivity().getBaseApplication().getWeiChatApi().isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "aboutball_wx_login";
            //  req.state = "wechat_sdk_demo_test";
            //  req.state = getPackageName() + (Math.random() * 100);
            this.getBaseActivity().getBaseApplication().getWeiChatApi().sendReq(req);

        } else {
            HintDialog hintDialog = new HintDialog.Builder(mContext)
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

    public void shareWebToWeiChat(String url) {
        LogUtils.w("shareWebToWeiChat--",url);
        IWXAPI weiChatApi = this.getBaseActivity().getBaseApplication().getWeiChatApi();
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.setThumbImage(BitmapFactory.decodeResource(getResources(), R.mipmap.app_launcher));
        msg.title = "邀请好友";
        msg.description = "快来参加我们的球队吧！";
        msg.mediaObject =webObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.openId = UserManger.get().getUser().weChatOpenId;
        weiChatApi.sendReq(req);
    }

    @Subscribe
    public void resultWeiChatData(BaseResp baseResp) {
        LogUtils.w("resultWeiChatData--", baseResp.errCode + "--");
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

        }

    }

    @Override
    public void resultOtherLogin(LoginResultBean bean) {

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
}