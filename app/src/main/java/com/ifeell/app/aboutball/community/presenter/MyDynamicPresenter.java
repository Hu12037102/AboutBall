package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.community.bean.ResultUserDynamicBean;
import com.ifeell.app.aboutball.community.contract.MyDynamicContract;
import com.ifeell.app.aboutball.community.model.MyDynamicModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:09
 * 更新时间: 2019/6/19 9:09
 * 描述:我的动态P
 */
public class MyDynamicPresenter extends LoginOrSharePresenter<MyDynamicContract.View, MyDynamicModel>
        implements MyDynamicContract.Presenter {
    public MyDynamicPresenter(@NonNull MyDynamicContract.View view) {
        super(view);
    }

    @Override
    protected MyDynamicModel createModel() {
        return new MyDynamicModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMyDynamic(long userId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyDynamic(mPageNum, mPageSize,userId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultUserDynamicBean>() {
            @Override
            public void onNext(BaseBean<ResultUserDynamicBean> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultMyDynamic(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
