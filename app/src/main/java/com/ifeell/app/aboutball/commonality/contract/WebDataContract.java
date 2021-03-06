package com.ifeell.app.aboutball.commonality.contract;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 10:27
 * 更新时间: 2019/4/11 10:27
 * 描述:纯web契约
 */
public interface WebDataContract {
    interface View extends LoginOrShareContract.View{}
    interface Presenter extends LoginOrShareContract.Presenter{}
}
