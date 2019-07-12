package com.work.guaishouxingqiu.aboutball.venue.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.MyAboutBallPresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 17:27
 * 更新时间: 2019/5/22 17:27
 * 描述:
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_ABOUT_BALL)
public class MyAboutBallActivity extends BaseActivity<MyAboutBallPresenter> implements
        MyAboutBallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tb_content)
    TabLayout mTbContent;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_about_ball;
    }

    @Override
    protected void initView() {
        List<String> mTabData = Arrays.asList(getResources().getStringArray(R.array.my_about_ball_array));
        for (int i = 0; i < mTabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTbContent, mTabData.get(i), i == 0, 45);
        }
        Fragment[] fragments = {ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_ABOUT_BALL, ARouterConfig.Key.ABOUT_BALL_FLAG, Contast.AboutBallFlag.PUBLISH),
                ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_ABOUT_BALL, ARouterConfig.Key.ABOUT_BALL_FLAG, Contast.AboutBallFlag.PARTICIPATION)};
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
        mTbContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTbContent.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected MyAboutBallPresenter createPresenter() {
        return new MyAboutBallPresenter(this);
    }


}
