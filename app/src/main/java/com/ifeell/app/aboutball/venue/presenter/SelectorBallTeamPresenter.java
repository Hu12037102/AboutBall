package com.ifeell.app.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.venue.bean.ResultMyBallTeamBean;
import com.ifeell.app.aboutball.venue.contract.SelectorBallTeamContract;
import com.ifeell.app.aboutball.venue.model.SelectorBallTeamModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/5 14:29
 * 更新时间: 2019/5/5 14:29
 * 描述:选择球队P
 */
public class SelectorBallTeamPresenter extends BasePresenter<SelectorBallTeamContract.View, SelectorBallTeamModel>
        implements SelectorBallTeamContract.Presenter {
    public SelectorBallTeamPresenter(@NonNull SelectorBallTeamContract.View view) {
        super(view);
    }

    @Override
    protected SelectorBallTeamModel createModel() {
        return new SelectorBallTeamModel();
    }

    @Override
    public void start() {
        mModel.loadMyBallTeam(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultMyBallTeamBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyBallTeamBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMyBallTeam(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
