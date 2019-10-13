package com.ifeell.app.aboutball.my.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.google.android.material.tabs.TabLayout;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.contract.MyTicketsContract;
import com.ifeell.app.aboutball.my.presenter.MyTicketsPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/4 17:14
 * 更新时间: 2019/9/4 17:14
 * 描述:我的购票activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_TICKETS)
public class MyTicketsActivity extends BaseActivity<MyTicketsPresenter> implements MyTicketsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_content)
    ViewPager mBvpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_tickets;
    }

    @Override
    protected void initView() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.my_tickets_tab_array));
        List<Fragment> fragmentData = new ArrayList<>();
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45, 18);
            fragmentData.add(ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_TICKETS, ARouterConfig.Key.KEY_STATUS, i));
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
        mBvpContent.setOffscreenPageLimit(pagerAdapter.getCount());
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
    protected MyTicketsPresenter createPresenter() {
        return new MyTicketsPresenter(this);
    }


}
