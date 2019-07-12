package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestFeedbackBean;
import com.work.guaishouxingqiu.aboutball.my.contract.FeedbackContract;
import com.work.guaishouxingqiu.aboutball.my.model.FeedbackModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/13 9:23
 * 更新时间: 2019/5/13 9:23
 * 描述:意见反馈P
 */
public class FeedbackPresenter extends BasePresenter<FeedbackContract.View,FeedbackModel>implements
FeedbackContract.Presenter{
    public FeedbackPresenter(@NonNull FeedbackContract.View view) {
        super(view);
    }

    @Override
    protected FeedbackModel createModel() {
        return new FeedbackModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void feedback(RequestFeedbackBean requestBean) {
        mModel.feedback(requestBean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)){
                    mView.resultFeedbackSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
