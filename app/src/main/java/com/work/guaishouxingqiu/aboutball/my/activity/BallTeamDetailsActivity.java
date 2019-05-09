package com.work.guaishouxingqiu.aboutball.my.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.contract.BallTeamDetailsContract;
import com.work.guaishouxingqiu.aboutball.my.fragment.BallTeamDetailsFragment;
import com.work.guaishouxingqiu.aboutball.my.fragment.BallTeamMemberFragment;
import com.work.guaishouxingqiu.aboutball.my.presenter.BallTeamDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.SingPopupWindows;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/25 15:47
 * 更新时间: 2019/4/25 15:47
 * 描述:球队信息Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS)
public class BallTeamDetailsActivity extends BaseActivity<BallTeamDetailsPresenter> implements BallTeamDetailsContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private ResultMyBallBean mMyBallBean;
    private SingPopupWindows mExitWindows;
    private BallTeamMemberFragment mTeamMemberFragment;
    private long mMyPlayerId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ball_team_details;
    }

    @Override
    protected void initView() {
        mTitleView.mTvSure.setVisibility(View.GONE);
        mMyBallBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        if (mMyBallBean == null) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }

        String[] tabArray = getResources().getStringArray(R.array.ball_team_details_tab_array);
        for (int i = 0; i < tabArray.length; i++) {
            UIUtils.setBaseCustomTabLayout(mTabLayout, tabArray[i], i == 0, 45);
        }
        initFragments();
    }

    private void initFragments() {
        BallTeamDetailsFragment mTeamDetailsFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BALL_TEAM_DETAILS, ARouterConfig.Key.PARCELABLE, mMyBallBean);
        mTeamMemberFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_BALL_TEAM_MEMBER, ARouterConfig.Key.PARCELABLE, mMyBallBean);
        mTeamMemberFragment.setOnPlayIdResult(new BallTeamMemberFragment.onPlayIdResult() {
            @Override
            public void resultMyPlayId(long myPlayId) {
                mMyPlayerId = myPlayId;
                mTitleView.mTvSure.setVisibility(View.VISIBLE);
            }
        });
        Fragment[] fragments = {mTeamDetailsFragment, mTeamMemberFragment};
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
        mBvpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                DataUtils.checkData(mTabLayout.getTabAt(i)).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        mTitleView.setOnTitleViewClickListener(new TitleView.OnTitleViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {

            }

            @Override
            public void onSureClick(@NonNull View view) {
                showExitView(view);

            }
        });
    }

    private void showExitView(View view) {
        if (mExitWindows == null) {
            mExitWindows = new SingPopupWindows(this);
            mExitWindows.setPopupWindowsItemClickListener(v -> {
                mExitWindows.dismiss();
                String host = UIUtils.getString(R.string.you_sure_exit);
                String body;
                if (mMyBallBean.isLeader == Contast.LEADER) {
                    body = UIUtils.getString(R.string.you_sure_dissolution_of_the_team_s, mMyBallBean.teamName);
                } else {
                    body = UIUtils.getString(R.string.you_sure_exit_s_team, mMyBallBean.teamName);
                }
                HintDialog hintDialog = new HintDialog.Builder(this)
                        .setTitle(R.string.hint)
                        .setBody(SpanUtils.getTextColor(R.color.color_4, host.length(), host.length() + mMyBallBean.teamName.length(), body))
                        .setShowSingButton(false)
                        .builder();
                hintDialog.show();
                hintDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
                    @Override
                    public void onClickSure(@NonNull View view) {
                        hintDialog.dismiss();
                        if (mMyBallBean.isLeader == Contast.LEADER) {
                            mPresenter.dissolutionBallTeam(mMyBallBean.teamId);
                        } else {
                            mPresenter.exitBallTeam(mMyBallBean.teamId, mMyPlayerId);
                        }
                    }

                    @Override
                    public void onClickCancel(@NonNull View view) {
                        hintDialog.dismiss();
                    }
                });

            });
        }
        if (mMyBallBean.isLeader == Contast.LEADER) {
            mExitWindows.setContent(R.string.dissolution_of_the_team);
            mExitWindows.setContentDrawableRes(R.mipmap.icon_dissolution_team, 0, 0, 0);
        } else {
            mExitWindows.setContent(R.string.exit_ball_team);
            mExitWindows.setContentDrawableRes(R.mipmap.icon_exit, 0, 0, 0);
        }
        if (mExitWindows != null && !mExitWindows.isShowing()) {
            mExitWindows.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, ScreenUtils.dp2px(this, 20),
                    mTitleView.getMeasuredHeight() + ScreenUtils.getStatuWindowsHeight(this));
        }

    }

    @Override
    protected BallTeamDetailsPresenter createPresenter() {
        return new BallTeamDetailsPresenter(this);
    }


    @Override
    public void resultBallTeamSucceed() {
        setResult(RESULT_OK);
        finish();
    }

}
