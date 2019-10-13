package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.community.bean.ResultUserDynamicBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:07
 * 更新时间: 2019/6/19 9:07
 * 描述:我的动态契约
 */
public interface MyDynamicContract {
    interface View extends LoginOrShareContract.View {
        void resultMyDynamic(ResultUserDynamicBean bean);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadMyDynamic(long userId);

    }
}
