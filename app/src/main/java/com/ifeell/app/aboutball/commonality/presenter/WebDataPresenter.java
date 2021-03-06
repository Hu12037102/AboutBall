package com.ifeell.app.aboutball.commonality.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.commonality.contract.WebDataContract;
import com.ifeell.app.aboutball.commonality.model.WebDataModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/11 10:29
 * 更新时间: 2019/4/11 10:29
 * 描述:纯web数据Presenter
 */
public class WebDataPresenter extends LoginOrSharePresenter<WebDataContract.View,WebDataModel>
implements WebDataContract.Presenter{
    public WebDataPresenter(@NonNull WebDataContract.View view) {
        super(view);
    }

    @Override
    protected WebDataModel createModel() {
        return new WebDataModel();
    }

    @Override
    public void start() {

    }


}
