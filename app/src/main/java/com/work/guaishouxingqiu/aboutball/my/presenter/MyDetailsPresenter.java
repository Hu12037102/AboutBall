package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateWeightBean;
import com.work.guaishouxingqiu.aboutball.my.contract.MyDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.model.MyDetailsModel;

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
        mModel.updateSex(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> baseBean) {
                if (baseBean.code == IApi.Code.SUCCEED) {
                    mView.resultUpdateSex();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void updateWeight(RequestUpdateWeightBean bean) {
        mModel.updateWeight(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultUpdateWeight();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void updateHeight(RequestUpdateHeightBean bean) {
        mModel.updateHeight(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultUpdateHeight();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void updateBirthday(RequestUpdateBirthdayBean bean) {
        mModel.updateBirthday(bean,new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED){
                    mView.resultUpdateBirthday();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
