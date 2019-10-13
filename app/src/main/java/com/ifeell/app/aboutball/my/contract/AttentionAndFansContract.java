package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultAttentionFanBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/26 10:54
 * 更新时间: 2019/7/26 10:54
 * 描述:关注和我的粉丝契约
 */
public interface AttentionAndFansContract {
    interface View extends IBaseView {
        void resultAttentionFansData(List<ResultAttentionFanBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadAttentionData();

        void loadFansData();
    }
}
