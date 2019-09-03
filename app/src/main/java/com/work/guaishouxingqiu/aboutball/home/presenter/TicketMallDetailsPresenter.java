package com.work.guaishouxingqiu.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.TicketMallDetailsContract;
import com.work.guaishouxingqiu.aboutball.home.model.TicketMallDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:06
 * 更新时间: 2019/9/3 11:06
 * 描述: 购票详情P
 */
public class TicketMallDetailsPresenter extends BasePresenter<TicketMallDetailsContract.View, TicketMallDetailsModel>
        implements TicketMallDetailsContract.Presenter {
    public TicketMallDetailsPresenter(@NonNull TicketMallDetailsContract.View view) {
        super(view);
    }

    @Override
    protected TicketMallDetailsModel createModel() {
        return new TicketMallDetailsModel();
    }

    @Override
    public void start() {

    }
}
