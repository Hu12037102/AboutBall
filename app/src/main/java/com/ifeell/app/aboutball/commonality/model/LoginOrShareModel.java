package com.ifeell.app.aboutball.commonality.model;

import com.ifeell.app.aboutball.BaseService;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.login.bean.ResultThreeLoginBean;
import com.ifeell.app.aboutball.login.model.MessageModel;
import com.ifeell.app.aboutball.commonality.WeiChatService;
import com.ifeell.app.aboutball.commonality.bean.RequestOtherLoginBean;
import com.ifeell.app.aboutball.commonality.bean.ResultWeiChatInfo;
import com.ifeell.app.aboutball.commonality.bean.ResultWeiChatTokenBean;
import com.ifeell.app.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.ifeell.app.aboutball.my.bean.RequestBandOtherAccountBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:32
 * 更新时间: 2019/4/4 13:32
 * 描述:第三方登录或者分享model
 */
public class LoginOrShareModel extends MessageModel {
    public void getWeiChatToken(RequestWeiChatTokenBean bean, Observer<ResultWeiChatTokenBean> observer) {
        mRetrofitManger.setBaseUrl(WeiChatService.BASE_WEICHAT_URL)
                .create(WeiChatService.class)
                .getWeiChatToken(bean.appid, bean.secret, bean.code, bean.grant_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        this.resetBaseUrl();

    }

    public void getWeiChatInfo(String accessToken, String openid, Observer<ResultWeiChatInfo> observer) {
        mRetrofitManger.setBaseUrl(WeiChatService.BASE_WEICHAT_URL)
                .create(WeiChatService.class)
                .getWeiChatInfo(accessToken, openid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        this.resetBaseUrl();
    }

    public void loginOtherWeiChat(RequestOtherLoginBean bean, BaseObserver<ResultThreeLoginBean> observer) {
        mRetrofitManger.create(WeiChatService.class)
                .weiChatLogin(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void bandOtherAccount(RequestBandOtherAccountBean bean, BaseObserver<String> observer) {
        mRetrofitManger.create(BaseService.class)
                .bandOtherAccount(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void resetBaseUrl() {
        mRetrofitManger.resetBaseUrl();
    }
}
