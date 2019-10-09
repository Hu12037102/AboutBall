package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.RequestEditAddressBean;
import com.ifeell.app.aboutball.my.bean.RequestNewAddressBean;
import com.ifeell.app.aboutball.my.bean.ResultMyAddress;
import com.ifeell.app.aboutball.my.contract.EditMyAddressContract;
import com.ifeell.app.aboutball.my.model.EditMyAddressModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 14:04
 * 更新时间: 2019/4/8 14:04
 * 描述:编辑我的地址P
 */
public class EditMyAddressPresenter extends BasePresenter<EditMyAddressContract.View, EditMyAddressModel>
        implements EditMyAddressContract.Presenter {
    public EditMyAddressPresenter(@NonNull EditMyAddressContract.View view) {
        super(view);
    }

    @Override
    protected EditMyAddressModel createModel() {
        return new EditMyAddressModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void saveNewsAddress(RequestNewAddressBean bean) {
        mModel.saveNewAddress(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultSaveAddress();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void getMyAddress() {
        mModel.getMyAddress(new BaseObserver<>(true, this, new BaseObserver.Observer<ResultMyAddress>() {
            @Override
            public void onNext(BaseBean<ResultMyAddress> t) {
                if (t.code == IApi.Code.SUCCEED && t.result != null) {
                    mView.resultMyAddress(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void saveEditAddress(RequestEditAddressBean bean) {
        mModel.saveEditAddress(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultSaveAddress();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
