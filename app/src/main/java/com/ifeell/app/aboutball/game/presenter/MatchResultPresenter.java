package com.ifeell.app.aboutball.game.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.game.bean.ResultGameDataBean;
import com.ifeell.app.aboutball.game.contract.MatchResultContract;
import com.ifeell.app.aboutball.game.model.MatchResultModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:33
 * 更新时间: 2019/3/25 9:33
 * 描述:赛况P
 */
public class MatchResultPresenter extends BasePresenter<MatchResultContract.View, MatchResultModel>
        implements MatchResultContract.Presenter {
    public MatchResultPresenter(@NonNull MatchResultContract.View view) {
        super(view);
    }

    @Override
    protected MatchResultModel createModel() {
        return new MatchResultModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(int gameId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadData(gameId, mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<ResultGameDataBean>() {
            @Override
            public void onNext(BaseBean<ResultGameDataBean> bean) {
                mPageNum++;
                if (DataUtils.isResultSure(bean)) {
                    mView.resultData(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
