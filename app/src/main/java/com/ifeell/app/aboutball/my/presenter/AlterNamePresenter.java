package com.ifeell.app.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.my.bean.RequestUpdateNameBean;
import com.ifeell.app.aboutball.my.contract.AlterNameContract;
import com.ifeell.app.aboutball.my.model.AlterNameModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/20 11:15
 * 更新时间: 2019/3/20 11:15
 * 描述:修改用户名称P
 */
public class AlterNamePresenter extends BasePresenter<AlterNameContract.View, AlterNameModel>
        implements AlterNameContract.Presenter {
    public AlterNamePresenter(@NonNull AlterNameContract.View view) {
        super(view);
    }

    @Override
    protected AlterNameModel createModel() {
        return new AlterNameModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void alterName(RequestUpdateNameBean bean) {
        mModel.alterName(bean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean>() {
            @Override
            public void onNext(BaseBean<BaseDataBean> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    mView.resultAlterName();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
