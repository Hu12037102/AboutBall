package com.ifeell.app.aboutball.home.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.home.bean.MainTabBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 14:14
 * 更新时间: 2019/3/4 14:14
 * 描述:
 */
public interface MainContract {
    interface View extends IBaseView {
        void loadMainTabResult(List<MainTabBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMainTab();
    }
}
