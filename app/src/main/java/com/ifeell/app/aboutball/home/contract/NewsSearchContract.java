package com.ifeell.app.aboutball.home.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.home.bean.ResultNewsSearchBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 14:34
 * 更新时间: 2019/4/9 14:34
 * 描述:搜索契约
 */
public interface NewsSearchContract {
    interface View extends IBaseView {
        void resultSearchNewsData(ResultNewsSearchBean bean);
    }

    interface Presenter extends IBasePresenter {
        void searchNews(String content);
    }
}
