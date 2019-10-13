package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.ResultMyGameRecordBean;
import com.ifeell.app.aboutball.my.contract.TeamMatchResultContract;
import com.ifeell.app.aboutball.my.model.TeamMatchResultModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况P
 */
public class TeamMatchResultPresenter extends BasePresenter<TeamMatchResultContract.View, TeamMatchResultModel>
        implements TeamMatchResultContract.Presenter {
    public TeamMatchResultPresenter(@NonNull TeamMatchResultContract.View view) {
        super(view);
    }

    @Override
    protected TeamMatchResultModel createModel() {
        return new TeamMatchResultModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMyGameResultDetails(long agreeId) {
        mModel.loadMyGameResultDetails(agreeId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultMyGameRecordBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyGameRecordBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    t.result.add(0,new ResultMyGameRecordBean(true));
                    mView.resultMyGameResultDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
