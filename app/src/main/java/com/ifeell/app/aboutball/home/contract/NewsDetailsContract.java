package com.ifeell.app.aboutball.home.contract;

import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.home.bean.RequestSendMessageBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsMessageBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 14:58
 * 更新时间: 2019/3/21 14:58
 * 描述:首页-资讯详情契约
 */
public interface NewsDetailsContract {
    interface View extends LoginOrShareContract.View {
        void resultNewsContent(BaseDataBean<String> dataBean);

        void resultMessageData(List<ResultNewsMessageBean> data);
        void resultSendNewsMessage();
    }

    interface Presenter extends LoginOrShareContract.Presenter {
        void loadNewsContent(long newsId);

        void loadMessage(long newsId);

        void sendNewsMessage(RequestSendMessageBean bean);


    }
}
