package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.ResultInputEvaluationBean;
import com.ifeell.app.aboutball.my.contract.PostEvaluationContract;
import com.ifeell.app.aboutball.my.model.PostEvaluationModel;
import com.ifeell.app.aboutball.util.DataUtils;

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

    @Override
    public void loadMyRefereeEvaluation() {
        mModel.loadMyRefereeEvaluation(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultInputEvaluationBean>>() {
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
