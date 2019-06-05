package com.work.guaishouxingqiu.aboutball.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.activity.LoginActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.OrderEvaluateActivity;
import com.work.guaishouxingqiu.aboutball.my.activity.RefundActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;
import com.work.guaishouxingqiu.aboutball.my.fragment.PostEvaluationFragment;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.other.SharedPreferencesHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.venue.activity.AboutBallDetailsActivity;
import com.work.guaishouxingqiu.aboutball.venue.activity.WaitPayOrderDetailsActivity;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultRefereeBean;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.PayDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.work.guaishouxingqiu.aboutball.weight.UpdateApkDialog;

import java.lang.ref.SoftReference;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.base
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/5/11
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public class ViewModel {

    private final SoftReference<Activity> mSoftActivity;
    //  private HintDialog mUpdateDialog;
    private HintDialog mUserNoExitDialog;
    private HintDialog mLoginDialog;
    private HintDialog mNotLoginDialog;
    private HintDialog mCancelOrderDialog;
    private UpdateApkDialog mUpdateDialog;

    static ViewModel createViewModel(Activity activity) {
        return new ViewModel(activity);
    }

    private ViewModel(Activity activity) {
        mSoftActivity = new SoftReference<>(activity);

    }

    public void resultBaseData(@NonNull BaseBean baseBean) {
        Activity activity = mSoftActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        switch (baseBean.code) {
            case IApi.Code.USER_NO_EXIST:
                if (mUserNoExitDialog == null) {
                    mUserNoExitDialog = new HintDialog.Builder(activity)
                            .setTitle(R.string.hint)
                            .setBody(R.string.this_phone_not_register)
                            .setSure(R.string.go_register)
                            .builder();
                }
                if (!mUserNoExitDialog.isShowing()) {
                    mUserNoExitDialog.show();
                }
                mUserNoExitDialog.setOnItemClickListener(view -> {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER, ARouterConfig.Key.LOGIN_STATUS, Contast.LoginStatus.REGISTER);
                    mUserNoExitDialog.dismiss();
                });
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.SERVICE_ERROR:
                Toasts.with().showToast(baseBean.message);
                break;
            case IApi.Code.USER_EXIST:
                if (mLoginDialog == null) {
                    mLoginDialog = new HintDialog.Builder(activity)
                            .setTitle(R.string.hint)
                            .setBody(R.string.this_phone_is_register)
                            .setSure(R.string.login_immediately)
                            .builder();
                }
                if (!mLoginDialog.isShowing()) {
                    mLoginDialog.show();
                }
                mLoginDialog.setOnItemClickListener(view -> {
                    activity.finish();
                    mLoginDialog.dismiss();
                });
                break;
            case IApi.Code.USER_NOT_LOGIN:
                showLoginDialog();
                break;
            default:
                break;
        }
    }

    public void showUpdateDialog(Context context, ResultUpdateApkBean updateBean) {
        String content = UIUtils.getString(R.string.update_content);
        if (mUpdateDialog == null) {
            mUpdateDialog = new UpdateApkDialog(context, content, false);
        }
        if (!mUpdateDialog.isShowing()) {
            mUpdateDialog.show();
        }
        mUpdateDialog.setOnClickUpdateViewListener(new UpdateApkDialog.OnClickUpdateViewListener() {
            @Override
            public void clickUpdate(View view) {
                DownloadApkHelp.loadApk(UIUtils.getContext(), updateBean.updateUrl);
            }
        });
      /*  if (mUpdateDialog == null) {
            mUpdateDialog = new HintDialog.Builder(context)
                    .setTitle(R.string.update_apk)
                    .setBody(R.string.update_content)
                    .setSure(R.string.sure)
                    .builder();
        }
        mUpdateDialog.setContentGravity(Gravity.LEFT);
        mUpdateDialog.setCanceledOnTouchOutside(false);
        mUpdateDialog.setCancelable(false);
        if (!mUpdateDialog.isShowing()) {
            mUpdateDialog.show();
        }
        mUpdateDialog.setOnItemClickListener(new HintDialog.OnItemClickListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                if (view instanceof TextView){
                    TextView textView = (TextView) view;
                    textView.setTextColor(ContextCompat.getColor(mSoftActivity.get(),R.color.colorFFA6A6A6));
                    textView.setText("正在下载...");
                }
                DownloadApkHelp.loadApk(UIUtils.getContext(), updateBean.updateUrl);
                view.setEnabled(false);
              //  mUpdateDialog.dismiss();
            }
        });*/
        /*mUpdateDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                DownloadApkHelp.loadApk(UIUtils.getContext(), updateBean.updateUrl);
                mUpdateDialog.dismiss();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mUpdateDialog.dismiss();
            }
        });*/

    }


    /**
     * 显示用户登陆dialog
     */
    public void showLoginDialog() {
        Activity activity = mSoftActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (mNotLoginDialog == null) {
            mNotLoginDialog = new HintDialog.Builder(activity)
                    .setTitle(R.string.hint)
                    .setBody(R.string.is_go_to_login)
                    .setSure(R.string.login_immediately)
                    .builder();
        }
        if (!mNotLoginDialog.isShowing()) {
            mNotLoginDialog.show();
        }
        mNotLoginDialog.setOnItemClickListener(view -> {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_LOGIN, mSoftActivity.get(), LoginActivity.REQUEST_CODE_LOGIN);
            mNotLoginDialog.dismiss();
        });
    }

    public void startActivityToOrderPay(long orderId, int flag) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_FLAG, flag);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, bundle);
    }

    public void startActivityToOrderPay(long orderId, int flag, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_FLAG, flag);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, mSoftActivity.get(), bundle, requestCode);
    }

    public void startActivityForResultToOrderPay(long orderId, int flag, Fragment fragment, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_FLAG, flag);
        ARouterIntent.startActivityForResult(fragment, WaitPayOrderDetailsActivity.class, bundle, requestCode);
        //ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, bundle);
    }

    /**
     * 跳转到订单评价页面
     *
     * @param venueName 场馆名称
     * @param time      预定时间
     * @param site      预定场地
     */
    public void startActivityToEvaluate(String venueName, String time, String site, long orderId, int requestCode, boolean isActivity, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString(ARouterConfig.Key.VENUE_NAME, venueName);
        bundle.putString(ARouterConfig.Key.TARGET_DATE, time);
        bundle.putString(ARouterConfig.Key.TARGET_SITE, site);
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        if (isActivity) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ORDER_EVALUATE, mSoftActivity.get(), bundle, requestCode);
        } else if (fragment != null) {
            ARouterIntent.startActivityForResult(fragment, OrderEvaluateActivity.class, bundle, requestCode);
        }
    }


    public void onDestroy() {
        Activity activity = mSoftActivity.get();
        if (activity != null) {
            mSoftActivity.clear();
        }
    }


    /**
     * 取消订单
     */
    public void showCancelOrderDialog(BaseDialog.OnItemClickSureAndCancelListener listener) {
        if (mCancelOrderDialog == null) {
            mCancelOrderDialog = new HintDialog.Builder(mSoftActivity.get())
                    .setShowSingButton(false)
                    .setTitle(R.string.hint)
                    .setBody(R.string.cancel_order_body)
                    .builder();
        }
        if (!mCancelOrderDialog.isShowing()) {
            mCancelOrderDialog.show();
        }
        mCancelOrderDialog.setOnItemClickSureAndCancelListener(listener);
    }

    public void clickBackForResult() {
        Activity activity = mSoftActivity.get();
        activity.setResult(Activity.RESULT_OK);
        if (!activity.isFinishing()) {
            mSoftActivity.get().finish();
        }
    }

    public void setNoDataView(@NonNull SmartRefreshLayout refreshLayout, boolean hasData) {
        //refreshLayout.resetNoMoreData();
        refreshLayout.setNoMoreData(hasData);
    }

    public void showPayDialog(@NonNull String money, @NonNull PayDialog.OnPayDialogClickListener listener) {
        PayDialog payDialog = new PayDialog(mSoftActivity.get())
                .setMoney(money);
        payDialog.setOnPayDialogClickListener(listener);

    }

    public void weiChatPay(ResultWeiChatSingBean bean) {

        if (bean == null) {
            UIUtils.showToast(R.string.not_find_wei_chat_sing);
            return;
        }
        IWXAPI wxApi = ((BaseActivity) (mSoftActivity.get())).getBaseApplication().getWeiChatApi();
        if (wxApi.isWXAppInstalled()) {
     /*  IWXAPI mWeiChatApi = WXAPIFactory.createWXAPI(mSoftActivity.get(), Contast.SecretKey.WEICHAT_APP_ID, false);
        mWeiChatApi.registerApp(Contast.SecretKey.WEICHAT_APP_ID);*/
            PayReq req = new PayReq();
            // req.appId = Contast.SECRET_KEY.WEICHAT_APP_ID;
            req.appId = bean.appid;
            //req.partnerId = Contast.SECRET_KEY.WEI_CHAT_BUSINESS_ID;
            req.partnerId = bean.partnerid;
            req.prepayId = bean.prepayid;
            req.nonceStr = bean.noncestr;
            req.packageValue = "Sign=WXPay";
            req.sign = bean.sign;
            req.timeStamp = bean.timestamp;
            wxApi.sendReq(req);
        } else {
            showInstallWeiChatDialog();
        }


    }

    /**
     * 申请退款页面
     *
     * @param venueName
     * @param time
     * @param site
     * @param orderId
     * @param money
     */
    public void toRefundActivityToResult(String venueName, String time, String site, long orderId, String money, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString(ARouterConfig.Key.VENUE_NAME, venueName);
        bundle.putString(ARouterConfig.Key.TARGET_DATE, time);
        bundle.putString(ARouterConfig.Key.TARGET_SITE, site);
        bundle.putString(ARouterConfig.Key.MONEY, money);
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        if (fragment != null) {
            ARouterIntent.startActivityForResult(fragment, RefundActivity.class, bundle);
        } else {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_REFUND, mSoftActivity.get(), bundle);
        }

    }

    public void setActivityForAboutBallDetails(long offerId, int flag) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.OFFER_ID, offerId);
        bundle.putInt(ARouterConfig.Key.ABOUT_BALL_FLAG, flag);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_ABOUT_BALL_DETAILS, bundle);
    }

    public void showInstallWeiChatDialog() {
        HintDialog hintDialog = new HintDialog.Builder(mSoftActivity.get())
                .setTitle(R.string.hint)
                .setBody(R.string.you_not_installed_weichat)
                .setSure(R.string.sure)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tencent.mm"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mSoftActivity.get().startActivity(intent);
            hintDialog.dismiss();
        });
    }

    /**
     * 查看队友评论
     *
     * @param teamId
     */
    public void startActivityToPostEvaluationForTeam(long teamId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.TEAMMATE);
        bundle.putLong(ARouterConfig.Key.TEAM_ID, teamId);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_POST_EVALUATION, bundle);
    }

    /**
     * 查看对手评论
     *
     * @param teamId
     */
    public void startActivityToPostEvaluationForOpponent(long teamId, long agreeId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.OPPONENT);
        bundle.putLong(ARouterConfig.Key.TEAM_ID, teamId);
        bundle.putLong(ARouterConfig.Key.AGREE_ID, agreeId);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_POST_EVALUATION, bundle);
    }

    /**
     * 查看裁判
     *
     * @param refereeId
     */
    public void startActivityToPostEvaluationForReferee(long refereeId, long agreeId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.REFEREE);
        bundle.putLong(ARouterConfig.Key.REFEREE_ID, refereeId);
        bundle.putLong(ARouterConfig.Key.AGREE_ID, agreeId);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_POST_EVALUATION, bundle);
    }

    public void startActivityToRefereeDetailsForResult(ResultRefereeBean bean, int chooseRefereeCount, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARouterConfig.Key.PARCELABLE, bean);
        bundle.putInt(ARouterConfig.Key.COUNT, chooseRefereeCount);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_REFEREE_DETAILS, mSoftActivity.get(), bundle, requestCode);
    }

    /**
     * 跳转参加约球Activity
     */
    public void startActivityToInvitation(long agreeId, long calendarId, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.AGREE_ID, agreeId);
        bundle.putLong(ARouterConfig.Key.CALENDAR_ID, calendarId);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_INVITATION_BALL, mSoftActivity.get(), bundle, requestCode);

    }

    public PostEvaluationFragment getRefereeEvaluationFragment(long refereeId, int flag) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.REFEREE_ID, refereeId);
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, flag);
        return ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_POST_EVALUATION, bundle);
    }

    public boolean isReadNews(String key) {
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        return sp.isContainsKey(key);
    }

    public void putNewsKey(long newId) {
        String key = "News" + newId;
        SharedPreferencesHelp sp = new SharedPreferencesHelp();
        if (!sp.isContainsKey(key)) {
            sp.putObject(key, true);
        }
    }
}
