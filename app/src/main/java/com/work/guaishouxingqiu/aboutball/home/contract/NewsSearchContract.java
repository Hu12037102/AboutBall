package com.work.guaishouxingqiu.aboutball.home.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 14:34
 * 更新时间: 2019/4/9 14:34
 * 描述:搜索契约
 */
public interface NewsSearchContract {
    interface View extends IBaseView {
        void resultSearchNewsData(List<ResultNewsBean> data);
    }

    interface Presenter extends IBasePresenter {
        void searchNews(String content);
    }
}
