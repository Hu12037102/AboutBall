package com.work.guaishouxingqiu.aboutball.my.activity;

import android.widget.ExpandableListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/5 9:38
 * 更新时间: 2019/9/5 9:38
 * 描述: 确认订单Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_SURE_ORDER)
public class SureOrderActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sure_order;
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
