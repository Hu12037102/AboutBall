package com.work.guaishouxingqiu.aboutball.home.model;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.home.HomeService;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsMessageBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/21 15:00
 * 更新时间: 2019/3/21 15:00
 * 描述:首页资讯详情Model
 */
public class NewsDetailsModel extends BaseModel {
    public void loadNewData(long newsId, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(HomeService.class)
                .getNewsDetails(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadMessageData(int pageNum, int pageSize, long newsId, BaseObserver<List<ResultNewsMessageBean>> observer) {
        mRetrofitManger.create(HomeService.class)
                .getNewsMessageData(pageNum, pageSize, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void sendNewsMessage(RequestSendMessageBean bean, BaseObserver<BaseDataBean<String>> observer) {
        mRetrofitManger.create(HomeService.class)
                .postSendMessageContent(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
