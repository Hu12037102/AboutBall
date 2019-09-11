package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.SureOrderContract;
import com.work.guaishouxingqiu.aboutball.my.model.SureOrderModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/11 10:20
 * 更新时间: 2019/9/11 10:20
 * 描述:确认订单P
 */
public class SureOrderPresenter extends BasePresenter<SureOrderContract.View, SureOrderModel>
        implements SureOrderContract.Presenter {
    public SureOrderPresenter(@NonNull SureOrderContract.View view) {
        super(view);
    }

    @Override
    protected SureOrderModel createModel() {
        return new SureOrderModel();
    }

    @Override
    public void start() {

    }
}
