package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.contract.CommunityAttentionContract;
import com.ifeell.app.aboutball.community.contract.CommunityContract;
import com.ifeell.app.aboutball.community.model.CommunityAttentionModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 13:49
 * 更新时间: 2019/3/19 13:49
 * 描述:社区-关注P
 */
public class CommunityAttentionPresenter extends LoginOrSharePresenter<CommunityAttentionContract.View,
        CommunityAttentionModel> implements CommunityContract.Presenter {
    public CommunityAttentionPresenter(@NonNull CommunityAttentionContract.View view) {
        super(view);
    }

    @Override
    protected CommunityAttentionModel createModel() {
        return new CommunityAttentionModel();
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
