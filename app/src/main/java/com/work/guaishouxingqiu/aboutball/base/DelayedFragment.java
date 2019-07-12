package com.work.guaishouxingqiu.aboutball.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.work.guaishouxingqiu.aboutball.util.LogUtils;

/**
 * 项  目 :  ipk_android
 * 包  名 :  com.work.guaishouxingqiu.aboutball.base
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/16
 * 描  述 :  ${TODO}延时加载Fragment
 *
 * @author ：
 */
public abstract class DelayedFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected boolean isFirstCreate = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.w("setUserVisibleHint--", getContext() + "--" + getUserVisibleHint());
        if (isVisibleToUser && isFirstCreate && getContext() != null) {
            LogUtils.w("setUserVisibleHint---", "天青色等烟雨，而我在等你！");
            initDelayedView();
            initDelayedData();
            initDelayedEvent();
            isFirstCreate = false;
        }
    }

    protected abstract void initDelayedView();

    protected abstract void initDelayedData();

    protected abstract void initDelayedEvent();


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getUserVisibleHint();
    }

}
