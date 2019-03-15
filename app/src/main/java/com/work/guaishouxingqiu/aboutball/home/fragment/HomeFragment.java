package com.work.guaishouxingqiu.aboutball.home.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseFragment;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;
import com.work.guaishouxingqiu.aboutball.home.contract.HomeContract;
import com.work.guaishouxingqiu.aboutball.home.presenter.HomePresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:04
 * 更新时间: 2019/3/6 13:04
 * 描述: 首页Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {


    }

    private void initPager(List<ResultHomeTabBean> tabData) {
        RecommendedFragment mRecommendedFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_RECOMMENDED, ARouterConfig.Key.TAB_TYPE_ID, 0);
        HotFragment mHotFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_HOT, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(0).labelId);
        HighlightsFragment mHighlightsFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_HIGHLIGHTS, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(1).labelId);
        SpecialFragment mSpecialFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_SPECIAL, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(2).labelId);
        VideoFragment mVideoFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_VIDEO, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(3).labelId);

        Fragment[] fragments = new Fragment[]{mRecommendedFragment, mHotFragment,
                mHighlightsFragment, mSpecialFragment, mVideoFragment};

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
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
        mBvpContent.setAdapter(fragmentPagerAdapter);

        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabTitle.getTabAt(i)).select();
                mBvpContent.setCurrentItem(i, true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }


    @Override
    protected void initEvent() {

        mTabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mBvpContent.getChildCount() > DataUtils.checkData(tab.getPosition())) {
                    mBvpContent.setCurrentItem(DataUtils.checkData(tab.getPosition()), true);
                }
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
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }

    @Override
    public void resultTabData(@NonNull BaseBean<List<ResultHomeTabBean>> data) {
        if (DataUtils.isResultSure(data) && data.result.size() > 0) {
            for (int i = 0; i < data.result.size(); i++) {
                mTabTitle.addTab(mTabTitle.newTab().setText(data.result.get(i).labelName));
                if (i == 0) {
                    DataUtils.checkData(mTabTitle.getTabAt(0)).select();
                }
            }
            initPager(data.result);
        }
    }
}
