package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestEditAddressBean;
import com.ifeell.app.aboutball.my.bean.RequestNewAddressBean;
import com.ifeell.app.aboutball.my.bean.ResultMyAddress;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 14:01
 * 更新时间: 2019/4/8 14:01
 * 描述:编辑我的地址model
 */
public class EditMyAddressModel extends BaseModel {

    public void saveNewAddress(RequestNewAddressBean bean, BaseObserver<BaseDataBean> observer) {
        mRetrofitManger.create(MyService.class)
                .postNewAddress(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void getMyAddress(BaseObserver<ResultMyAddress> observer){
        mRetrofitManger.create(MyService.class)
                .getMyAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void saveEditAddress(RequestEditAddressBean bean, BaseObserver<BaseDataBean> observer) {
        mRetrofitManger.create(MyService.class)
                .postEditAddress(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
