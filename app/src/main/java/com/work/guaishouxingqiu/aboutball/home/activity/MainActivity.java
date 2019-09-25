package com.work.guaishouxingqiu.aboutball.home.activity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.BuildConfig;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.community.fragment.CommunityFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameFragment;
import com.work.guaishouxingqiu.aboutball.home.adapter.MainTabAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.home.contract.MainContract;
import com.work.guaishouxingqiu.aboutball.home.fragment.HomeFragment;
import com.work.guaishouxingqiu.aboutball.home.presenter.MainPresenter;
import com.work.guaishouxingqiu.aboutball.my.fragment.MyFragment;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.other.EmojiManger;
import com.work.guaishouxingqiu.aboutball.other.SellingPointsEvent;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.NetWorkUtils;
import com.work.guaishouxingqiu.aboutball.venue.fragment.VenueFragment;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.TeamBallInviteDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 主页面
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MAIN)
public class MainActivity extends PermissionActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.rv_main_tab)
    RecyclerView mRvMainTab;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private MainTabAdapter mTabAdapter;
    private long mFinishTime;
    private TeamBallInviteDialog mTeamInviteDialog;
    private List<MainTabBean> mTabData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        registerEventBus();
       /* if (BuildConfig.IS_UPDATE) {
            mPresenter.updateApkInfo(DownloadApkHelp.getVersionName(this));
        }*/
        mPresenter.updateApkInfo(DownloadApkHelp.getVersionName(this));
        mRvMainTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        mTabData = new ArrayList<>();
        initLocation();
        // mPresenter.loadMainTab();
    }

    private void initLocation() {
        if (NetWorkUtils.isNetCanUse()) {
            requestLocation();
        } else {
            mPresenter.loadMainTab();
        }
    }

    @Override
    public void locationResult(double longitude, double latitude, String city) {
        mPresenter.loadMainTab();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.obtainRedPoint();
      /*  if (mTabAdapter != null) {
            mTabData.get(mTabData.size() - 1).isChecked = mViewModel.isShowRedPoint();
            mTabAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }


    private void initFragment() {

        HomeFragment homeFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_HOME);
        GameFragment gameFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME);
        VenueFragment venueFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_VENUE);
        CommunityFragment communityFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY);
        MyFragment myFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY);
        Fragment[] fragments = {homeFragment, gameFragment, venueFragment, communityFragment, myFragment};
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

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void resultRedPointData(List<ResultRedPointInfoBean> data) {
        if (mTabData.size() > 0) {
            mTabData.get(mTabData.size() - 1).showRedPoint = mViewModel.isShowRedPoint();
            mTabAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMainTabResult(@NonNull List<MainTabBean> data) {
        if (data.size() > 0) {
            mTabData.addAll(data);
            if (mTabAdapter == null) {
                mTabAdapter = new MainTabAdapter(data);
                mRvMainTab.setAdapter(mTabAdapter);
                mTabAdapter.setOnCheckTabListener((view, position) -> {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            DataUtils.addSellingPoint(MainActivity.this, SellingPointsEvent.Key.A0201);
                            break;
                        case 2:
                            DataUtils.addSellingPoint(MainActivity.this, SellingPointsEvent.Key.A0301);
                            break;
                        case 3:
                            DataUtils.addSellingPoint(MainActivity.this, SellingPointsEvent.Key.A0401);
                            break;
                        case 4:
                            DataUtils.addSellingPoint(MainActivity.this, SellingPointsEvent.Key.A0501);
                            break;
                        default:
                            break;
                    }
                    mBvpContent.setCurrentItem(position, true);
                });
                mPresenter.obtainRedPoint();
                initFragment();
            } else {
                mTabAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe
    public void selectorVenuePager(HomeFragment.Message message) {
        if (mBvpContent == null || message == null) {
            return;
        }
        mTabAdapter.setSelectorTab(message.mTabIndex);
        mBvpContent.setCurrentItem(message.mTabIndex, true);
    }

    @Override
    public void onBackPressed() {

        long clickBack = System.currentTimeMillis();
        if (clickBack - mFinishTime > 2000) {
            mFinishTime = clickBack;
            Toasts.with().showToast(R.string.resume_click_exit_app);
        } else {
            super.onBackPressed();
        }

    }


}
