package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestActionRecordsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MatchRefereeResultContract;
import com.work.guaishouxingqiu.aboutball.my.model.MatchRefereeResultModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 13:09
 * 更新时间: 2019/6/4 13:09
 * 描述:赛况记录P
 */
public class MatchRefereeResultPresenter extends BasePresenter<MatchRefereeResultContract.View, MatchRefereeResultModel>
        implements MatchRefereeResultContract.Presenter {
    public MatchRefereeResultPresenter(@NonNull MatchRefereeResultContract.View view) {
        super(view);
    }

    @Override
    protected MatchRefereeResultModel createModel() {
        return new MatchRefereeResultModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMatchRecord(long agreeId) {
        mModel.loadMatchRecord(agreeId, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultMatchRefereeResultBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMatchRefereeResultBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMatchRecord(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void goActionRecord(RequestActionRecordsBean requestBean) {
        mModel.goActionRecord(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultActionRecord();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadRecordDetails(long agreeId) {
        mModel.loadRecordDetails(agreeId, new BaseObserver<>(true, this, new BaseObserver.Observer<ResultRefereeRecordDetailsBean>() {
            @Override
            public void onNext(BaseBean<ResultRefereeRecordDetailsBean> t) {
                if (DataUtils.isResultSure(t)) {
                mView.resultRecordDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
