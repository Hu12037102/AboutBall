package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.ResultMyTicketsBean;
import com.ifeell.app.aboutball.my.contract.MyTicketsChildContract;
import com.ifeell.app.aboutball.my.model.MyTicketsChildModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:37
 * 更新时间: 2019/9/4 17:37
 * 描述:我的契约P
 */
public class MyTicketsChildPresenter extends BasePresenter<MyTicketsChildContract.View, MyTicketsChildModel>
        implements MyTicketsChildContract.Presenter {
    public MyTicketsChildPresenter(@NonNull MyTicketsChildContract.View view) {
        super(view);
    }

    @Override
    protected MyTicketsChildModel createModel() {
        return new MyTicketsChildModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMyTicketsList(int exchange) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyTicketsList(mPageNum, mPageSize, exchange, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultMyTicketsBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyTicketsBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultMyTicketList(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
