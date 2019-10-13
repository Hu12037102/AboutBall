package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.home.bean.ResultNewsSearchBean;
import com.ifeell.app.aboutball.home.contract.NewsSearchContract;
import com.ifeell.app.aboutball.home.model.NewsSearchModel;
import com.ifeell.app.aboutball.http.IApi;

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
        mModel.loadSearchNews(content, mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultNewsSearchBean>() {
            @Override
            public void onNext(BaseBean<ResultNewsSearchBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
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
