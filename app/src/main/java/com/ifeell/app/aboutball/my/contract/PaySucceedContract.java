package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 11:09
 * 更新时间: 2019/5/22 11:09
 * 描述:支付成功契约
 */
public interface PaySucceedContract {
    interface View extends LoginOrShareContract.View{}
    interface Presenter extends LoginOrShareContract.Presenter{}
}
