package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.bean.ResultHomeTabBean;
import com.ifeell.app.aboutball.home.contract.HomeContract;
import com.ifeell.app.aboutball.home.model.HomeModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:01
 * 更新时间: 2019/3/6 16:01
 * 描述:首页P
 */
public class HomePresenter extends BasePresenter<HomeContract.View, HomeModel> implements HomeContract.Presenter {
    public HomePresenter(@NonNull HomeContract.View view) {
        super(view);
    }

    @Override
    protected HomeModel createModel() {
        return new HomeModel();
    }

    @Override
    public void start() {
        mModel.loadHomeTab(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultHomeTabBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultHomeTabBean>> t) {
                if (mView != null) {
                    mView.resultTabData(t);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.resultDataError();
            }
        }));
    }
}
