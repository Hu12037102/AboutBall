package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:07
 * 更新时间: 2019/5/9 10:07
 * 描述:我的裁判记录契约
 */
public interface MyRefereeRecordChildContract {
    interface View extends IBaseView {
        void resultMyRefereeRecord(List<ResultRefereeRecordBean> data);
    }

    interface Presenter extends IBasePresenter {
    }
}
