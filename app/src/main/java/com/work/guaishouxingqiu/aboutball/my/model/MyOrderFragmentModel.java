package com.work.guaishouxingqiu.aboutball.my.model;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.MyService;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderFragmentContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyOrderFragmentPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:24
 * 更新时间: 2019/5/14 10:24
 * 描述:我的订单fragmentModel
 */
public class MyOrderFragmentModel extends BaseModel {

    public void lordMyOrder(int orderStatus, int pageNum, int pageSize, BaseObserver<List<ResultMyOrderBean>>observer) {
        mRetrofitManger.create(MyService.class)
                .loadMyOrder(orderStatus,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
