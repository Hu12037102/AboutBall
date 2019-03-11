package com.work.guaishouxingqiu.aboutball.base.imp;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:24
 * 更新时间: 2019/3/4 13:24
 * 描述: MVPBaseView接口
 */
public interface IBaseView {
    void showLoadingView();

    void dismissLoadingView();

    void showToast(@NonNull String text);


}
