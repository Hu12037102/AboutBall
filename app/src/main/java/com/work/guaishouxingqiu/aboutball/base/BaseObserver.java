package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

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
        LogUtils.w("BaseObserver---","onSubscribe--");
    }

    @Override
    public void onNext(BaseBean<T> baseBean) {
        if (mObserver != null) {
            mObserver.onNext(baseBean);
        }
        EventBus.getDefault().post(baseBean);
        LogUtils.w("BaseObserver---","onNext--");
    }




    @Override
    public void onError(Throwable e) {
        if (mObserver != null) {
            mObserver.onError(e);
        }
        BaseBean baseBean = new BaseBean();
        baseBean.code = IApi.Code.SERVICE_ERROR;
        baseBean.message = "请求超时";
        EventBus.getDefault().post(baseBean);
        LogUtils.w("BaseObserver---","onError--");
    }

    @Override
    public void onComplete() {
        LogUtils.w("BaseObserver---","onComplete--");
    }

    public interface Observer<T> {

        void onNext(BaseBean<T> t);

        void onError(Throwable e);

    }
}
