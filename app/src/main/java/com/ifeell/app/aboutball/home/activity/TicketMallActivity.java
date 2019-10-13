package com.ifeell.app.aboutball.home.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.google.android.material.tabs.TabLayout;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.home.contract.TicketMallContract;
import com.ifeell.app.aboutball.home.presenter.TicketMallPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/27 11:22
 * 更新时间: 2019/8/27 11:22
 * 描述:售票商城Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_TICKET_MALL)
public class TicketMallActivity extends BaseActivity<TicketMallPresenter> implements TicketMallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket_mall;
    }

    @Override
    protected void initView() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.ticket_mall_tab_array));
        List<Fragment> fragmentData = new ArrayList<>();
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45,18);
            fragmentData.add(ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_TICK_MALL, ARouterConfig.Key.KEY_STATUS, i));
        }
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentData.get(position);
            }

            @Override
            public int getCount() {
                return fragmentData.size();
            }
        };
        mBvpContent.setAdapter(pagerAdapter);
        mBvpContent.setOffscreenPageLimit(fragmentData.size());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mTabContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabContent.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected TicketMallPresenter createPresenter() {
        return new TicketMallPresenter(this);
    }


}
