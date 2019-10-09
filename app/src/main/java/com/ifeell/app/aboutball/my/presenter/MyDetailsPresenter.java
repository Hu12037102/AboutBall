package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.commonality.presenter.LoginOrSharePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateHeightBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateSexBean;
import com.ifeell.app.aboutball.my.bean.RequestUpdateWeightBean;
import com.ifeell.app.aboutball.my.contract.MyDetailsContract;
import com.ifeell.app.aboutball.my.model.MyDetailsModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 9:21
 * 更新时间: 2019/3/20 9:21
 * 描述:我的详情P
 */
public class MyDetailsPresenter extends LoginOrSharePresenter<MyDetailsContract.View, MyDetailsModel>
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
        mModel.updateBirthday(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultUpdateBirthday();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void updateHeadPhoto(RequestUpdateHeadPhotoBean bean) {
        mModel.updateHeadPhoto(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultUpdateHeadPhoto(bean.headerImg);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
