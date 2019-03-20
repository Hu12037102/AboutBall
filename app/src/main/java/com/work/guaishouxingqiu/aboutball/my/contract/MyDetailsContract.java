package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:20
 * 更新时间: 2019/3/20 9:20
 * 描述:我的详情契约
 */
public interface MyDetailsContract {
    interface View extends IBaseView {
        void resultUpdateSex(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {
        void updateSex(int sexType);
    }
}
