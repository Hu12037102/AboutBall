package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestUpdateNameBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:13
 * 更新时间: 2019/3/20 11:13
 * 描述:修改用户姓名契约
 */
public interface AlterNameContract {
    interface View extends IBaseView {
        void resultAlterName();
    }

    interface Presenter extends IBasePresenter {
        void alterName(RequestUpdateNameBean bean);
    }
}
