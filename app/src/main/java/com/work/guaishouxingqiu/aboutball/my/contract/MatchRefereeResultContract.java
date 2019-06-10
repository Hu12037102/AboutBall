package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 11:55
 * 更新时间: 2019/6/4 11:55
 * 描述:赛况记录契约
 */
public interface MatchRefereeResultContract {
    interface View extends IBaseView {
        void resultMatchRecord(List<ResultMatchRefereeResultBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMatchRecord(long agreeId);
    }
}
