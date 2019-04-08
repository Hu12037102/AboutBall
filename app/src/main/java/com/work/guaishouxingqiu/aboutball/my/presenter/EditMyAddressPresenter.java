package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.contract.EditMyAddressContract;
import com.work.guaishouxingqiu.aboutball.my.model.EditMyAddressModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 14:04
 * 更新时间: 2019/4/8 14:04
 * 描述:编辑我的地址P
 */
public class EditMyAddressPresenter extends BasePresenter<EditMyAddressContract.View,EditMyAddressModel>
implements EditMyAddressContract.Presenter{
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
}
