package com.work.guaishouxingqiu.aboutball.commonality.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.presenter.MessagePresenter;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestOtherLoginBean;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ResultWeiChatInfo;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ResultWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:49
 * 更新时间: 2019/4/4 13:49
 * 描述: 登录或者分享P
 * 先获取微信登录权限，在获取token，拿到token再去拿微信信息，在调自己的登录接口，在获取用户信息
 */
public abstract class LoginOrSharePresenter<V extends LoginOrShareContract.View, M extends LoginOrShareModel> extends MessagePresenter<V, M>
        implements LoginOrShareContract.Presenter {


    public LoginOrSharePresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWeiChatToken(RequestWeiChatTokenBean bean) {
        mModel.getWeiChatToken(bean, new Observer<ResultWeiChatTokenBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(ResultWeiChatTokenBean resultWeiChatTokenBean) {
                if (resultWeiChatTokenBean.errcode == IApi.Code.SUCCEED) {
                    getWeiChatInfo(resultWeiChatTokenBean.access_token, resultWeiChatTokenBean.openid);
                } else {
                    mView.showToast(UIUtils.getString(R.string.weichat_login_failed));
                }
                LogUtils.w("getWeiChatToken--", resultWeiChatTokenBean + "--");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void getWeiChatInfo(String accessToken, String openid) {
        mModel.getWeiChatInfo(accessToken, openid, new Observer<ResultWeiChatInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(ResultWeiChatInfo resultWeiChatInfo) {
                if (resultWeiChatInfo != null && resultWeiChatInfo.errcode == IApi.Code.SUCCEED) {
                    RequestOtherLoginBean bean = new RequestOtherLoginBean();
                    bean.imageUrl = resultWeiChatInfo.headimgurl;
                    bean.nickName = resultWeiChatInfo.nickname;
                    bean.signCode = resultWeiChatInfo.unionid;
                    bean.type = 1;
                    mModel.loginOtherWeiChat(bean, new BaseObserver<>(true, LoginOrSharePresenter.this, new BaseObserver.Observer<LoginResultBean>() {
                        @Override
                        public void onNext(BaseBean<LoginResultBean> t) {
                            if (t.code == IApi.Code.SUCCEED) {
                                mView.resultOtherLogin(t.result);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }));
                    //   mView.resultWeiChatInfo(resultWeiChatInfo);
                } else {
                    mView.showToast(UIUtils.getString(R.string.weichat_login_failed));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void deathPresenter() {
        mModel.resetBaseUrl();
        super.deathPresenter();
    }
}
