package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.AboutBallDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 15:32
 * 更新时间: 2019/5/16 15:32
 * 描述:约球详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ABOUT_BALL_DETAILS)
public class AboutBallDetailsActivity extends BaseActivity<AboutBallDetailsPresenter> implements AboutBallDetailsContract.View {
    @BindView(R.id.civ_logo)
    CircleImageView mCivLogo;
    @BindView(R.id.civ_logo_join)
    CircleImageView mCivLogoJoin;
    @BindView(R.id.tv_bottom_left)
    TextView mTvBottomLeft;
    @BindView(R.id.tv_bottom_right)
    TextView mTvBottomRight;
    @BindView(R.id.rl_bottom_multiple)
    RelativeLayout mRlBottomMultiple;
    @BindView(R.id.tv_sing)
    TextView mTvSing;
    @BindView(R.id.rl_bottom_sing)
    RelativeLayout mRlBottomSing;
    @BindView(R.id.tv_top_team_name)
    TextView mTvTopTeamName;
    @BindView(R.id.tv_bottom_team_name)
    TextView mTvBottomTeamName;
    @BindView(R.id.item_site)
    ItemView mItemSite;
    @BindView(R.id.item_date)
    ItemView mItemDate;
    @BindView(R.id.item_time)
    ItemView mItemTime;
    @BindView(R.id.item_money)
    ItemView mItemMoney;
    @BindView(R.id.tv_team_content)
    TextView mTvTeamContent;
    @BindView(R.id.cl_bottom_team)
    ViewGroup mClBottomTeam;
    @BindView(R.id.cl_top_team)
    ViewGroup mClTopTeam;
    private int mRefereeStatus;
    private int mTeamStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_ball_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }
        mRefereeStatus = bundle.getInt(ARouterConfig.Key.REFEREE_STATUS, -1);
        mTeamStatus = bundle.getInt(ARouterConfig.Key.TEAM_STATUS, -1);
        long offerId = bundle.getLong(ARouterConfig.Key.OFFER_ID, -1);
        if (mRefereeStatus == -1 || mTeamStatus == -1 || offerId == -1) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }
        mPresenter.loadDetails(offerId);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected AboutBallDetailsPresenter createPresenter() {
        return new AboutBallDetailsPresenter(this);
    }

    @Override
    public void resultDetails(ResultAboutBallDetailsBean bean) {
        UIUtils.setText(mTvTopTeamName, bean.hostTeamName);
        GlideManger.get().loadLogoImage(this, bean.hostTeamLogo, mCivLogo);
        UIUtils.setText(mItemSite.mTvRight, bean.stadiumName);
        UIUtils.setText(mItemDate.mTvRight, DateUtils.getDate(bean.startTime));
        UIUtils.setText(mItemMoney.mTvRight, DateUtils.getHourMinutes(bean.startTime) + "-" + DateUtils.getHourMinutes(bean.endTime));
        String host = "报名队伍";
        //缺对手
        if (mTeamStatus == Contast.HAS_RIVAL) {
            String body = "暂无队伍";
            String content = host + "\n" + body;
            mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), content));
            mClBottomTeam.setVisibility(View.INVISIBLE);
            //缺裁判
            if (mRefereeStatus == Contast.HAS_REFEREE) {
                mRlBottomMultiple.setVisibility(View.VISIBLE);
                mRlBottomSing.setVisibility(View.GONE);
                mTvBottomRight.setEnabled(true);
                mTvBottomRight.setBackgroundResource(R.drawable.shape_click_button);
                mTvBottomLeft.setEnabled(true);
            } else {
                mRlBottomMultiple.setVisibility(View.VISIBLE);
            }
        } else {
            mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), host));
            mClBottomTeam.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right, R.id.tv_sing, R.id.cl_bottom_team, R.id.cl_top_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_left:
                break;
            case R.id.tv_bottom_right:
                break;
            case R.id.tv_sing:
                break;
            case R.id.cl_bottom_team:
                break;
            case R.id.cl_top_team:
                break;
        }
    }


}
