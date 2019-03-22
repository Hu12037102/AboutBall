package com.work.guaishouxingqiu.aboutball.game.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.game.contract.GameDetailsContract;
import com.work.guaishouxingqiu.aboutball.game.presenter.GameDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        layoutParams.height = ScreenUtils.dp2px(this, 220)+ ScreenUtils.getStatuWindowsHeight(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected GameDetailsPresenter createPresenter() {
        return new GameDetailsPresenter(this);
    }


}
