package com.work.guaishouxingqiu.aboutball.venue.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 9:22
 * 更新时间: 2019/5/27 9:22
 * 描述:约球规则页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ABOUT_RULE)
public class AboutRuleActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_rule;
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
    protected BasePresenter createPresenter() {
        return null;
    }
}
