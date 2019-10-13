package com.ifeell.app.aboutball.venue.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.my.fragment.PostAllEvaluationFragment;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.weight.BaseViewPager;

import butterknife.BindView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/3 11:54
 * 更新时间: 2019/7/3 11:54
 * 描述:我的
 */
@Route(path = ARouterConfig.Path.ACTIVITY_MY_TEAM_EVALUATE)
public class MyTeamEvaluateActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.bvp_content)
    BaseViewPager mBvpContent;
    private long mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team_evaluate;
    }

    @Override
    public void initPermission() {
        mId = mIntent.getLongExtra(ARouterConfig.Key.ID, -1);
        if (mId == -1) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        super.initPermission();
    }

    @Override
    protected void initView() {
        Bundle myTeamBundle = new Bundle();
        myTeamBundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.TEAMMATE);
        myTeamBundle.putLong(ARouterConfig.Key.ID, mId);
        PostAllEvaluationFragment fragment = ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_ALL_EVALUATION,myTeamBundle);
        Fragment[] fragments = {fragment};
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

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}
