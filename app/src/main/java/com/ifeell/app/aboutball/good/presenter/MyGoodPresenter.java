package com.ifeell.app.aboutball.good.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.good.bean.ResultMyGoodBean;
import com.ifeell.app.aboutball.good.contract.MyGoodContract;
import com.ifeell.app.aboutball.good.model.MyGoodModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 14:55
 * 更新时间: 2019/9/19 14:55
 * 描述:我的订单Presenter
 */
public class MyGoodPresenter extends BasePresenter<MyGoodContract.View, MyGoodModel> implements MyGoodContract.Presenter {
    public MyGoodPresenter(@NonNull MyGoodContract.View view) {
        super(view);
    }

    @Override
    protected MyGoodModel createModel() {
        return new MyGoodModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMyGoodList(int status) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyGoodList(mPageNum, mPageSize, status, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultMyGoodBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyGoodBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultMyGoodList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
