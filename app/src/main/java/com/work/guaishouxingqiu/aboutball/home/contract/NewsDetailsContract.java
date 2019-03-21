package com.work.guaishouxingqiu.aboutball.home.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 14:58
 * 更新时间: 2019/3/21 14:58
 * 描述:首页-资讯详情契约
 */
public interface NewsDetailsContract {
    interface View extends IBaseView {
        void resultNewsContent(String newsContent);
    }

    interface Presenter extends IBasePresenter {
        void loadNewsContent(long newsId);
    }
}
