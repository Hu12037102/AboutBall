package com.work.guaishouxingqiu.aboutball.my.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MyRefereeRecordContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyRefereeRecordPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 14:54
 * 更新时间: 2019/5/7 14:54
 * 描述:我的裁判记录页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_REFEREE_RECORD)
public class MyRefereeRecordActivity extends BaseActivity<MyRefereeRecordPresenter>
        implements MyRefereeRecordContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_referee_record;
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
    protected MyRefereeRecordPresenter createPresenter() {
        return new MyRefereeRecordPresenter(this);
    }
}
