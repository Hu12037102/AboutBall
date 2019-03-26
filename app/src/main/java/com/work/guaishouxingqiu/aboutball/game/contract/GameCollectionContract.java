package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 10:13
 * 更新时间: 2019/3/25 10:13
 * 描述:比赛-集锦契约
 */
public interface GameCollectionContract {
    interface View extends IBaseView {
        void resultData(List<ResultGameCollectionBean> data);
    }

    interface Presenter extends IBasePresenter {
        void loadData(int gameId);
    }
}
