package com.ifeell.app.aboutball.venue.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.BaseActivity;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.bean.RequestInvitationBallBean;
import com.ifeell.app.aboutball.venue.bean.ResultMyBallTeamBean;
import com.ifeell.app.aboutball.venue.contract.InvitationBallContract;
import com.ifeell.app.aboutball.venue.presenter.InvitationBallPresenter;
import com.ifeell.app.aboutball.weight.SelectorColorDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 13:27
 * 更新时间: 2019/5/21 13:27
 * 描述:应邀约球Activity
 */
@Route(path = ARouterConfig.Path.ACTIVITY_INVITATION_BALL)
public class InvitationBallActivity extends BaseActivity<InvitationBallPresenter> implements InvitationBallContract.View {
    @BindView(R.id.title_view)
    TitleView mTitleView;
    @BindView(R.id.item_team)
    ItemView mItemTeam;
    @BindView(R.id.item_color)
    ItemView mItemColor;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    private ResultMyBallTeamBean mMyBallTeam;
    private RequestInvitationBallBean mRequestBean;
    private static final int REQUEST_CODE_WAIT_PAY = 123;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invitaion_ball;
    }

    @Override
    protected void initView() {
        // mDetailsBean = mIntent.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
        long agreeId = mIntent.getLongExtra(ARouterConfig.Key.AGREE_ID, -1);
        long calendarId = mIntent.getLongExtra(ARouterConfig.Key.CALENDAR_ID, -1);
        if (agreeId == -1 || calendarId == -1) {
            UIUtils.showToast(R.string.not_find_ball_team_id);
            finish();
            return;
        }
        mTvCommit.setText(R.string.next);
        mItemColor.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemColor.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemColor.mTvRight.setHint(R.string.please_selector_ball_clothing_color);
        mItemTeam.mTvRight.setHintTextColor(ContextCompat.getColor(this, R.color.colorFFA6A6A6));
        mItemTeam.mTvRight.setTextColor(ContextCompat.getColor(this, R.color.color_4));
        mItemTeam.mTvRight.setHint(R.string.please_selector_ball_team);
        mRequestBean = new RequestInvitationBallBean();
        mRequestBean.agreeId = agreeId;
        mRequestBean.calendarId = calendarId == 0 ? null : calendarId;


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {
        mItemTeam.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_SELECTOR_BALL_TEAM,
                        InvitationBallActivity.this, ARouterConfig.Key.PARCELABLE, mMyBallTeam);
            }
        });
        mItemColor.setOnItemClickListener(new ItemView.OnItemClickListener() {

            private SelectorColorDialog mColorDialog;

            @Override
            public void onClickItem(View view) {
                if (mColorDialog == null) {
                    mColorDialog = new SelectorColorDialog(InvitationBallActivity.this);
                    mColorDialog.setOnColorSelectorListener((view1, color) -> {
                        mRequestBean.guestShirtColor = color;
                        mItemColor.mTvRight.setText(color);
                    });
                }
                if (!mColorDialog.isShowing() && !isFinishing()) {
                    mColorDialog.show();
                }
            }
        });
    }

    @Override
    protected InvitationBallPresenter createPresenter() {
        return new InvitationBallPresenter(this);
    }

    private boolean isSelectorTeam() {
        if (mMyBallTeam == null) {
            UIUtils.showToast(R.string.selector_ball_team);
            return false;
        }
        return true;
    }

    private boolean isSelectorTeamColor() {
        if (DataUtils.isEmpty(mRequestBean.guestShirtColor)) {
            UIUtils.showToast(R.string.please_selector_shirt_color);
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        //mPresenter.createOrder(mRequestOrderBean);
        if (isSelectorTeam() && isSelectorTeamColor()) {
            mPresenter.invitationBall(mRequestBean);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     /*   if (requestCode == ARouterIntent.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
            mItemTeam.setContentText(mMyBallTeam.teamName);
            mRequestBean.guestTeamId = mMyBallTeam.teamId;
        }*/
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ARouterIntent.REQUEST_CODE:
                    if (data == null) {
                        return;
                    }
                    mMyBallTeam = data.getParcelableExtra(ARouterConfig.Key.PARCELABLE);
                    mItemTeam.setContentText(mMyBallTeam.teamName);
                    mRequestBean.guestTeamId = mMyBallTeam.teamId;
                    break;
                case InvitationBallActivity.REQUEST_CODE_WAIT_PAY:
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void resultOrderId(long result) {
        mViewModel.startActivityToOrderPay(result, Contast.PayOrderFlag.PAY_LAUNCHER_ORDER, InvitationBallActivity.REQUEST_CODE_WAIT_PAY);
    }

    @Override
    public void resultOrderIdNull() {
        mViewModel.clickBackForResult();
    }
}
