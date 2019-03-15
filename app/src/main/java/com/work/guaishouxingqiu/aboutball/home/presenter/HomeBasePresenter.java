package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.HomeBaseContract;
import com.work.guaishouxingqiu.aboutball.home.model.HomeBaseModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;

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
            pageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadData(pageNum, pageSize,typeId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultNewsBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsBean>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    pageNum++;
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
