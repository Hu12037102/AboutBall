package com.work.guaishouxingqiu.aboutball.game.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameCollectionFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameCommentFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameDataFragment;
import com.work.guaishouxingqiu.aboutball.game.fragment.GameResultFragment;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;
import com.work.guaishouxingqiu.aboutball.weight.FocusableTextView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 15:11
 * 更新时间: 2019/3/22 15:11
 * 描述:比赛详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_GAME_DETAILS)
public class GameDetailsActivity extends PermissionActivity<GameDetailsPresenter>
        implements GameDetailsContract.View {
    @BindView(R.id.civ_left)
    CircleImageView mCivLeft;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.civ_right)
    CircleImageView mCivRight;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.ll_live_details)
    LinearLayout mLlLiveDetails;
    @BindView(R.id.cl_head)
    ConstraintLayout mClHead;
    @BindView(R.id.tb_data)
    TabLayout mTbData;
    @BindView(R.id.bv_data)
    BaseViewPager mBvData;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_defails;
    }

    @Override
    protected void initStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = mClHead.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ScreenUtils.dp2px(this, 220) + ScreenUtils.getStatuWindowsHeight(this);

    }

    @Override
    protected void initData() {
        int gameId = mIntent.getIntExtra(ARouterConfig.Key.GAME_ID, -1);
        mPresenter.loadGameSimple(gameId);


    }


    private void initTabData() {
        if (mTbData.getTabCount() == 0) {
            String[] tabArray = getResources().getStringArray(R.array.game_details_tab_array);
            for (int i = 0; i < tabArray.length; i++) {
                mTbData.addTab(mTbData.newTab().setText(tabArray[i]), i == 0);
            }

            mTbData.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mBvData.setCurrentItem(tab.getPosition(), true);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void initPagerData(ResultGameSimpleBean bean) {
        if (mPagerAdapter == null) {
            GameResultFragment resultFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_RESULT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameDataFragment dataFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_DATA, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameCommentFragment commentFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COMMENT, ARouterConfig.Key.GAME_DETAILS_BEAN, bean);
            GameCollectionFragment collectionFragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_GAME_COLLECTION);
            Fragment[] fragments = {resultFragment, dataFragment, commentFragment, collectionFragment};
            mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int i) {
                    return fragments[i];
                }

                @Override
                public int getCount() {
                    return fragments.length;
                }
            };
            mBvData.setOffscreenPageLimit(fragments.length);
            mBvData.setAdapter(mPagerAdapter);

            mBvData.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    DataUtils.checkData(mTbData.getTabAt(i)).select();
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected GameDetailsPresenter createPresenter() {
        return new GameDetailsPresenter(this);
    }


    @Override
    public void resultGameSimple(@NonNull ResultGameSimpleBean bean) {
        initTabData();
        initPagerData(bean);

        GlideManger.get().loadImage(this, bean.hostLogoUrl, R.mipmap.icon_image_background,
                R.mipmap.icon_image_background, mCivLeft);
        GlideManger.get().loadImage(this, bean.guestLogoUrl, R.mipmap.icon_image_background,
                R.mipmap.icon_image_background, mCivRight);
        mTvLeft.setText(bean.hostName);
        mTvRight.setText(bean.guestName);
        if (mLlLiveDetails.getChildCount() > 0) {
            mLlLiveDetails.removeAllViews();
        }
        switch (bean.stateId) {
            //比赛未开始
            case Contast.GAME_STATUS_NO_START:
                View noStartView = getLayoutInflater().inflate(R.layout.layout_live_no_start_view, mLlLiveDetails, false);
                mLlLiveDetails.addView(noStartView);
                TextView tvTime = noStartView.findViewById(R.id.tv_time);
                tvTime.setText(bean.startTime);
                break;
            //比赛进行中
            case Contast.GAME_STATUS_STARTING:
                //比赛结束
            case Contast.GAME_STATUS_FINISH:
                View startView = getLayoutInflater().inflate(R.layout.layout_watch_live_body_view, mLlLiveDetails, false);
                mLlLiveDetails.addView(startView);
                FocusableTextView tVTitle = startView.findViewById(R.id.tv_title);
                TextView mTvStatus = startView.findViewById(R.id.tv_status);
                if (bean.stateId == Contast.GAME_STATUS_STARTING) {
                    mTvStatus.setText(R.string.watch_live);
                } else {
                    mTvStatus.setText(R.string.watch_collection);
                }
                tVTitle.setText(bean.gameName);
                TextView tVGrade = startView.findViewById(R.id.tv_grade);
                tVGrade.setText(bean.hostScore.concat(" - ").concat(bean.guestScore));
                break;


        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }

    }
}
