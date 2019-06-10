package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.model.MatchRefereeResultModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:09
 * 更新时间: 2019/6/4 13:09
 * 描述:赛况记录P
 */
public class MatchRefereeResultPresenter extends BasePresenter<MatchRefereeResultContract.View, MatchRefereeResultModel>
        implements MatchRefereeResultContract.Presenter {
    public MatchRefereeResultPresenter(@NonNull MatchRefereeResultContract.View view) {
        super(view);
    }

    @Override
    protected MatchRefereeResultModel createModel() {
        return new MatchRefereeResultModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMatchRecord(long agreeId) {
        mModel.loadMatchRecord(agreeId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultMatchRefereeResultBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMatchRefereeResultBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchRecord(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
