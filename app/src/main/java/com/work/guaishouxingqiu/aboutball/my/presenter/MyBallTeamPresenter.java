package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyBallTeamContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyBallTeamModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 9:46
 * 更新时间: 2019/4/24 9:46
 * 描述:我的球队P
 */
public class MyBallTeamPresenter extends BasePresenter<MyBallTeamContract.View, MyBallTeamModel> implements
        MyBallTeamContract.Presenter {
    public MyBallTeamPresenter(@NonNull MyBallTeamContract.View view) {
        super(view);
    }

    @Override
    protected MyBallTeamModel createModel() {
        return new MyBallTeamModel();
    }

    @Override
    public void start() {
        mModel.loadMyBallTeam(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultMyBallBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyBallBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMyBallTeam(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
