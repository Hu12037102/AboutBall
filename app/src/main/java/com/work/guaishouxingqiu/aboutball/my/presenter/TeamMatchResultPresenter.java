package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyGameRecordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.TeamMatchResultContract;
import com.work.guaishouxingqiu.aboutball.my.model.TeamMatchResultModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

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
