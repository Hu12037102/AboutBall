package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.bean.RequestAddRecordBean;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.ifeell.app.aboutball.my.contract.AddBallPeopleRecordContract;
import com.ifeell.app.aboutball.my.model.AddBallPeopleRecordModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 17:25
 * 更新时间: 2019/6/4 17:25
 * 描述:添加球员记录P
 */
public class AddBallPeopleRecordPresenter extends BasePresenter<AddBallPeopleRecordContract.View,
        AddBallPeopleRecordModel> implements AddBallPeopleRecordContract.Presenter {
    public AddBallPeopleRecordPresenter(@NonNull AddBallPeopleRecordContract.View view) {
        super(view);
    }

    @Override
    protected AddBallPeopleRecordModel createModel() {
        return new AddBallPeopleRecordModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMemberDetails(long teamId) {
        mModel.loadMemberDetails(teamId, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultTeamDetailsMemberBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTeamDetailsMemberBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMemberDetails(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void addRefereePlayerRecord(RequestAddRecordBean requestBean) {
        mModel.addRefereePlayerRecord(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultSaveRecord();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void refereeDeleteRecord(long outsId) {
        mModel.deleteRefereeRecord(outsId,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultDeleteRecordSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void editRefereePlayerRecord(RequestAddRecordBean requestBean) {
        mModel.editRefereePlayerRecord(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultSaveRecord();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
