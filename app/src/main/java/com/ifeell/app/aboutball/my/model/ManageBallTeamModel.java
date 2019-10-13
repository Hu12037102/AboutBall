package com.ifeell.app.aboutball.my.model;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseModel;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.my.MyService;
import com.ifeell.app.aboutball.my.bean.RequestManageBallTeamBean;
import com.ifeell.app.aboutball.venue.VenueService;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:46
 * 更新时间: 2019/4/24 11:46
 * 描述:管理球队model
 */
public class ManageBallTeamModel extends BaseModel {
    public void loadBallListType(BaseObserver<List<ResultTypeBean>> observable) {
        mRetrofitManger.create(VenueService.class)
                .getBallTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observable);
    }

    public void createTeam(RequestManageBallTeamBean requestBean, BaseObserver<BaseDataBean<Long>> observer) {
        mRetrofitManger.create(MyService.class)
                .manageBallTeam(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void editTeam(RequestManageBallTeamBean requestBean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(MyService.class)
                .editTeamInfo(requestBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
