package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:28
 * 更新时间: 2019/3/4 13:28
 * 描述:
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter, IBaseModelCallback {
    public int mPageNum = Contast.DEFAULT_PAGE_NUM;
    public int mPageSize = Contast.DEFAULT_PAGE_SIZE;
    public  boolean isRefresh = true;
    protected CompositeDisposable mCompositeDisposable;
    public V mView;

    protected M mModel;


    protected abstract M createModel();

    public BasePresenter(@NonNull V view) {
        mView = DataUtils.checkData(view);
        mCompositeDisposable = new CompositeDisposable();
        mModel = createModel();


    }


    private void defaultShowResult(BaseBean baseBean) {
        if (mView != null) {
            mView.showToast(baseBean.message);
        }
    }

    @Override
    public void deathPresenter() {


        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        if (mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }

        if (mView != null) {
            mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
    }


}
