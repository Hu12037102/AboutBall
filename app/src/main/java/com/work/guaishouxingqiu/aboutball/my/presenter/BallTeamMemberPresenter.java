package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMemberContract;
import com.work.guaishouxingqiu.aboutball.my.model.BallTeamMemberModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:26
 * 更新时间: 2019/4/26 9:26
 * 描述:球队信息P
 */
public class BallTeamMemberPresenter extends BasePresenter<BallTeamMemberContract.View, BallTeamMemberModel>
        implements BallTeamMemberContract.Presenter {
    public BallTeamMemberPresenter(@NonNull BallTeamMemberContract.View view) {
        super(view);
    }

    @Override
    protected BallTeamMemberModel createModel() {
        return new BallTeamMemberModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMemberDetails(long teamId) {
        mModel.loadMemberDetails(teamId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultTeamDetailsMemberBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTeamDetailsMemberBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMemberDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
