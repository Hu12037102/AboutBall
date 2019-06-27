package com.work.guaishouxingqiu.aboutball.community.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.commonality.contract.LoginOrShareContract;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:54
 * 更新时间: 2019/3/19 13:54
 * 描述: 社区-推荐契约
 */
public interface CommunityRecommendContract {
    interface View extends LoginOrShareContract.View {
        void resultHeadData(List<ResultRecommendHotBean> data);

        void resultData(List<ResultCommunityDataBean> data);
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadData();

        void loadHeadData();
    }
}
