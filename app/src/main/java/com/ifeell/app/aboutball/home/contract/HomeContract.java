package com.ifeell.app.aboutball.home.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.home.bean.ResultHomeTabBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 16:02
 * 更新时间: 2019/3/6 16:02
 * 描述: 首页契约
 */
public interface HomeContract {
    interface View extends IBaseView {
        void resultTabData(@NonNull BaseBean<List<ResultHomeTabBean>> data);
        void resultDataError();
    }

    interface Presenter extends IBasePresenter {

    }
}
