package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.login.contract.MessageContract;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePasswordBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/16 14:46
 * 更新时间: 2019/4/16 14:46
 * 描述:更新密码契约
 */
public interface UpdatePasswordContract {
    interface View extends MessageContract.View {
        void resultPasswordSucceed(String token);
    }

    interface Presenter extends MessageContract.Presenter {
        void updatePassword(RequestUpdatePasswordBean bean);
    }
}
