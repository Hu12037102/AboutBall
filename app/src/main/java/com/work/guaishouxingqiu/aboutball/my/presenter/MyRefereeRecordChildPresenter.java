package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyRefereeRecordChildContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyRefereeRecordChildModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:11
 * 更新时间: 2019/5/9 10:11
 * 描述:我的裁判记录P
 */
public class MyRefereeRecordChildPresenter extends BasePresenter<MyRefereeRecordChildContract.View,
        MyRefereeRecordChildModel> implements MyRefereeRecordChildContract.Presenter {
    public MyRefereeRecordChildPresenter(@NonNull MyRefereeRecordChildContract.View view) {
        super(view);
    }

    @Override
    protected MyRefereeRecordChildModel createModel() {
        return new MyRefereeRecordChildModel();
    }

    @Override
    public void start() {
        mModel.loadMyRefereeRecord(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultRefereeRecordBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultRefereeRecordBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMyRefereeRecord(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadRefereeRecord(long refereeId) {
        mModel.loadRefereeRecord(refereeId,new BaseObserver<>(true,this, new BaseObserver.Observer<List<ResultRefereeRecordBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultRefereeRecordBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultMyRefereeRecord(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
