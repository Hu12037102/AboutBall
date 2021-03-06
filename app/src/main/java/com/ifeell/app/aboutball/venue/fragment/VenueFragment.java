package com.ifeell.app.aboutball.venue.fragment;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.DelayedFragment;
import com.ifeell.app.aboutball.home.adapter.RecommendedAdapter;
import com.ifeell.app.aboutball.home.fragment.HomeFragment;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.contract.VenueContract;
import com.ifeell.app.aboutball.venue.presenter.VenuePresenter;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 15:19
 * 更新时间: 2019/3/6 15:19
 * 描述:场馆Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_VENUE)
public class VenueFragment extends DelayedFragment<VenuePresenter> implements VenueContract.View {
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
    protected void initDelayedView() {
        registerEventBus();
    }

    @Override
    protected void initDelayedData() {
        initTabData();
        initPagerData();
    }

    @Override
    protected void initDelayedEvent() {
        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1){
                    DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0303);
                }
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
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterEventBus();
    }

    @Subscribe
    public void selectorVenuePager(HomeFragment.Message message){
        if (mBvpContent == null){
            return;
        }
        if (message.mChildTabIndex == RecommendedAdapter.TYPE_VENUE){
            mBvpContent.setCurrentItem(RecommendedAdapter.TYPE_VENUE);
        }else if (message.mChildTabIndex == RecommendedAdapter.TYPE_BALL){
            mBvpContent.setCurrentItem(RecommendedAdapter.TYPE_BALL);
        }
    }

    @Override
    protected void initData() {

    }


    private void initTabData() {
        String[] tabArray = getResources().getStringArray(R.array.venue_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
          //  mTabTitle.addTab(mTabTitle.newTab().setText(tabArray[i]), i == 0);
            UIUtils.setBaseCustomTabLayout(mTabTitle,tabArray[i],i==0,45,18);
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

    }

    @Override
    protected VenuePresenter createPresenter() {
        return new VenuePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }
}
