package com.ifeell.app.aboutball.community.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:23
 * 更新时间: 2019/6/14 18:23
 * 描述:选择话题契约
 */
public interface SelectorTopicContract {
    interface View extends IBaseView {
        void resultTopicData(List<ResultTopicBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadTopicData();
    }
}
