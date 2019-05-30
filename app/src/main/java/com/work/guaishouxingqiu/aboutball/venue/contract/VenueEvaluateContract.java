package com.work.guaishouxingqiu.aboutball.venue.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueEvaluateBean;

/**
 * 项  目 :  AboutBalls
 * 包  名 :  com.work.guaishouxingqiu.aboutball.venue.contract
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/30
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public interface VenueEvaluateContract {
    interface View extends IBaseView {
        void resultVenueEvaluate(ResultVenueEvaluateBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadVenueEvaluate(long areaId, int flag);
    }
}
