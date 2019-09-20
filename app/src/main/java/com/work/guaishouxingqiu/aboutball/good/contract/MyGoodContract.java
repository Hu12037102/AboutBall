package com.work.guaishouxingqiu.aboutball.good.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.good.bean.ResultMyGoodBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 14:14
 * 更新时间: 2019/9/19 14:14
 * 描述: 我的订单列表
 */
public interface MyGoodContract {
    interface View extends IBaseView {
        void resultMyGoodList(List<ResultMyGoodBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadMyGoodList(int status);
    }
}