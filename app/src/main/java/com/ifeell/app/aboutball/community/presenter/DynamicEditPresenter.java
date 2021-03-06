package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.community.bean.RequestPublishDynamicBean;
import com.ifeell.app.aboutball.community.contract.DynamicEditContract;
import com.ifeell.app.aboutball.community.model.DynamicEditModel;
import com.ifeell.app.aboutball.util.DataUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:35
 * 更新时间: 2019/6/14 16:35
 * 描述:动态编辑P
 */
public class DynamicEditPresenter extends BasePresenter<DynamicEditContract.View, DynamicEditModel>
        implements DynamicEditContract.Presenter {
    public DynamicEditPresenter(@NonNull DynamicEditContract.View view) {
        super(view);
    }

    @Override
    protected DynamicEditModel createModel() {
        return new DynamicEditModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void publishDynamic(RequestPublishDynamicBean requestBean) {
        mModel.publishDynamic(requestBean, new BaseObserver<>(true, this, new BaseObserver.Observer<BaseDataBean<String>>() {
            @Override
            public void onNext(BaseBean<BaseDataBean<String>> t) {
                if (DataUtils.baseDataBeanIsSucceed(t)) {
                    mView.resultPublishSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
