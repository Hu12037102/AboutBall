package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.bean.ResultRecommendHotBean;
import com.ifeell.app.aboutball.community.contract.CommunityRecommendContract;
import com.ifeell.app.aboutball.community.model.CommunityRecommendModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:56
 * 更新时间: 2019/3/19 13:56
 * 描述:社区-推荐P
 */
public class CommunityRecommendPresenter extends LoginOrSharePresenter<CommunityRecommendContract.View,
        CommunityRecommendModel> implements CommunityRecommendContract.Presenter {
    public CommunityRecommendPresenter(@NonNull CommunityRecommendContract.View view) {
        super(view);
    }

    @Override
    protected CommunityRecommendModel createModel() {
        return new CommunityRecommendModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData() {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadData(mPageNum, mPageSize, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultCommunityDataBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultCommunityDataBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadHeadData() {
        mModel.loadHeadData(new BaseObserver<>(this, new BaseObserver.Observer<List<ResultRecommendHotBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultRecommendHotBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultHeadData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
