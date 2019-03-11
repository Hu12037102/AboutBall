package com.work.guaishouxingqiu.aboutball.login.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.login.contract.MessageContract;
import com.work.guaishouxingqiu.aboutball.login.model.MessageModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 10:43
 * 更新时间: 2019/3/11 10:43
 * 描述: 短信P
 */
public abstract   class MessagePresenter<V extends MessageContract.View, M extends MessageModel>
        extends BasePresenter<V, M> implements MessageContract.Presenter {

    public MessagePresenter(@NonNull V view) {
        super(view);
    }

    /**
     * 发送信息验证码
     *
     * @param phoneNumber 电话号码
     * @param type        类型（登录，注册...）
     */
    @Override
    public void sendMessageCode(@NonNull String phoneNumber, int type) {
        mModel.sendMessageCode(phoneNumber, type, new BaseObserver(mCompositeDisposable, new BaseObserver.Observer() {
            @Override
            public void onNext(BaseBean bean) {
                if (mView == null)
                    return;
                if (bean.code == 0) {
                    mView.sendMessageCodeSucceedResult();
                }
                mView.showToast(bean.message);
            }

            @Override
            public void onError(Throwable e) {

            }

        }));
    }

    /**
     * 倒计时
     */
    @Override
    public void countDownTime(int timeLength) {
        mModel.countDownTime(timeLength, new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(Long time) {
                mView.countDownTimeUpdate(time);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                mView.countDownTimeComplete();
            }
        });
    }

}
