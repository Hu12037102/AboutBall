package com.ifeell.app.aboutball.commonality.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.commonality.contract.WebUrlContract;
import com.ifeell.app.aboutball.commonality.model.WebUrlModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 11:11
 * 更新时间: 2019/9/19 11:11
 * 描述:包含Url传递的Presenter
 */
public class WebUrlPresenter extends LoginOrSharePresenter<WebUrlContract.View, WebUrlModel>
implements WebUrlContract.Presenter{
    public WebUrlPresenter(@NonNull WebUrlContract.View view) {
        super(view);
    }

    @Override
    protected WebUrlModel createModel() {
        return new WebUrlModel();
    }

    @Override
    public void start() {

    }
}
