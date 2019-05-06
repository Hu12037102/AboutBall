package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestApplyRefereeBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 13:30
 * 更新时间: 2019/5/6 13:30
 * 描述:申请成为裁判契约
 */
public interface ApplyBecomeRefereeContract {
    interface View extends IBaseView {
        void resultCommitRefereeCredential();
    }

    interface Presenter extends IBasePresenter {
        void commitRefereeCredential(RequestApplyRefereeBean requestBean);
    }
}
