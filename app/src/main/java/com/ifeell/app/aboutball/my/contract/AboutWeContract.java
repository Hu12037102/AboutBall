package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my.contract
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}关于我们契约
 *
 * @author ：
 */
public interface AboutWeContract {
    interface View extends IBaseView{
        void resultApkInfo(ResultUpdateApkBean bean);
    }
    interface Presenter extends IBasePresenter{
        void updateApkInfo(String version);
    }
}
