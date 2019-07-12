package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestManageBallTeamBean;
import com.work.guaishouxingqiu.aboutball.my.contract.ManageBallTeamContract;
import com.work.guaishouxingqiu.aboutball.my.model.ManageBallTeamModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:45
 * 更新时间: 2019/4/24 11:45
 * 描述: 管理球队P
 */
public class ManageBallTeamPresenter extends BasePresenter<ManageBallTeamContract.View, ManageBallTeamModel>
        implements ManageBallTeamContract.Presenter {
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

    public void loadBallTypeList() {
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

    @Override
    public void createTeam(RequestManageBallTeamBean requestBean) {
        mModel.createTeam(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<Long>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<Long>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultManageTeam(t.result.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void editTeam(RequestManageBallTeamBean requestBean) {
        mModel.editTeam(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED){
                    mView.resultEditTeam();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
