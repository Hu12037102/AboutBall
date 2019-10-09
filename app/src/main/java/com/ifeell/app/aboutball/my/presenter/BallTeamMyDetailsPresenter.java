package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.RequestTeamMyDetailsBean;
import com.ifeell.app.aboutball.my.contract.BallTeamMyDetailsContract;
import com.ifeell.app.aboutball.my.model.BallTeamMyDetailsModel;

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
