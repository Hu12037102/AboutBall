package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.bean.ResultReviewBean;
import com.ifeell.app.aboutball.game.contract.MatchReviewContract;
import com.ifeell.app.aboutball.game.model.MatchReviewModel;
import com.ifeell.app.aboutball.util.DataUtils;

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
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMatchReviewData(mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultReviewBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultReviewBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultReviewData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
