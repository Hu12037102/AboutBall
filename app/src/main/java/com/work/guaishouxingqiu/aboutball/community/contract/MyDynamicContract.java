package com.work.guaishouxingqiu.aboutball.community.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:07
 * 更新时间: 2019/6/19 9:07
 * 描述:我的动态契约
 */
public interface MyDynamicContract {
    interface View extends LoginOrShareContract.View {
        void resultMyDynamic(List<ResultCommunityDataBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadMyDynamic();

    }
}
