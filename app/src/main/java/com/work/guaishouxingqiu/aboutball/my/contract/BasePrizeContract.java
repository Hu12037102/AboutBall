package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 17:00
 * 更新时间: 2019/4/11 17:00
 * 描述:
 */
public interface BasePrizeContract {
    interface View extends IBaseView {
        void resultMyPrize(ResultPrizeBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadMyPrize(int status);
    }
}
