package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultPrizeBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:00
 * 更新时间: 2019/4/11 17:00
 * 描述:
 */
public interface BasePrizeContract {
    interface View extends IBaseView {
        void resultMyPrize(ResultPrizeBean bean);
        void resultHasAddress(int type);
    }

    interface Presenter extends IBasePresenter {
        void loadMyPrize(int status);
    }
}
