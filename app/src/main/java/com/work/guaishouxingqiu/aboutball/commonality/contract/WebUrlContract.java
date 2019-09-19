package com.work.guaishouxingqiu.aboutball.commonality.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.presenter.LoginOrSharePresenter;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 11:05
 * 更新时间: 2019/9/19 11:05
 * 描述: 包含Url传递的契约
 */
public interface WebUrlContract {
    interface View extends LoginOrShareContract.View {

    }

    interface Presenter extends LoginOrShareContract.Presenter {
    }
}
