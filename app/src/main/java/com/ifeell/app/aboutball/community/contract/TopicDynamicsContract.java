package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 17:55
 * 更新时间: 2019/6/19 17:55
 * 描述:话题动态契约
 */
public interface TopicDynamicsContract {
    interface View extends LoginOrShareContract.View {
        void resultTopicData(List<ResultCommunityDataBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadRecommendedTopic(long topicId);

        void loadNewTopic(long topicId);
    }
}
