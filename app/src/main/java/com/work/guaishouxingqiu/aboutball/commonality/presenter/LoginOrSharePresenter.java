package com.work.guaishouxingqiu.aboutball.commonality.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.ResultThreeLoginBean;
import com.work.guaishouxingqiu.aboutball.login.presenter.MessagePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestBandOtherAccountBean;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestOtherLoginBean;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ResultWeiChatInfo;
import com.work.guaishouxingqiu.aboutball.commonality.bean.ResultWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.bean.RequestWeiChatTokenBean;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;
import com.work.guaishouxingqiu.aboutball.wxapi.WXEntryActivity;

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
    //微信登录
    public static final int WEICHAT_LOGIN_TYPE = 1;
    //微信分享
    public static final int WEICHAT_SHARE_TYPE = 2;
    private WXEntryActivity.WeiChatStatus mStatus = WXEntryActivity.WeiChatStatus.LOGIN;

    public LoginOrSharePresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void start() {

    }

    public void setWeiChatStatus(WXEntryActivity.WeiChatStatus status) {
        this.mStatus = status;
    }

    @Override
    public void getWeiChatToken(RequestWeiChatTokenBean bean) {

        mView.showLoadingView();
        mModel.getWeiChatToken(bean, new Observer<ResultWeiChatTokenBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);

            }

            @Override
            public void onNext(ResultWeiChatTokenBean resultWeiChatTokenBean) {
                mView.dismissLoadingView();
                if (resultWeiChatTokenBean.errcode == IApi.Code.SUCCEED) {
                    getWeiChatInfo(resultWeiChatTokenBean.access_token, resultWeiChatTokenBean.openid);
                } else {
                    mView.showToast(UIUtils.getString(R.string.weichat_login_failed));
                }
                LogUtils.w("getWeiChatToken--", resultWeiChatTokenBean + "--");
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingView();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void getWeiChatInfo(String accessToken, String openid) {
        mView.showLoadingView();
        mModel.getWeiChatInfo(accessToken, openid, new Observer<ResultWeiChatInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(ResultWeiChatInfo resultWeiChatInfo) {
                mView.dismissLoadingView();
                if (resultWeiChatInfo != null && resultWeiChatInfo.errcode == IApi.Code.SUCCEED) {
                    RequestOtherLoginBean bean = new RequestOtherLoginBean();
                    bean.imageUrl = resultWeiChatInfo.headimgurl;
                    bean.nickName = resultWeiChatInfo.nickname;
                    bean.signCode = resultWeiChatInfo.unionid;
                    bean.type = 1;
                    if (mStatus == WXEntryActivity.WeiChatStatus.LOGIN) {
                        mModel.loginOtherWeiChat(bean, new BaseObserver<>(true, LoginOrSharePresenter.this, new BaseObserver.Observer<ResultThreeLoginBean>() {
                            @Override
                            public void onNext(BaseBean<ResultThreeLoginBean> t) {
                                if (t.code == IApi.Code.SUCCEED) {
                                    mView.resultOtherLogin(t.result, bean.signCode);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.dismissLoadingView();
                            }
                        }));
                    } else if (mStatus == WXEntryActivity.WeiChatStatus.BAND) {
                        RequestBandOtherAccountBean bandBean = new RequestBandOtherAccountBean();
                        bandBean.imageUrl = bean.imageUrl;
                        bandBean.nickName = bean.nickName;
                        bandBean.type = 1;
                        bandBean.signCode = bean.signCode;
                        bandOtherAccount(bandBean);
                    }
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

    public void bandOtherAccount(RequestBandOtherAccountBean bean) {
        mModel.bandOtherAccount(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<String>() {
            @Override
            public void onNext(BaseBean<String> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultBandOtherAccount(bean.signCode);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void deathPresenter() {
        mModel.resetBaseUrl();
        super.deathPresenter();
    }
}
