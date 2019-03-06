package com.work.guaishouxingqiu.aboutball.base;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:28
 * 更新时间: 2019/3/4 13:28
 * 描述:
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter, IBaseModelCallback {

    protected V mView;

    protected M mModel;

    protected abstract M createModel();

    public BasePresenter(@NonNull V view) {
        mView = DataUtils.checkData(view);
        mModel = createModel();
    }


    @Override
    public void deathPresenter() {
        if (mView != null) {
            mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
    }
}
