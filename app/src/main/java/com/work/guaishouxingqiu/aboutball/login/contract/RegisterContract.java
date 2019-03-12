package com.work.guaishouxingqiu.aboutball.login.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestRegisterBean;

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
        void register(RequestRegisterBean mRequestBean);
    }
}
