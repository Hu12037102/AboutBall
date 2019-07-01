package com.work.guaishouxingqiu.aboutball.splash.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.home.bean.EventOpenTypeBean;
import com.work.guaishouxingqiu.aboutball.login.activity.LoginActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.other.ActivityManger;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.other.UserManger;
import com.work.guaishouxingqiu.aboutball.permission.PermissionActivity;
import com.work.guaishouxingqiu.aboutball.permission.imp.OnPermissionsResult;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.splash.contract.WelcomeContract;
import com.work.guaishouxingqiu.aboutball.splash.presenter.WelcomePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.PhoneUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.TeamBallInviteDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.start.activity
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/4
 * 描  述 :  ${TODO}欢迎页每次打开都会有
 *
 * @author ：
 */

@Route(path = ARouterConfig.Path.ACTIVITY_WELCOME)
public class WelcomeActivity extends PermissionActivity<WelcomePresenter> implements WelcomeContract.View {

    private static final int WHAT = 100;
    private int mTimeLength = 3;
    @BindView(R.id.iv_content)
    ImageView mIvContent;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.iv_bottom)
    ImageView ivBottom;
    private TeamBallInviteDialog mTeamInviteDialog;
    private Handler mSkipHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (mTimeLength > 1) {
                mTimeLength--;
                mTvSkip.setText(UIUtils.getString(R.string.skip_s_second, mTimeLength));
                mSkipHandler.sendEmptyMessageDelayed(WHAT, 1000);
            } else {
                skipActivity();
            }
            return true;
        }
    });
    private Long mTeamId;

    private void skipActivity() {
        mSkipHandler.removeMessages(WHAT, null);
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (sp.getBoolean(SharedPreferencesHelp.KEY_GUIDE_OPEN)) {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MAIN);
        } else {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GUIDE);
        }
        finish();
    }

    @Override
    public void initPermission() {
        requestPermission(new OnPermissionsResult() {
                              @Override
                              public void onAllow(List<String> allowPermissions) {
                                  WelcomeActivity.super.initPermission();
                              }

                              @Override
                              public void onNoAllow(List<String> noAllowPermissions) {
                                  Toasts.with().showToast(R.string.must_permission);
                                  initPermission();
                              }

                              @Override
                              public void onForbid(List<String> noForbidPermissions) {
                                  showForbidPermissionDialog();
                              }
                          }, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        mTvSkip.setText(UIUtils.getString(R.string.skip_s_second, mTimeLength));
        mSkipHandler.sendEmptyMessageDelayed(WHAT, 1000);
        initOpenAgreement();


    }

    private void initOpenAgreement() {
        Uri uri = mIntent.getData();
        if (uri != null) {
            LogUtils.w("initOpenAgreement--", uri.toString());
            String data = uri.toString();
            if (DataUtils.isEmpty(data) || !DataUtils.hasDigit(data)) {
                return;
            }
            String typeId = uri.getQueryParameter(ARouterConfig.Key.SHARE_TYPE);
            String shareId = uri.getQueryParameter(ARouterConfig.Key.SHARE_ID);
            if (typeId != null && shareId != null) {
                switch (Integer.valueOf(typeId)) {
                    case IApiService.TypeId.OPEN_BALL_INVITE:
                        mSkipHandler.removeMessages(WHAT, null);
                        mTeamId = Long.valueOf(shareId);
                        if (UserManger.get().isLogin()) {
                            mPresenter.loadTeamDetails(mTeamId);
                        } else {
                            mViewModel.showLoginDialog();
                        }
                        break;
                    case IApiService.TypeId.OPEN_GAME_DETAILS_VIDEO:
                        mSkipHandler.removeMessages(WHAT, null);
                        int matchId = Integer.valueOf(shareId);
                        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_GAME_DETAILS, ARouterConfig.Key.GAME_ID, matchId);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void resultTeamDetails(ResultBallDetailsBean bean) {
        super.resultTeamDetails(bean);
        //  mViewModel.showLoginDialog();
        if (mTeamInviteDialog == null) {
            mTeamInviteDialog = new TeamBallInviteDialog(this, bean);
        }
        if (!this.isFinishing() && !mTeamInviteDialog.isShowing()) {
            mTeamInviteDialog.show();
        }
        mTeamInviteDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mPresenter.joinTeam(bean.teamId);

            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mTeamInviteDialog.dismiss();
                openDialogFinishActivity();
            }
        });
        mTeamInviteDialog.setOnDismissListener(dialog -> mSkipHandler.sendEmptyMessageDelayed(WHAT, 1000));
    }

    @Override
    public void resultJoinTeamSucceed() {
        if (mTeamInviteDialog != null && mTeamInviteDialog.isShowing()) {
            mTeamInviteDialog.dismiss();
            openDialogFinishActivity();

        }
    }

    private void openDialogFinishActivity() {
        LogUtils.w("resultJoinTeamSucceed--", ActivityManger.get().getActivitySize() + "--");
        if (ActivityManger.get().getActivitySize() > 1) {
            finish();
        }
        PhoneUtils.launchBackgroundApp(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSkipHandler.removeMessages(WHAT, null);
    }

    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }


    @OnClick(R.id.tv_skip)
    public void onViewClicked() {
        skipActivity();
    }

    @Override
    public void onBackPressed() {
        mSkipHandler.removeMessages(WHAT, null);
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (requestCode == LoginActivity.REQUEST_CODE_LOGIN) {
                    mPresenter.loadTeamDetails(mTeamId);
                }
                break;
            default:
                break;
        }
    }
}
