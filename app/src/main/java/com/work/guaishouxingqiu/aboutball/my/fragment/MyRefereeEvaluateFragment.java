package com.work.guaishouxingqiu.aboutball.my.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.DelayedFragment;
import com.work.guaishouxingqiu.aboutball.my.contract.MyRefereeEvaluateContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyRefereeEvaluatePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/9 10:37
 * 更新时间: 2019/5/9 10:37
 * 描述:我的裁判评价Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_MY_REFEREE_EVALUATE)
public class MyRefereeEvaluateFragment extends DelayedFragment<MyRefereeEvaluatePresenter> implements MyRefereeEvaluateContract.View{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_referee_evaluate;
    }



    @Override
    protected MyRefereeEvaluatePresenter createPresenter() {
        return new MyRefereeEvaluatePresenter(this);
    }

    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void initDelayedEvent() {

    }
}
