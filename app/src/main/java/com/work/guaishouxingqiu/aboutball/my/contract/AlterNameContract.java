package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:13
 * 更新时间: 2019/3/20 11:13
 * 描述:修改用户姓名契约
 */
public interface AlterNameContract {
    interface View extends IBaseView {
        void resultAlterName();
    }

    interface Presenter extends IBasePresenter {
        void alterName(RequestUpdateNameBean bean);
    }
}
