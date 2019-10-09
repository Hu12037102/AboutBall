package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 15:05
 * 更新时间: 2019/4/1 15:05
 * 描述:设置契约
 */
public interface SettingContract {
    interface View extends IBaseView {
        void resultFileSize(String fileSize);

        void resultApkInfo(ResultUpdateApkBean bean);
    }

    interface Presenter extends IBasePresenter {
        void getFileSize();
    }
}
