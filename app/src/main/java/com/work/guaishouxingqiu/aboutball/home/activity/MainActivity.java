package com.work.guaishouxingqiu.aboutball.home.activity;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameFragment;
import com.work.guaishouxingqiu.aboutball.home.adapter.MainTabAdapter;
import com.work.guaishouxingqiu.aboutball.home.bean.EventOpenTypeBean;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;
import com.work.guaishouxingqiu.aboutball.home.contract.MainContract;
import com.work.guaishouxingqiu.aboutball.home.fragment.HomeFragment;
import com.work.guaishouxingqiu.aboutball.home.presenter.MainPresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.fragment.MyFragment;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.venue.fragment.VenueFragment;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.TeamBallInviteDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import org.greenrobot.eventbus.Subscribe;

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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        registerEventBus();
        mPresenter.updateApkInfo(DownloadApkHelp.getVersionName(this));
        mRvMainTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        mPresenter.loadMainTab();
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
        //CommunityFragment communityFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_COMMUNITY);
        MyFragment myFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_MY);
        Fragment[] fragments = {homeFragment, gameFragment, venueFragment,/* communityFragment,*/ myFragment};
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
    public void loadMainTabResult(@NonNull List<MainTabBean> data) {
        if (data.size() > 0) {
            if (mTabAdapter == null) {
                mTabAdapter = new MainTabAdapter(data);
                mRvMainTab.setAdapter(mTabAdapter);
                mTabAdapter.setOnCheckTabListener((view, position) -> {
                    mBvpContent.setCurrentItem(position, true);
                });
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
