package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.ifeell.app.aboutball.my.contract.BallTeamMemberContract;
import com.ifeell.app.aboutball.my.model.BallTeamMemberModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:26
 * 更新时间: 2019/4/26 9:26
 * 描述:球队信息P
 */
public class BallTeamMemberPresenter extends LoginOrSharePresenter<BallTeamMemberContract.View, BallTeamMemberModel>
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

    @Override
    public void deleteMember(long teamId, long playId,int position) {
       // mModel.deleteMember(teamId,playId);
        mModel.deleteMember(teamId, playId, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null && t.result.code == IApi.Code.SUCCEED) {
                    mView.resultDeleteMember(position);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
