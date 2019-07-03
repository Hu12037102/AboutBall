package com.work.guaishouxingqiu.aboutball.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.contract.MyPrizeContract;
import com.work.guaishouxingqiu.aboutball.my.fragment.BasePrizeFragment;
import com.work.guaishouxingqiu.aboutball.my.presenter.MyPrizePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/8 18:27
 * 更新时间: 2019/4/8 18:27
 * 描述:我的奖品Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_PRIZE)
public class MyPrizeActivity extends BaseActivity<MyPrizePresenter> implements MyPrizeContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.vs_rule)
    ViewStub mVsHint;
    private View inflateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_prize;
    }

    @Override
    protected void initView() {

    }

    private void initHeadView(int type) {
        if (inflateView == null) {
            inflateView = mVsHint.inflate();
            TextView tvRule = inflateView.findViewById(R.id.tv_rule);
            ImageView ivClose = inflateView.findViewById(R.id.iv_close);
            tvRule.setText(R.string.line_prize_input_address);
            inflateView.setOnClickListener(v -> ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_EDIT_MY_ADDRESS));
            ivClose.setOnClickListener(v -> inflateView.setVisibility(View.GONE));
        }
        if (type == 0) {
            inflateView.setVisibility(View.GONE);
        } else {
            inflateView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        List<String> tabData = Arrays.asList(getResources().getStringArray(R.array.award_status_array));
        for (int i = 0; i < tabData.size(); i++) {
            UIUtils.setBaseCustomTabLayout(mTabTitle,tabData.get(i),i== 0,45);
        }
        BasePrizeFragment waitPrizeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BASE_PRIZE, ARouterConfig.Key.KEY_STATUS, Contast.PRIZE_WAIT);
        waitPrizeFragment.setOnHasAddressResult(this::initHeadView);
        BasePrizeFragment hasChangePrizeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BASE_PRIZE, ARouterConfig.Key.KEY_STATUS, Contast.PRIZE_HAS_CHANGE);
        BasePrizeFragment timeOutPrizeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BASE_PRIZE, ARouterConfig.Key.KEY_STATUS, Contast.PRIZE_TIME_OUT);
        Fragment[] fragments = {waitPrizeFragment, hasChangePrizeFragment, timeOutPrizeFragment};
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
        mBvpContent.setOffscreenPageLimit(fragments.length);
        mBvpContent.setAdapter(pagerAdapter);

    }

    @Override
    protected void initEvent() {
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabTitle.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    protected MyPrizePresenter createPresenter() {
        return new MyPrizePresenter(this);
    }


}
