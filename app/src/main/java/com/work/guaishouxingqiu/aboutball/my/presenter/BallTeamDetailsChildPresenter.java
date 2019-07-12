package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsChildContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamDetailsChildModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 16:30
 * 更新时间: 2019/4/25 16:30
 * 描述:球队信息fragmentP
 */
public class BallTeamDetailsChildPresenter extends BasePresenter<BallTeamDetailsChildContract.View,
        BallTeamDetailsChildModel> implements BallTeamDetailsChildContract.Presenter {


    public BallTeamDetailsChildPresenter(@NonNull BallTeamDetailsChildContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamDetailsChildModel createModel() {
        return new BallTeamDetailsChildModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadDetails(long mTeamId) {
        mModel.loadDetails(mTeamId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultBallDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultBallDetailsBean> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
