package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 14:01
 * 更新时间: 2019/3/19 14:01
 * 描述:社区-最新契约
 */
public interface CommunityNewsContract {
    interface View extends LoginOrShareContract.View {
        void resultData(List<ResultCommunityDataBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
    }
}
