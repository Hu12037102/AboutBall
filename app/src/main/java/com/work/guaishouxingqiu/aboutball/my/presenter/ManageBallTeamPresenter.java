package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.ManageBallTeamContract;
import com.work.guaishouxingqiu.aboutball.my.model.ManageBallTeamModel;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:45
 * 更新时间: 2019/4/24 11:45
 * 描述: 管理球队P
 */
public class ManageBallTeamPresenter extends BasePresenter<ManageBallTeamContract.View,ManageBallTeamModel>
implements ManageBallTeamContract.Presenter{
    public ManageBallTeamPresenter(@NonNull ManageBallTeamContract.View view) {
        super(view);
    }

    @Override
    protected ManageBallTeamModel createModel() {
        return new ManageBallTeamModel();
    }

    @Override
    public void start() {

    }
    public void loadBallTypeList(){
        mModel.loadBallListType(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultTypeBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTypeBean>> bean) {
                if (mView != null && bean.result != null) {
                    mView.resultBallTypeList(bean.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
