package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.model.LoginOrShareModel;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:26
 * 更新时间: 2019/4/26 9:26
 * 描述:球队成员model
 */
public class BallTeamMemberModel extends LoginOrShareModel {
    public void loadMemberDetails(long teamId, BaseObserver<List<ResultTeamDetailsMemberBean>> observer) {
        mRetrofitManger.create(MyService.class)
                .loadBallTeamMember(teamId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void deleteMember(long teamId, long playId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .deleteMeber(teamId,playId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
