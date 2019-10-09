package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultRefereeRecordBean;

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
        void loadRefereeRecord(long mRefereeId);
    }
}
