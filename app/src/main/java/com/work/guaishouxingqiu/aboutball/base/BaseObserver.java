package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 11:54
 * 更新时间: 2019/3/8 11:54
 * 描述: BaseObserver
 */
public class BaseObserver<T> implements Observer<BaseBean<T>> {

    private BasePresenter mPresenter;
    private BaseObserver.Observer<T> mObserver;


    public BaseObserver(@NonNull BasePresenter presenter, BaseObserver.Observer<T> observer) {
        this.mPresenter = presenter;
        this.mObserver = observer;
    }

    public BaseObserver(boolean isShowLoadingView, @NonNull BasePresenter presenter, BaseObserver.Observer<T> observer) {
        this.mPresenter = presenter;
        this.mObserver = observer;
        if (isShowLoadingView && mPresenter.mView != null) {
            mPresenter.mView.showLoadingView();
        }
    }

    public BaseObserver(@NonNull BasePresenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (mPresenter != null) {
            mPresenter.mCompositeDisposable.add(d);
        }
    }

    @Override
    public void onNext(BaseBean<T> baseBean) {
        if (mObserver != null) {
            mObserver.onNext(baseBean);
        }
        defaultPresenter(baseBean);
        LogUtils.w("BaseObserver---", "onNext--");
    }

    private void defaultPresenter(BaseBean baseBean) {
        if (mPresenter == null || mPresenter.mView == null) {
            return;
        }
        mPresenter.mView.resultBaseData(baseBean);
        if (!DataUtils.isEmpty(baseBean.message)) {
            mPresenter.mView.showToast(baseBean.message);
        } else {
            Object object = baseBean.result;
            if (object instanceof BaseDataBean) {
                BaseDataBean baseDataBean = (BaseDataBean) object;
                if (!DataUtils.isEmpty(baseDataBean.message)) {
                    mPresenter.mView.showToast(baseDataBean.message);
                }
            }
        }
        mPresenter.mView.dismissLoadingView();
    }


    @Override
    public void onError(Throwable e) {
        if (mObserver != null) {
            mObserver.onError(e);
        }
        if (e instanceof HttpException) {
            mPresenter.mView.showToast("网络异常！"+((HttpException) e).code());
        } else {
            BaseBean baseBean = new BaseBean();
            baseBean.code = IApi.Code.SERVICE_ERROR;
            baseBean.message = "请求超时";
            defaultPresenter(baseBean);
        }
        mPresenter.mView.dismissLoadingView();
    }

    @Override
    public void onComplete() {
        LogUtils.w("BaseObserver---", "onComplete--");
    }

    public interface Observer<T> {

        void onNext(BaseBean<T> t);

        void onError(Throwable e);

    }

}
