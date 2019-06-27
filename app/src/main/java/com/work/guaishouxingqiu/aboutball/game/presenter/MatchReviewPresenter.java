package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultReviewBean;
import com.work.guaishouxingqiu.aboutball.game.contract.MatchReviewContract;
import com.work.guaishouxingqiu.aboutball.game.model.MatchReviewModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 11:22
 * 更新时间: 2019/6/27 11:22
 * 描述:
 */
public class MatchReviewPresenter extends BasePresenter<MatchReviewContract.View, MatchReviewModel>
        implements MatchReviewContract.Presenter {

    public MatchReviewPresenter(@NonNull MatchReviewContract.View view) {
        super(view);
    }

    @Override
    protected MatchReviewModel createModel() {
        return new MatchReviewModel();
    }

    @Override
    public void start() {
        mModel.loadMatchReviewData(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultReviewBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultReviewBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultReviewData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
