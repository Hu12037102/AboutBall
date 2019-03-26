package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:39
 * 更新时间: 2019/3/25 9:39
 * 描述:比赛数据契约
 */
public interface GameDataContract {
    interface View extends IBaseView{
        void resultGameDetails(List<ResultGameDetailsBean.Bean> data);
    }
    interface Presenter extends IBasePresenter{
        void loadGameDetails(int gameId);
    }
}
