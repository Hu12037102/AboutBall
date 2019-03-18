package com.work.guaishouxingqiu.aboutball.login.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestLoginBean;
import com.work.guaishouxingqiu.aboutball.login.bean.UserBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/7 11:20
 * 更新时间: 2019/3/7 11:20
 * 描述: 手机号码登录契约
 */
public interface LoginContract {
    interface View extends MessageContract.View {
        void loginSucceedResult(BaseBean<LoginResultBean> bean);
        void resultUserAccount(BaseBean<UserBean> bean);
    }

    interface Presenter extends MessageContract.Presenter {
        void login(@NonNull RequestLoginBean loginBean);

        void loadUserAccount();
    }
}
