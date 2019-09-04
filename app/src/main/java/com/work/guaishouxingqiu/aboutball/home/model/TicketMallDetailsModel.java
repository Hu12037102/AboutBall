package com.work.guaishouxingqiu.aboutball.home.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.commonality.model.LoginOrShareModel;
import com.work.guaishouxingqiu.aboutball.home.HomeService;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultDoorTicketDetailsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultGameTicketDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/3 11:02
 * 更新时间: 2019/9/3 11:02
 * 描述: 购票详情model
 */
public class TicketMallDetailsModel extends LoginOrShareModel {

    public void loadGameTicketDetails(long skuId, BaseObserver<ResultGameTicketDetailsBean> observer) {
        mRetrofitManger.create(HomeService.class)
                .getGameTicketDetails(skuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    public void loadDoorTicketDetails(long skuId,BaseObserver<ResultDoorTicketDetailsBean> observer) {
        mRetrofitManger.create(HomeService.class)
                .getDoorTicketDetails(skuId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
