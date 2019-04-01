package com.work.guaishouxingqiu.aboutball.my.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;

import java.io.File;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/1 15:05
 * 更新时间: 2019/4/1 15:05
 * 描述:设置契约
 */
public interface SettingContract {
    interface View extends IBaseView {
        void resultFileSize(String fileSize);
    }

    interface Presenter extends IBasePresenter {
        void getFileSize(File file);
    }
}
