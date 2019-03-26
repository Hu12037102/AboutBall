package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.RequestGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameCommentContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameCommentModel;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:49
 * 更新时间: 2019/3/25 9:49
 * 描述:比赛-评论P
 */
public class GameCommentPresenter extends BasePresenter<GameCommentContract.View, GameCommentModel>
        implements GameCommentContract.Presenter {
    public GameCommentPresenter(@NonNull GameCommentContract.View view) {
        super(view);
    }

    @Override
    protected GameCommentModel createModel() {
        return new GameCommentModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadCommentData(int gameId) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mPageSize = 30;
        mModel.loadCommentData(gameId, mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultGameCommentBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameCommentBean>> bean) {
                if (bean.code == IApi.Code.SUCCEED) {
                    mPageNum++;
                    mView.resultCommentData(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void sendCommentMessage(RequestGameCommentBean bean) {
        mModel.senCommendMessage(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultCommentMessage();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
