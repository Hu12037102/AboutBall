package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyGameRecordBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/30
 * 更新时间: 2019/6/30
 * 描述:赛况契约
 */
public interface TeamMatchResultContract {
    interface View extends IBaseView {
        void resultMyGameResultDetails(List<ResultMyGameRecordBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMyGameResultDetails(long agreeId);

    }
}
