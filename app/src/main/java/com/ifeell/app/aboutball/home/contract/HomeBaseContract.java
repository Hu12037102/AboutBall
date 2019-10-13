package com.ifeell.app.aboutball.home.contract;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 9:33
 * 更新时间: 2019/3/15 9:33
 * 描述:
 */
public interface HomeBaseContract {
    interface View extends IBaseView{
        void resultData(@NonNull BaseBean<List<ResultNewsBean>> bean);
    }
    interface Presenter extends IBasePresenter{
        void loadData(int typeId);
    }
}
