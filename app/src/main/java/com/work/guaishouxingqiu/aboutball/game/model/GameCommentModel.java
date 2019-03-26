package com.work.guaishouxingqiu.aboutball.game.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.game.GameService;
import com.work.guaishouxingqiu.aboutball.game.bean.RequestGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 9:48
 * 更新时间: 2019/3/25 9:48
 * 描述:比赛-评论model
 */
public class GameCommentModel extends BaseModel {
    public void loadCommentData(int gameId, int pageNum, int pageSize, BaseObserver<List<ResultGameCommentBean>> observer) {
        mRetrofitManger.create(GameService.class)
                .loadCommentList(gameId, pageNum, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void senCommendMessage(RequestGameCommentBean bean, BaseObserver<BaseDataBean> observer){
        mRetrofitManger.create(GameService.class)
                .sendCommentMessage(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
