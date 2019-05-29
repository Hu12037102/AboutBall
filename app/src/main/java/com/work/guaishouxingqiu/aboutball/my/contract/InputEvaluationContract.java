package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestInputEvaluationBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:42
 * 更新时间: 2019/5/27 10:42
 * 描述:填写评价契约
 */
public interface InputEvaluationContract {
    interface View extends IBaseView {
        void resultEvaluationSucceed();
    }

    interface Presenter extends IBasePresenter {
        void postEvaluationOpponent(RequestInputEvaluationBean requestBean);

        void postEvaluationReferee(RequestInputEvaluationBean mRequestBean);
    }
}