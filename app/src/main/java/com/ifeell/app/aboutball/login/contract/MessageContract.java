package com.ifeell.app.aboutball.login.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 10:38
 * 更新时间: 2019/3/11 10:38
 * 描述: 短信契约
 */
public interface MessageContract {
    interface View extends IBaseView {
        void sendMessageCodeSucceedResult();

        void countDownTimeUpdate(long time);
        void countDownTimeComplete();
        void resultMessageCode();
    }

    interface Presenter extends IBasePresenter {
        void sendMessageCode(@NonNull String phoneNumber, int type);
        void countDownTime(int timeLength);
        void judgeMessageCode(String phone,String messageCode);
    }
}
