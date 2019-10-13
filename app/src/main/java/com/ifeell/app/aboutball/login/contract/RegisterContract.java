package com.ifeell.app.aboutball.login.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.login.bean.RegisterResultBean;
import com.ifeell.app.aboutball.login.bean.RequestRegisterBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 11:51
 * 更新时间: 2019/3/11 11:51
 * 描述: 注册契约
 */
public interface RegisterContract {
    interface View extends IBaseView {
        void registerResult(@NonNull BaseBean<RegisterResultBean> bean);
    }

    interface Presenter extends IBasePresenter {
        void register(RequestRegisterBean requestBean);
        void forgetPassword(RequestRegisterBean requestBean);
    }
}
