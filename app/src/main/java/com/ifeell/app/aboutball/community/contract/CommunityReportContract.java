package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 11:43
 * 更新时间: 2019/6/17 11:43
 * 描述:动态举报契约
 */
public interface CommunityReportContract {
    interface View extends IBaseView{
        void reportCommunitySucceed();
    }
    interface Presenter extends IBasePresenter{
        void reportCommunity(long tweetId, String reportCase);
    }
}
