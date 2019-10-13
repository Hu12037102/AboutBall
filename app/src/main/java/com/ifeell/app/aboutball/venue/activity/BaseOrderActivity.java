package com.ifeell.app.aboutball.venue.activity;

import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.venue.contract.BaseOrderContrast;
import com.ifeell.app.aboutball.venue.presenter.BaseOrderPresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 11:13
 * 更新时间: 2019/5/16 11:13
 * 描述:订单BaseActivity基类
 */
public abstract class BaseOrderActivity<P extends BaseOrderPresenter> extends BaseActivity<P> implements BaseOrderContrast.View {


    @Override
    public void resultOrderDetails(ResultOrderDetailsBean bean) {

    }

    @Override
    public void resultBandPhoneNumber() {

    }

    @Override
    public void resultCancelOrderSucceed() {

    }
}
