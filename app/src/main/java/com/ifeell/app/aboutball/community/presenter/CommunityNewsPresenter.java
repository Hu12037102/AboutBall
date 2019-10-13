package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.contract.CommunityNewsContract;
import com.ifeell.app.aboutball.community.model.CommunityNewsModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 14:04
 * 更新时间: 2019/3/19 14:04
 * 描述:社区-最新P
 */
public class CommunityNewsPresenter extends LoginOrSharePresenter<CommunityNewsContract.View,CommunityNewsModel>
implements CommunityNewsContract.Presenter{
    public CommunityNewsPresenter(@NonNull CommunityNewsContract.View view) {
        super(view);
    }

    @Override
    protected CommunityNewsModel createModel() {
        return new CommunityNewsModel();
    }

    @Override
    public void start() {
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
}
