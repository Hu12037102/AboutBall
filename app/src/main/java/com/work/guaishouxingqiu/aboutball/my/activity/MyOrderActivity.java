package com.work.guaishouxingqiu.aboutball.my.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MyOrderContract;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyOrderPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/14 10:00
 * 更新时间: 2019/5/14 10:00
 * 描述:我的订单activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_ORDER)
public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.View {


    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_content)
    TabLayout mTabContent;
    @BindView(R.id.bvp_data)
    BaseViewPager mBvpContent;
    private List<Fragment> mFragmentData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.order_status_array));
        mFragmentData = new ArrayList<>();
        int orderStatus;
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabContent, tabData.get(i), i == 0, 45);
            switch (i) {
                case 0:
                    //全部
                    orderStatus = Contast.OrderStatus.ALL;
                    break;
                case 1:
                    //待付款
                    orderStatus = Contast.OrderStatus.WAIT_PAY;
                    break;
                case 2:
                    //待使用
                    orderStatus = Contast.OrderStatus.WAIT_USER;
                    break;
                case 3:
                    //待评价
                    orderStatus = Contast.OrderStatus.WAIT_EVALUATE;
                    break;
                case 4:
                    orderStatus = Contast.OrderStatus.ALL_REFUNDED;
                    break;
                default:
                    orderStatus = Contast.OrderStatus.ALL;
                    break;
            }
            mFragmentData.add(ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_ORDER, ARouterConfig.Key.ORDER_STATUS, orderStatus));
        }
    }

    @Override
    protected void initData() {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentData.get(i);
            }

            @Override
            public int getCount() {
                return mFragmentData.size();
            }
        };

        mBvpContent.setAdapter(adapter);
        mBvpContent.setOffscreenPageLimit(mFragmentData.size());
    }


    @Override
    protected void initEvent() {
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabContent.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
    }

    @Override
    protected MyOrderPresenter createPresenter() {
        return new MyOrderPresenter(this);
    }

}
