package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataBean;
import com.work.guaishouxingqiu.aboutball.game.contract.MatchResultContract;
import com.work.guaishouxingqiu.aboutball.game.model.MatchResultModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import java.util.List;

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
        mModel.loadData(gameId, mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultGameDataBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameDataBean>> bean) {
                mPageNum++;
                if (bean.code == IApi.Code.SUCCEED) {
                    mView.resultData(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
