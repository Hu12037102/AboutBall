package com.work.guaishouxingqiu.aboutball.venue.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.venue.contract.VenueContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.VenuePresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:19
 * 更新时间: 2019/3/6 15:19
 * 描述:场馆Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VENUE)
public class VenueFragment extends BaseFragment<VenuePresenter> implements VenueContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    public static VenueFragment newInstance() {
        return new VenueFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_venue;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initTabData();
        initPagerData();
    }


    private void initTabData() {
        String[] tabArray = getResources().getStringArray(R.array.venue_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]), i == 0);
        }
    }

    private void initPagerData() {
        VenueListFragment venueListFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_VENUE_LIST);
        AboutBallFragment aboutBallFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_ABOUT_BALL);
        Fragment[] fragments = {venueListFragment, aboutBallFragment};
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        mBvpContent.setAdapter(fragmentPagerAdapter);
    }

    @Override
    protected void initEvent() {
        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBvpContent.setCurrentItem(tab.getPosition(),true);
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
                    mTabTitle.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected VenuePresenter createPresenter() {
        return new VenuePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }
}
