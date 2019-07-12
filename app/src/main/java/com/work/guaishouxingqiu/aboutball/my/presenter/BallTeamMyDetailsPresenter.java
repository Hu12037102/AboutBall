package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestTeamMyDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamMyDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/28 15:29
 * 更新时间: 2019/4/28 15:29
 * 描述:对内个人信息P
 */
public class BallTeamMyDetailsPresenter extends BasePresenter<BallTeamMyDetailsContract.View,
        BallTeamMyDetailsModel> implements BallTeamMyDetailsContract.Presenter {
    public BallTeamMyDetailsPresenter(@NonNull BallTeamMyDetailsContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamMyDetailsModel createModel() {
        return new BallTeamMyDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void saveMyDetails(RequestTeamMyDetailsBean bean) {
        mModel.saveMyDetails(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result.code == IApi.Code.SUCCEED) {
                    mView.resultMyDetailsSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
