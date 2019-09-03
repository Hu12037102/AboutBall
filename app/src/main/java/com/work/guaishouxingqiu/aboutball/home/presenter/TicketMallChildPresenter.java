package com.work.guaishouxingqiu.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultTicketMallBean;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallChildContract;
import com.work.guaishouxingqiu.aboutball.home.model.TicketMallChildModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;

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
