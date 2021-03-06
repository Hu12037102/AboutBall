package com.ifeell.app.aboutball.my.activity;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.my.contract.MyRefereeRecordContract;
import com.ifeell.app.aboutball.my.fragment.MyRefereeRecordFragment;
import com.ifeell.app.aboutball.my.fragment.PostEvaluationFragment;
import com.ifeell.app.aboutball.my.presenter.MyRefereeRecordPresenter;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/7 14:54
 * 更新时间: 2019/5/7 14:54
 * 描述:我的裁判记录页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_REFEREE_RECORD)
public class MyRefereeRecordActivity extends BaseActivity<MyRefereeRecordPresenter>
        implements MyRefereeRecordContract.View {
    @BindView(R.id.tab_content)
    TabLayout mTabData;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_referee_record;
    }

    @Override
    protected void initView() {
        String[] refereeArray = getResources().getStringArray(R.array.my_referee_tab_array);
        for (int i = 0; i < refereeArray.length; i++) {
            UIUtils.setBaseCustomTabLayout(mTabData, refereeArray[i], i == 0, 45);
        }
        MyRefereeRecordFragment refereeRecordFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY_REFEREE_RECORD);

        PostEvaluationFragment refereeEvaluateFragment = mViewModel.getRefereeEvaluationFragment(-1, Contast.InputEvaluationType.MY_REFEREE);
        Fragment[] fragments = {refereeRecordFragment, refereeEvaluateFragment};
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
        mTabData.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                DataUtils.checkData(mTabData.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected MyRefereeRecordPresenter createPresenter() {
        return new MyRefereeRecordPresenter(this);
    }


}
