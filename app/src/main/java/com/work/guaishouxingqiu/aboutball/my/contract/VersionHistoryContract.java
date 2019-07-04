package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultVersionHistoryBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/4 10:56
 * 更新时间: 2019/7/4 10:56
 * 描述:历史版本契约
 */
public interface VersionHistoryContract {
    interface View extends IBaseView {
        void resultVersionHistoryData(List<ResultVersionHistoryBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}
