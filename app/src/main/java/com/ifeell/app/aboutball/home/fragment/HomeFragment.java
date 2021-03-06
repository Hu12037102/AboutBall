package com.ifeell.app.aboutball.home.fragment;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewStub;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.CameraFragment;
import com.ifeell.app.aboutball.home.bean.ResultHomeTabBean;
import com.ifeell.app.aboutball.home.contract.HomeContract;
import com.ifeell.app.aboutball.home.presenter.HomePresenter;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 13:04
 * 更新时间: 2019/3/6 13:04
 * 描述: 首页Fragment
 */
@Route(path = ARouterConfig.Path.FRAGMENT_HOME)
public class HomeFragment extends CameraFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.tab_title)
    TabLayout mTabTitle;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    @BindView(R.id.vs_data)
    ViewStub mVsData;
    private View mInflateDataView;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        if (mInflateDataView == null) {
            mInflateDataView = mVsData.inflate();
        }
        mInflateDataView.findViewById(R.id.iv_not_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.start();
            }
        });
        mInflateDataView.setVisibility(View.GONE);
    }

    private void initPager(List<ResultHomeTabBean> tabData) {
        if (tabData == null || tabData.size() == 0) {
            return;
        }
        RecommendedFragment mRecommendedFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_RECOMMENDED, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(0).parentLabelId);
        HotFragment mHotFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_HOT, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(1).parentLabelId);
        HighlightsFragment mHighlightsFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_HIGHLIGHTS, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(2).parentLabelId);

        VideoFragment mVideoFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_VIDEO, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(3).parentLabelId);
        DrillFragment drillFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_DRILL, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(4));


        SpecialFragment mSpecialFragment = ARouterIntent
                .getFragment(ARouterConfig.Path.FRAGMENT_SPECIAL, ARouterConfig.Key.TAB_TYPE_ID, tabData.get(5).parentLabelId);
        Fragment[] fragments = new Fragment[]{mRecommendedFragment, mHotFragment,
                mHighlightsFragment, mVideoFragment, drillFragment, mSpecialFragment};

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
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0103);
                        break;
                    case 2:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0104);
                        break;
                    case 3:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0105);
                        break;
                    case 4:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0106);
                        break;
                    case 5:
                        DataUtils.addSellingPoint(getContext(), SellingPointsEvent.Key.A0107);
                        break;
                    default:
                        break;
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


    @Override
    public void resultTabData(@NonNull BaseBean<List<ResultHomeTabBean>> data) {
        mBvpContent.setVisibility(View.VISIBLE);
        mInflateDataView.setVisibility(View.GONE);
        if (DataUtils.isResultSure(data) && data.result.size() > 0) {
            //data.result.add(0, new ResultHomeTabBean(getString(R.string.recommend)));
            for (int i = 0; i < data.result.size(); i++) {
               /* mTabTitle.addTab(mTabTitle.newTab().setText(data.result.get(i).parentLabelName));
                if (i == 0) {
                    DataUtils.checkData(mTabTitle.getTabAt(0)).select();
                }*/
                UIUtils.setBaseCustomTabLayout(mTabTitle, data.result.get(i).parentLabelName, i == 0, 45, 18);
            }
            initPager(data.result);
        }
    }

    @Override
    public void resultDataError() {
        mBvpContent.setVisibility(View.GONE);
        mInflateDataView.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_search, R.id.iv_scan_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                DataUtils.addSellingPoint(getContext(),SellingPointsEvent.Key.A0108);
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_NEWS_SEARCH);
                break;
            case R.id.iv_scan_code:
                openScanCode();
                break;
        }
    }

    public static class Message {
        public int mTabIndex;
        //0代表场馆、1代表约球
        public int mChildTabIndex;

        public Message(int childTabIndex, int tabIndex) {
            this.mChildTabIndex = childTabIndex;
            this.mTabIndex = tabIndex;
        }
    }
}
