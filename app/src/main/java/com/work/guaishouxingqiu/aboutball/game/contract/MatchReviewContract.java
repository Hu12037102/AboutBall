package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultReviewBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/27 11:20
 * 更新时间: 2019/6/27 11:20
 * 描述:比赛回顾契约
 */
public interface MatchReviewContract {
    interface View extends IBaseView {
        void resultReviewData(List<ResultReviewBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}
