package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.contract.InputEvaluationContract;
import com.work.guaishouxingqiu.aboutball.my.model.InputEvaluationModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 10:43
 * 更新时间: 2019/5/27 10:43
 * 描述:填写评价P
 */
public class InputEvaluationPresenter extends BasePresenter<InputEvaluationContract.View,
        InputEvaluationModel>implements InputEvaluationContract.Presenter{
    public InputEvaluationPresenter(@NonNull InputEvaluationContract.View view) {
        super(view);
    }

    @Override
    protected InputEvaluationModel createModel() {
        return new InputEvaluationModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void postEvaluationOpponent(RequestInputEvaluationBean requestBean) {
        mModel.postEvaluationOpponent(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultEvaluationSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void postEvaluationReferee(RequestInputEvaluationBean requestBean) {
        mModel.postEvaluationReferee(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultEvaluationSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
