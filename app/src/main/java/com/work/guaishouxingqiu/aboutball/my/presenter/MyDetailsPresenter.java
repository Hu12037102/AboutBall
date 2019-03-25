package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.login.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyDetailsModel;

import okhttp3.RequestBody;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:21
 * 更新时间: 2019/3/20 9:21
 * 描述:我的详情P
 */
public class MyDetailsPresenter extends BasePresenter<MyDetailsContract.View, MyDetailsModel>
        implements MyDetailsContract.Presenter {
    public MyDetailsPresenter(@NonNull MyDetailsContract.View view) {
        super(view);
    }

    @Override
    protected MyDetailsModel createModel() {
        return new MyDetailsModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void updateSex(int sexType) {
        RequestUpdateSexBean bean = new RequestUpdateSexBean();
        bean.gender = sexType;
        mModel.updateSex(bean, new BaseObserver(true, this, new BaseObserver.Observer() {
            @Override
            public void onNext(BaseBean bean) {
                mView.resultUpdateSex(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}