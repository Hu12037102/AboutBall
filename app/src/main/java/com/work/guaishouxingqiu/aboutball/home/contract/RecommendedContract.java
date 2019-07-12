package com.work.guaishouxingqiu.aboutball.home.contract;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 17:28
 * 更新时间: 2019/3/12 17:28
 * 描述:推荐契约
 */
public interface RecommendedContract {
    interface View extends HomeBaseContract.View {
        void resultBannerData(BaseBean<ResultRecommendDataBean> bean);


    }

    interface Presenter extends HomeBaseContract.Presenter{
        void loadHead(RequestRecommendDataBean registerBean);

    }
}
