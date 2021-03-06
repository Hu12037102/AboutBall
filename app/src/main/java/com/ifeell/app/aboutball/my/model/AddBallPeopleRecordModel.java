package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestAddRecordBean;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 17:24
 * 更新时间: 2019/6/4 17:24
 * 描述:添加球员记录model
 */
public class AddBallPeopleRecordModel extends BaseModel {
    public void loadMemberDetails(long teamId, BaseObserver<List<ResultTeamDetailsMemberBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadBallTeamMember(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void addRefereePlayerRecord(RequestAddRecordBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .addRefereePlayerRecord(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void deleteRefereeRecord(long outsId,BaseObserver< BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .deleteRefereeMathRecord(outsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void editRefereePlayerRecord(RequestAddRecordBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .editRefereePlayerRecord(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
