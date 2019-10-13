package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.community.contract.CommunityReportContract;
import com.ifeell.app.aboutball.community.model.CommunityReportModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 11:48
 * 更新时间: 2019/6/17 11:48
 * 描述:动态、话题举报P
 */
public class CommunityReportPresenter extends BasePresenter<CommunityReportContract.View,
        CommunityReportModel> implements CommunityReportContract.Presenter {
    public CommunityReportPresenter(@NonNull CommunityReportContract.View view) {
        super(view);
    }

    @Override
    protected CommunityReportModel createModel() {
        return new CommunityReportModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void reportCommunity(long tweetId, String reportCase) {
        mModel.reportCommunity(tweetId, reportCase, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.reportCommunitySucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
