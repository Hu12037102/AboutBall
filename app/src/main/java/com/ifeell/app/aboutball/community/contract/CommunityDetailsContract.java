package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.home.bean.ResultNewsMessageBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/13 18:33
 * 更新时间: 2019/6/13 18:33
 * 描述:动态详情契约
 */
public interface CommunityDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultCommentData(List<ResultNewsMessageBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadCommentData(long tweetId);
    }
}
