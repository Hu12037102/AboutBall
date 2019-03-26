package com.work.guaishouxingqiu.aboutball.game.contract;

import com.work.guaishouxingqiu.aboutball.base.imp.IBasePresenter;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseView;
import com.work.guaishouxingqiu.aboutball.game.bean.RequestGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:45
 * 更新时间: 2019/3/25 9:45
 * 描述:比赛评论契约
 */
public interface GameCommentContract {
    interface View extends IBaseView {
        void resultCommentData(List<ResultGameCommentBean> data);
        void resultCommentMessage();
    }

    interface Presenter extends IBasePresenter {
        void loadCommentData(int gameId);

        void sendCommentMessage(RequestGameCommentBean bean);
    }
}
