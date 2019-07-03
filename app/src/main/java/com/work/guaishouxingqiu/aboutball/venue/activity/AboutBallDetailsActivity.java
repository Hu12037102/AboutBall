package com.work.guaishouxingqiu.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.item.util.ScreenUtils;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseActivity;
import com.work.guaishouxingqiu.aboutball.commonality.activity.LoginOrShareActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.WaitUserOrderDetailsActivity;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;
import com.work.guaishouxingqiu.aboutball.util.SpanUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.AboutBallDetailsContract;
import com.work.guaishouxingqiu.aboutball.venue.presenter.AboutBallDetailsPresenter;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/16 15:32
 * 更新时间: 2019/5/16 15:32
 * 描述:约球详情Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_ABOUT_BALL_DETAILS)
public class AboutBallDetailsActivity extends LoginOrShareActivity<AboutBallDetailsPresenter> implements AboutBallDetailsContract.View {
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
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.rl_cancel)
    RelativeLayout mRlCancel;
    private int mHasRefereeStatus;
    private int mHasTeamStatus;
    private ResultAboutBallDetailsBean mResultBean;
    private Integer mRefereeStatus;
    private HintDialog mRequestRefereeDialog;
    private long mAgreeId;
    private HintDialog mPlayRefereeDialog;
    public static final int REQUEST_CODE = 123;
    private static final int REQUEST_CODE_ORDER_USER = 124;
    private int mAboutBallFlag = -1;
    private HintDialog mCancelBallDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_ball_details;
    }

    @Override
    protected void initView() {
        mTitleView.mTvSure.setVisibility(View.GONE);
        mClTopTeam.setEnabled(false);
        mClBottomTeam.setEnabled(false);
        mTvBottomLeft.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        Bundle bundle = mIntent.getExtras();
        if (bundle == null) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }
        mHasRefereeStatus = bundle.getInt(ARouterConfig.Key.REFEREE_STATUS, -1);
        mHasTeamStatus = bundle.getInt(ARouterConfig.Key.TEAM_STATUS, -1);
        //mAboutBallFlag 0,参加约球1，取消约球
        mAboutBallFlag = bundle.getInt(ARouterConfig.Key.ABOUT_BALL_FLAG, 0);
        mAgreeId = bundle.getLong(ARouterConfig.Key.OFFER_ID, -1);


        if (mAboutBallFlag == -1 || mAgreeId == -1) {
            UIUtils.showToast(R.string.not_this_ball_team_details);
            finish();
            return;
        }
        mPresenter.loadDetails(mAgreeId);
        mPresenter.judgeRefereeStatus();
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick(@NonNull View view) {
                clickBack();
            }
        });
        mTitleView.setOnSureViewClickListener(new TitleView.OnSureViewClickListener() {
            @Override
            public void onSureClick(@NonNull View view) {
                if (mResultBean != null) {
                    showShareDialog(mViewModel.getAboutBallDetailShareBean(mAgreeId, mResultBean.startTime, mResultBean.stadiumName));
                }
            }
        });
    }

    @Override
    protected AboutBallDetailsPresenter createPresenter() {
        return new AboutBallDetailsPresenter(this);
    }

    @Override
    public void resultDetails(ResultAboutBallDetailsBean bean) {
        mClTopTeam.setEnabled(true);
        mClBottomTeam.setEnabled(true);
        mResultBean = bean;
        mTitleView.mTvSure.setVisibility(View.VISIBLE);
        UIUtils.setText(mTvTopTeamName, bean.hostTeamName);
        GlideManger.get().loadLogoImage(this, bean.hostTeamLogo, mCivLogo);
        GlideManger.get().loadLogoImage(this, bean.guestTeamLogo, mCivLogoJoin);
        UIUtils.setText(mTvBottomTeamName, bean.guestTeamName);
        UIUtils.setText(mItemSite.mTvRight, bean.stadiumName);
        UIUtils.setText(mItemDate.mTvRight, DateUtils.getDate(bean.startTime));
        UIUtils.setText(mItemTime.mTvRight, DateUtils.getHourMinutes(bean.startTime) + "-" + DateUtils.getHourMinutes(bean.endTime));
        UIUtils.setText(mItemMoney.mTvRight, DataUtils.getMoneyFormat(bean.cost));
        if (mAboutBallFlag == 1) {
            if (DateUtils.isNewTimeMoreThan(bean.startTime)) {
                mRlCancel.setVisibility(View.GONE);
            } else {
                mRlCancel.setVisibility(View.VISIBLE);
            }

            LinearLayout.LayoutParams tvTeamParams = (LinearLayout.LayoutParams) mTvTeamContent.getLayoutParams();
            String host = "报名队伍";
            if (bean.guestTeamId <= 0) {
                mClBottomTeam.setVisibility(View.GONE);
                String body = "暂无队伍";
                String content = host + "\n" + body;
                mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), content));
                mTvTeamContent.setGravity(Gravity.TOP);
                // tvTeamParams.gravity = Gravity.TOP;
                tvTeamParams.height = ScreenUtils.dp2px(this, 115);
                mTvTeamContent.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20), 0, 0);

            } else {
                mClBottomTeam.setVisibility(View.VISIBLE);
                mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), host));
                mClBottomTeam.setVisibility(View.VISIBLE);
                mTvTeamContent.setGravity(Gravity.BOTTOM);
                //tvTeamParams.gravity = Gravity.BOTTOM;
                tvTeamParams.height = ScreenUtils.dp2px(this, 40);
                mTvTeamContent.setPadding(ScreenUtils.dp2px(this, 20), 0, 0, 0);
            }
            mTvTeamContent.setLayoutParams(tvTeamParams);
        } else {
            mRlCancel.setVisibility(View.GONE);
            notifyRefereeTeamStatus(bean);
        }


    }

    /**
     * 判断裁判和队伍状态
     *
     * @param bean
     */
    private void notifyRefereeTeamStatus(ResultAboutBallDetailsBean bean) {
        String host = "报名队伍";
        LinearLayout.LayoutParams tvTeamParams = (LinearLayout.LayoutParams) mTvTeamContent.getLayoutParams();
        tvTeamParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //缺对手
        if (mHasTeamStatus == Contast.HAS_RIVAL) {
            String body = "暂无队伍";
            String content = host + "\n" + body;
            mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), content));
            mClBottomTeam.setVisibility(View.GONE);

            mTvTeamContent.setGravity(Gravity.TOP);
            // tvTeamParams.gravity = Gravity.TOP;
            tvTeamParams.height = ScreenUtils.dp2px(this, 115);
            mTvTeamContent.setPadding(ScreenUtils.dp2px(this, 20), ScreenUtils.dp2px(this, 20), 0, 0);


            //缺裁判
            if (mHasRefereeStatus == Contast.HAS_REFEREE) {
                mRlBottomMultiple.setVisibility(View.VISIBLE);
                mRlBottomSing.setVisibility(View.GONE);
                mTvBottomRight.setEnabled(true);
                mTvBottomRight.setBackgroundResource(R.drawable.shape_click_button);
                mTvBottomLeft.setEnabled(true);
            } else {
                mRlBottomSing.setVisibility(View.VISIBLE);
                mRlBottomMultiple.setVisibility(View.GONE);
                mTvSing.setText(R.string.invited_to_the_ball_about);
            }
        } else {
            mTvTeamContent.setText(SpanUtils.getTextSize(17, 0, host.length(), host));
            mClBottomTeam.setVisibility(View.VISIBLE);
            mTvTeamContent.setGravity(Gravity.BOTTOM);
            //tvTeamParams.gravity = Gravity.BOTTOM;
            tvTeamParams.height = ScreenUtils.dp2px(this, 40);
            mTvTeamContent.setPadding(ScreenUtils.dp2px(this, 20), 0, 0, 0);

            //缺裁判
            if (mHasRefereeStatus == Contast.HAS_REFEREE) {
                mRlBottomMultiple.setVisibility(View.VISIBLE);
                mRlBottomSing.setVisibility(View.GONE);
                mTvBottomRight.setEnabled(false);
                mTvBottomRight.setBackgroundResource(R.drawable.shape_default_button);
                mTvBottomLeft.setEnabled(true);
            } else {
                mRlBottomSing.setVisibility(View.GONE);
                mRlBottomMultiple.setVisibility(View.GONE);
                //  mTvSing.setText(R.string.invited_to_the_ball_about);
            }

        }
        mTvTeamContent.setLayoutParams(tvTeamParams);
    }

    @Override
    public void resultPlayReferee() {
        mHasRefereeStatus = Contast.HAS_NO_REFEREE;
        if (mResultBean != null) {
            resultDetails(mResultBean);
        }
    }

    /**
     * 取消约球后，让用户去订单详情中自己选择
     *
     * @param orderId
     */
    @Override
    public void resultCancelAboutBall(long orderId) {
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_WAIT_USER_ORDER_DETAILS, this, ARouterConfig.Key.ORDER_ID, orderId, AboutBallDetailsActivity.REQUEST_CODE_ORDER_USER);
    }

    private void startActivityForBallTeamDetails(int teamId, String shirtColor) {
    }

    @OnClick({R.id.tv_bottom_left, R.id.tv_bottom_right, R.id.tv_sing, R.id.cl_bottom_team, R.id.cl_top_team, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_left:
                clickAsReferee();
                break;
            case R.id.tv_bottom_right:
            case R.id.tv_sing:
                // ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_INVITATION_BALL, this, ARouterConfig.Key.PARCELABLE, mResultBean, AboutBallDetailsActivity.REQUEST_CODE);
                mViewModel.startActivityToInvitation(mResultBean.agreeId, mResultBean.calendarId, AboutBallDetailsActivity.REQUEST_CODE);
                break;
            case R.id.cl_bottom_team:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS_VENUE, ARouterConfig.Key.TEAM_ID, mResultBean.guestTeamId, ARouterConfig.Key.SHIRT_COLOR, mResultBean.hostShirtColor);
                break;
            case R.id.cl_top_team:
                ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_BALL_TEAM_DETAILS_VENUE, ARouterConfig.Key.TEAM_ID, mResultBean.hostTeamId, ARouterConfig.Key.SHIRT_COLOR, mResultBean.guestShirtColor);
                break;
            case R.id.tv_cancel:
                if (mCancelBallDialog == null) {
                    mCancelBallDialog = new HintDialog.Builder(this)
                            .setTitle(R.string.hint)
                            .setBody(R.string.you_sure_cancel_about_ball)
                            .setShowSingButton(false)
                            .builder();
                }
                if (!mCancelBallDialog.isShowing()) {
                    mCancelBallDialog.show();
                }
                mCancelBallDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
                    @Override
                    public void onClickSure(@NonNull View view) {
                        mPresenter.cancelAboutBall(mAgreeId);
                    }

                    @Override
                    public void onClickCancel(@NonNull View view) {

                    }
                });

                break;
            default:
                break;
        }
    }

    private void requestRefereeDialog() {
        if (mRequestRefereeDialog == null) {
            mRequestRefereeDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_not_referee_is_request)
                    .setShowSingButton(false)
                    .setCancel(R.string.cancel)
                    .setSures(R.string.my_as_the_referee)
                    .builder();
        }
        if (!mRequestRefereeDialog.isShowing()) {
            mRequestRefereeDialog.show();
        }
        mRequestRefereeDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_APPLY_REFEREE, AboutBallDetailsActivity.this);
            }

            @Override
            public void onClickCancel(@NonNull View view) {

            }
        });
    }

    /**
     * 担任裁判
     */
    private void clickAsReferee() {
        if (mRefereeStatus == null) {
            requestRefereeDialog();
        } else {
            switch (mRefereeStatus) {
                case Contast.RefereeStatus.REFEREE_0:
                    UIUtils.showToast(R.string.your_application_for_referee_is_under_review);
                    break;
                case Contast.RefereeStatus.REFEREE_1:
                    playRefereeDialog();
                    break;
                case Contast.RefereeStatus.REFEREE_2:
                    requestRefereeDialog();
                    break;
                case Contast.RefereeStatus.REFEREE_3:
                    playRefereeDialog();
                    break;
                default:
                    break;
            }
        }

    }

    private void playRefereeDialog() {
        if (mPlayRefereeDialog == null) {
            mPlayRefereeDialog = new HintDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setBody(R.string.umpire)
                    .setShowSingButton(false)
                    .setCancel(R.string.cancel)
                    .setSures(R.string.sure)
                    .builder();
        }
        if (!mPlayRefereeDialog.isShowing()) {
            mPlayRefereeDialog.show();
        }
        mPlayRefereeDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mPresenter.playReferee(mAgreeId);
            }

            @Override
            public void onClickCancel(@NonNull View view) {

            }
        });

    }

    @Override
    public void resultRefereeStatus(Integer status) {
        super.resultRefereeStatus(status);
        this.mRefereeStatus = status;
        mTvBottomLeft.setEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //裁判状态
                case ARouterIntent.REQUEST_CODE:
                    mPresenter.judgeRefereeStatus();
                    break;
                //应邀约球
                case AboutBallDetailsActivity.REQUEST_CODE:
                    mHasTeamStatus = 1;
                    mPresenter.loadDetails(mAgreeId);
                    break;
               /* //取消约球
                case AboutBallDetailsActivity.REQUEST_CODE_ORDER_USER:
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;*/
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    private void clickBack() {
        Intent intent = new Intent();
        intent.putExtra(ARouterConfig.Key.REFEREE_STATUS, mHasRefereeStatus);
        intent.putExtra(ARouterConfig.Key.TEAM_STATUS, mHasTeamStatus);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //取消约球结果返回
    @Subscribe
    public void resultCancelAboutBall(WaitUserOrderDetailsActivity.ResultPayBean bean) {
        if (bean != null && bean.isUpdateResult) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
