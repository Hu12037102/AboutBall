package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.contract.CommunityReportContract;
import com.work.guaishouxingqiu.aboutball.community.model.CommunityReportModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

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
