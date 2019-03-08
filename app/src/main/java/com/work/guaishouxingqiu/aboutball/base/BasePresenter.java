package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:28
 * 更新时间: 2019/3/4 13:28
 * 描述:
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter, IBaseModelCallback {
    protected CompositeDisposable mCompositeDisposable;
    protected V mView;

    protected M mModel;


    protected abstract M createModel();

    public BasePresenter(@NonNull V view) {
        mView = DataUtils.checkData(view);
        mCompositeDisposable = new CompositeDisposable();
        mModel = createModel();


    }

    @Override
    public void sendMessageCode(@NonNull String phoneNumber, int type) {
        mModel.sendMessageCode(phoneNumber, type, new BaseObserver(mCompositeDisposable));
    }

    private void defaultShowResult(BaseBean baseBean) {
        switch (baseBean.code) {
            case IApi.Code.SUCCEED:
                mView.showToast(baseBean.message);
                break;
        }
    }

    @Override
    public void deathPresenter() {

        if (mView != null) {
            mView = null;
        }
        if (mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        if (mModel != null) {
            mModel = null;
        }

    }
}
