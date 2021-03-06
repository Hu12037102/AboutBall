package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestUpdateNameBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:14
 * 更新时间: 2019/3/20 11:14
 * 描述: 修改用户姓名model
 */
public class AlterNameModel extends BaseModel{
    public void alterName(RequestUpdateNameBean bean, BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(MyService.class)
                .updateAccountSex(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
