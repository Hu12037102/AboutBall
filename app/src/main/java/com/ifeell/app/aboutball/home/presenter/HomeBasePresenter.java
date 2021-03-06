package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.home.contract.HomeBaseContract;
import com.ifeell.app.aboutball.home.model.HomeBaseModel;
import com.ifeell.app.aboutball.http.IApi;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:39
 * 更新时间: 2019/3/15 9:39
 * 描述:
 */
public abstract class HomeBasePresenter<V extends HomeBaseContract.View, M extends HomeBaseModel> extends BasePresenter<V, M>
        implements HomeBaseContract.Presenter {
    public HomeBasePresenter(@NonNull V view) {
        super(view);
    }

    @Override
    public void loadData(int typeId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadData(mPageNum, mPageSize,typeId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultNewsBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsBean>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    mPageNum++;
                    if (mView != null) {
                        mView.resultData(bean);
                    }

                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }


}
