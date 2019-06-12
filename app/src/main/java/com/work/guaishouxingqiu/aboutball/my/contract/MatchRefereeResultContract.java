package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestActionRecordsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordDetailsBean;

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
        void resultActionRecord();
        void resultRecordDetails(ResultRefereeRecordDetailsBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadMatchRecord(long agreeId);

        void goActionRecord(RequestActionRecordsBean mRequestBean);

        void loadRecordDetails(long agreeId);

    }
}
