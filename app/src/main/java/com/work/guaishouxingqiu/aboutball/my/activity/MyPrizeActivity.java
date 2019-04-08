package com.work.guaishouxingqiu.aboutball.my.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MyPrizeContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPrizePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 18:27
 * 更新时间: 2019/4/8 18:27
 * 描述:我的奖品Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_PRIZE)
public class MyPrizeActivity extends BaseActivity<MyPrizePresenter> implements MyPrizeContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_prize;
    }

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
    protected MyPrizePresenter createPresenter() {
        return new MyPrizePresenter(this);
    }
}
