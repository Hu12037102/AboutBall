package com.work.guaishouxingqiu.aboutball.home.contract;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;

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
    }

    interface Presenter extends IBasePresenter {
    }
}
