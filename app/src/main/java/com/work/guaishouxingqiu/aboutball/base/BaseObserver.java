package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 11:54
 * 更新时间: 2019/3/8 11:54
 * 描述: BaseObserver
 */
public class BaseObserver<T> implements Observer<BaseBean<T>> {

    private CompositeDisposable mCompositeDisposable;
    private BaseObserver.Observer<T> mObserver;


    public BaseObserver(@NonNull CompositeDisposable compositeDisposable, BaseObserver.Observer<T> observer) {
        this.mCompositeDisposable = compositeDisposable;
        this.mObserver = observer;
    }

    public BaseObserver(@NonNull CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
    }


    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onNext(BaseBean<T> baseBean) {
        if (mObserver != null) {
            mObserver.onNext(baseBean);
        } else {
            EventBus.getDefault().post(baseBean);
        }
    }




    @Override
    public void onError(Throwable e) {
        if (mObserver != null) {
            mObserver.onError(e);
        }
    }

    @Override
    public void onComplete() {

    }

    public interface Observer<T> {

        void onNext(BaseBean<T> t);

        void onError(Throwable e);

    }
}
