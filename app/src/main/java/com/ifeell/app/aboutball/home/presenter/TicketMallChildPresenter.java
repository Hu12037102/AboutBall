package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.bean.ResultTicketMallBean;
import com.ifeell.app.aboutball.home.contract.TicketMallChildContract;
import com.ifeell.app.aboutball.home.model.TicketMallChildModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/30 9:39
 * 更新时间: 2019/8/30 9:39
 * 描述:售票商城fragmentP
 */
public class TicketMallChildPresenter extends BasePresenter<TicketMallChildContract.View, TicketMallChildModel>
        implements TicketMallChildContract.Presenter {
    public TicketMallChildPresenter(@NonNull TicketMallChildContract.View view) {
        super(view);
    }

    @Override
    protected TicketMallChildModel createModel() {
        return new TicketMallChildModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTickMallList(int statusId) {
        mModel.loadTickMallList(statusId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultTicketMallBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTicketMallBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultTickMallData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
