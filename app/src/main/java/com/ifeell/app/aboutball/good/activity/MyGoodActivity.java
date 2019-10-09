package com.ifeell.app.aboutball.good.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.google.android.material.tabs.TabLayout;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.good.fragment.MyGoodFragment;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/19 14:14
 * 更新时间: 2019/9/19 14:14
 * 描述:我的订单页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_GOOD)
public class MyGoodActivity extends BaseActivity {
    @BindView(R.id.tb_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.title_view)
    TitleView mTitleView;
    public static final int REQUEST_CODE_TO_GOOD_DETAILS = 1259;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_good;
    }

    @Override
    protected void initView() {
        List<String> mGoodTabData = Arrays.asList(getResources().getStringArray(R.array.my_good_tab_array));
        for (int i = 0; i < mGoodTabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, mGoodTabData.get(i), i == 0, 45,18);
        }
    }

    @Override
    protected void initData() {
        MyGoodFragment allGoodFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_GOOD, ARouterConfig.Key.GOOD_STATUS, Contast.MyGoodStatus.ALL);
        MyGoodFragment waitPayFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_GOOD, ARouterConfig.Key.GOOD_STATUS, Contast.MyGoodStatus.WAIT_PAY);
        MyGoodFragment completeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_GOOD, ARouterConfig.Key.GOOD_STATUS, Contast.MyGoodStatus.COMPLETE);
        MyGoodFragment cancelFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_GOOD, ARouterConfig.Key.GOOD_STATUS, Contast.MyGoodStatus.CANCEL);
        Fragment[] fragments = {allGoodFragment, waitPayFragment, completeFragment, cancelFragment};
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        mBvpContent.setAdapter(pagerAdapter);
        mBvpContent.setOffscreenPageLimit(pagerAdapter.getCount());
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
    protected BasePresenter createPresenter() {
        return null;
    }


}
