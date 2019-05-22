package com.work.guaishouxingqiu.aboutball.venue.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.model.MyAboutBallModel;
import com.work.guaishouxingqiu.aboutball.venue.presenter.MyAboutBallPresenter;

import java.util.Arrays;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:27
 * 更新时间: 2019/5/22 17:27
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_ABOUT_BALL)
public class MyAboutBallActivity extends BaseActivity<MyAboutBallPresenter> implements
        MyAboutBallContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_about_ball;
    }

    @Override
    protected void initView() {
        List<String> mTabData = Arrays.asList(getResources().getStringArray(R.array.my_about_ball_array));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected MyAboutBallPresenter createPresenter() {
        return new MyAboutBallPresenter(this);
    }


}
