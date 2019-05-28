package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.PostEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.model.PostEvaluationModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 11:41
 * 更新时间:2019/5/27 11:41
 * 描述:发表评论P
 */
public class PostEvaluationPresenter extends BasePresenter<PostEvaluationContract.View,
        PostEvaluationModel> implements PostEvaluationContract.Presenter {
    public PostEvaluationPresenter(@NonNull PostEvaluationContract.View view) {
        super(view);
    }

    @Override
    protected PostEvaluationModel createModel() {
        return new PostEvaluationModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTeamEvaluation(long teamId) {
        mModel.loadTeamEvaluation(teamId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultInputEvaluationBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultInputEvaluationBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultEvaluation(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadRefereeEvaluation(long refereeId) {
        mModel.loadRefereeEvaluation(refereeId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultInputEvaluationBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultInputEvaluationBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultEvaluation(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
