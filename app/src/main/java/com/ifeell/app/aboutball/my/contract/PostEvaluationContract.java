package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultInputEvaluationBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 11:39
 * 更新时间: 2019/5/27 11:39
 * 描述:发表评论
 */
public interface PostEvaluationContract {
    interface View extends IBaseView{
        void resultEvaluation(List<ResultInputEvaluationBean> data);
    }
    interface Presenter extends IBasePresenter{
        void loadTeamEvaluation(long teamId);

        void loadRefereeEvaluation(long refereeId);

        void loadMyRefereeEvaluation();
    }
}
