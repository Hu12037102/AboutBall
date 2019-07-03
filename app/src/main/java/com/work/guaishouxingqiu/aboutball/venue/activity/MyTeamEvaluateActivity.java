package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.fragment.PostAllEvaluationFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/3 11:54
 * 更新时间: 2019/7/3 11:54
 * 描述:我的
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_TEAM_EVALUATE)
public class MyTeamEvaluateActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private long mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team_evaluate;
    }

    @Override
    public void initPermission() {
        mId = mIntent.getLongExtra(ARouterConfig.Key.ID, -1);
        if (mId == -1) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        Bundle myTeamBundle = new Bundle();
        myTeamBundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.TEAMMATE);
        myTeamBundle.putLong(ARouterConfig.Key.ID, mId);
        PostAllEvaluationFragment fragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION,myTeamBundle);
        Fragment[] fragments = {fragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        mBvpContent.setAdapter(pagerAdapter);
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
