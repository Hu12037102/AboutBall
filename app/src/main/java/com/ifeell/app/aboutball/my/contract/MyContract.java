package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:48
 * 更新时间: 2019/3/6 15:48
 * 描述: 我的契约
 */
public interface MyContract {
    interface View extends LoginOrShareContract.View {
       // void resultRefereeStatus(Integer status);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
    //    void judgeRefereeStatus();

    }
}
