package com.work.guaishouxingqiu.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallContract;
import com.work.guaishouxingqiu.aboutball.home.model.TicketMallModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/27 11:27
 * 更新时间: 2019/8/27 11:27
 * 描述:售票商城P
 */
public class TicketMallPresenter extends BasePresenter<TicketMallContract.View, TicketMallModel>
        implements TicketMallContract.Presenter {
    public TicketMallPresenter(@NonNull TicketMallContract.View view) {
        super(view);
    }

    @Override
    protected TicketMallModel createModel() {
        return new TicketMallModel();
    }

    @Override
    public void start() {

    }
}
