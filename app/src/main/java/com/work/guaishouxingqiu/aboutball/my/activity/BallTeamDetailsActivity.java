package com.work.guaishouxingqiu.aboutball.my.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamMemberContract;
import com.work.guaishouxingqiu.aboutball.my.fragment.BallTeamDetailsFragment;
import com.work.guaishouxingqiu.aboutball.my.fragment.BallTeamMemberFragment;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:47
 * 更新时间: 2019/4/25 15:47
 * 描述:球队信息Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS)
public class BallTeamDetailsActivity extends BaseActivity<BallTeamDetailsPresenter> implements BallTeamDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private ResultMyBallBean myBallBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_details;
    }

    @Override
    protected void initView() {
        myBallBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (myBallBean == null) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }

        String[] tabArray = getResources().getStringArray(R.array.ball_team_details_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            UIUtils.setBaseCustomTabLayout(mTabLayout, tabArray[i], i == 0, 45);
        }
        initFragments();
    }

    private void initFragments() {
        BallTeamDetailsFragment mTeamDetailsFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BALL_TEAM_DETAILS, ARouterConfig.Key.PARCELABLE, myBallBean);
        BallTeamMemberFragment mTeamMemberFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BALL_TEAM_MEMBER, ARouterConfig.Key.PARCELABLE, myBallBean);
        Fragment[] fragments = {mTeamDetailsFragment, mTeamMemberFragment};
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
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabLayout.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvpContent.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected BallTeamDetailsPresenter createPresenter() {
        return new BallTeamDetailsPresenter(this);
    }


}
