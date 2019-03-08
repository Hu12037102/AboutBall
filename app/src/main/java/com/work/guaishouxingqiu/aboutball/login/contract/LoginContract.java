package com.work.guaishouxingqiu.aboutball.login.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:20
 * 更新时间: 2019/3/7 11:20
 * 描述: 手机号码登录契约
 */
public interface LoginContract {
    interface View extends IBaseView {
    }

    interface Presenter extends IBasePresenter {
        void login(@NonNull RequestLoginBean loginBean);
    }
}
