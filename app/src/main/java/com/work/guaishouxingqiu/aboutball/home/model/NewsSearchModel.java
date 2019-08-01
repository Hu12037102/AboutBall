package com.work.guaishouxingqiu.aboutball.home.model;

import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.home.HomeService;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsSearchBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 14:36
 * 更新时间: 2019/4/9 14:36
 * 描述:
 */
public class NewsSearchModel extends BaseModel {
    public void loadSearchNews(String content, int pageNum, int pageSize, BaseObserver<ResultNewsSearchBean> observer) {
        mRetrofitManger.create(HomeService.class)
                .getSearchNewsData(content,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
