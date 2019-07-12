package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AlterNameContract;
import com.work.guaishouxingqiu.aboutball.my.model.AlterNameModel;

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
