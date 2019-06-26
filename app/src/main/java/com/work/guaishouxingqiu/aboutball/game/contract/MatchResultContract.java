package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:31
 * 更新时间: 2019/3/25 9:31
 * 描述: 赛况契约
 */
public interface MatchResultContract {
    interface View extends IBaseView {
        void resultData(ResultGameDataBean bean);
    }

    interface Presenter extends IBasePresenter {
        void loadData(int gameId);
    }
}
