package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.ApplyRefereeContract;
import com.work.guaishouxingqiu.aboutball.my.model.ApplyRefereeModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/6 10:53
 * 更新时间: 2019/5/6 10:53
 * 描述:
 */
public class ApplyRefereePresenter extends BasePresenter<ApplyRefereeContract.View, ApplyRefereeModel>
        implements ApplyRefereeContract.Presenter {
    public ApplyRefereePresenter(@NonNull ApplyRefereeContract.View view) {
        super(view);
    }

    @Override
    protected ApplyRefereeModel createModel() {
        return new ApplyRefereeModel();
    }

    @Override
    public void start() {

    }
}
