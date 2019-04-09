package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.contract.NewsSearchContract;
import com.work.guaishouxingqiu.aboutball.home.model.NewsSearchModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/9 14:37
 * 更新时间: 2019/4/9 14:37
 * 描述:搜索P
 */
public class NewsSearchPresenter extends BasePresenter<NewsSearchContract.View, NewsSearchModel> implements
        NewsSearchContract.Presenter {
    public NewsSearchPresenter(@NonNull NewsSearchContract.View view) {
        super(view);
        mPageSize = 30;
    }

    @Override
    protected NewsSearchModel createModel() {
        return new NewsSearchModel();

    }

    @Override
    public void start() {

    }

    @Override
    public void searchNews(String content) {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadSearchNews(content, mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultNewsBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultNewsBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultSearchNewsData(t.result);
                }

            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
