package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:47
 * 更新时间: 2019/3/19 13:47
 * 描述:社区关注契约
 */
public interface CommunityAttentionContract {
    interface View extends LoginOrShareContract.View {
        void resultData(List<ResultCommunityDataBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
    }
}
