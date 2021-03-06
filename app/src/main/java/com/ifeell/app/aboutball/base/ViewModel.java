package com.ifeell.app.aboutball.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.base.bean.ResultSureOrderDialogBean;
import com.ifeell.app.aboutball.commonality.activity.WebUrlActivity;
import com.ifeell.app.aboutball.commonality.bean.ShareWebBean;
import com.ifeell.app.aboutball.community.activity.CommunityDetailsActivity;
import com.ifeell.app.aboutball.community.activity.MyDynamicActivity;
import com.ifeell.app.aboutball.community.activity.TopicDynamicsActivity;
import com.ifeell.app.aboutball.community.adapter.CommunityDataAdapter;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.bean.ResultRecommendHotBean;
import com.ifeell.app.aboutball.good.activity.GoodDetailsActivity;
import com.ifeell.app.aboutball.good.activity.GoodRefundActivity;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.http.IApi;
import com.ifeell.app.aboutball.login.activity.LoginActivity;
import com.ifeell.app.aboutball.media.bean.MediaSelectorFile;
import com.ifeell.app.aboutball.my.activity.AttentionAndFansActivity;
import com.ifeell.app.aboutball.my.activity.OrderCompleteEvaluateCancelActivity;
import com.ifeell.app.aboutball.my.activity.OrderEvaluateActivity;
import com.ifeell.app.aboutball.my.activity.RefundActivity;
import com.ifeell.app.aboutball.my.activity.UpdatePhoneActivity;
import com.ifeell.app.aboutball.my.bean.ResultAttentionFanBean;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.bean.ResultWeiChatSingBean;
import com.ifeell.app.aboutball.my.fragment.PostEvaluationFragment;
import com.ifeell.app.aboutball.other.DownloadApkHelp;
import com.ifeell.app.aboutball.other.SellingPointsEvent;
import com.ifeell.app.aboutball.other.SharedPreferencesHelp;
import com.ifeell.app.aboutball.other.UserManger;
import com.ifeell.app.aboutball.router.ARouterConfig;
import com.ifeell.app.aboutball.router.ARouterIntent;
import com.ifeell.app.aboutball.util.DataUtils;
import com.ifeell.app.aboutball.util.FileUtils;
import com.ifeell.app.aboutball.util.UIUtils;
import com.ifeell.app.aboutball.venue.activity.WaitPayOrderDetailsActivity;
import com.ifeell.app.aboutball.venue.bean.ResultRefereeBean;
import com.ifeell.app.aboutball.weight.BaseDialog;
import com.ifeell.app.aboutball.weight.HintDialog;
import com.ifeell.app.aboutball.weight.LoadingView;
import com.ifeell.app.aboutball.weight.PayDialog;
import com.ifeell.app.aboutball.weight.SureOrderDialog;
import com.ifeell.app.aboutball.weight.Toasts;
import com.ifeell.app.aboutball.weight.UpdateApkDialog;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private LoadingView mLoadingView;
    private final SoftReference<Activity> mSoftActivity;
    //  private HintDialog mUpdateDialog;
    private HintDialog mUserNoExitDialog;
    private HintDialog mLoginDialog;
    private HintDialog mNotLoginDialog;
    private HintDialog mCancelOrderDialog;
    private UpdateApkDialog mUpdateDialog;
    private HintDialog mCreateDialog;
    private PayDialog mPayDialog;
    private SureOrderDialog mSureOrderDialog;
    private HintDialog mInputKeyDialog;

    static ViewModel createViewModel(Activity activity) {
        return new ViewModel(activity);
    }

    private ViewModel(Activity activity) {
        mSoftActivity = new SoftReference<>(activity);

    }

    public void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(mSoftActivity.get());
        }
        mLoadingView.showLoadingView();
    }

    public void dismissLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.dismissLoadingView();
        }
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
            case IApi.Code.INPUT_KEY_ERROR:
                showInputKeyDialog(baseBean.message);
                break;
            default:
                break;
        }
    }

    private void showInputKeyDialog(String message) {
        if (mInputKeyDialog == null) {
            mInputKeyDialog = new HintDialog.Builder(mSoftActivity.get())
                    .setTitle(R.string.hint)
                    .setBody(message)
                    .setSure(R.string.sure)
                    .builder();
        }
        if (!mInputKeyDialog.isShowing() && !mSoftActivity.get().isFinishing()) {
            mInputKeyDialog.show();
        }
        mInputKeyDialog.setOnItemClickListener(new HintDialog.OnItemClickListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                mInputKeyDialog.dismiss();
            }
        });
    }

    public void showUpdateDialog(Context context, ResultUpdateApkBean updateBean) {
        // String content = UIUtils.getString(R.string.update_content);
        if (mUpdateDialog == null) {
            mUpdateDialog = new UpdateApkDialog(context, updateBean);
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
        if (!DataUtils.isEmpty(UserManger.get().getToken())) {
            UserManger.get().loginOut();
            FileUtils.removeFileCache();
        }
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

    public void showNotCancelLoginDialog() {
        showLoginDialog();
        if (mNotLoginDialog!=null){
            mNotLoginDialog.setCancelable(false);
            mNotLoginDialog.setCanceledOnTouchOutside(false);
        }

    }

    public void showLoginDialog(Fragment fragment) {
        if (!DataUtils.isEmpty(UserManger.get().getToken())) {
            UserManger.get().loginOut();
            FileUtils.removeFileCache();
        }
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
            ARouterIntent.startActivityForResult(fragment, LoginActivity.class, LoginActivity.REQUEST_CODE_LOGIN);
            mNotLoginDialog.dismiss();
        });
    }

    public void checkOutLogin(@Nullable Fragment fragment) {
        if (!UserManger.get().isLogin()) {
            if (fragment == null) {
                showLoginDialog();
            } else {
                showLoginDialog(fragment);
            }
        }
    }

    public boolean isCheckOutLogin(@Nullable Fragment fragment) {
        boolean isLogin = UserManger.get().isLogin();
        if (!isLogin) {
            if (fragment == null) {
                showLoginDialog();
            } else {
                showLoginDialog(fragment);
            }
        }
        return isLogin;
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

    public ShareWebBean getShareBean(@NonNull String title, @NonNull String content, @NonNull String url) {
        ShareWebBean webBean = new ShareWebBean();
        webBean.title = title;
        webBean.description = content;
        webBean.webUrl = url;
        return webBean;
    }

    public ShareWebBean getCommunityShare(ResultCommunityDataBean bean) {
        String shareUrl = IApiService.H5.DYNAMIC_DETAILS + "?" + ARouterConfig.Key.SHARE_ID + "=" + bean.tweetId + "&" + ARouterConfig.Key.SHARE_TYPE + "=" + IApiService.TypeId.DYNAMIC_DETAILS;
        return getShareBean(UIUtils.getString(R.string.community_title), bean.tweetContent, shareUrl);
    }

    public void showSureOrderDialog(ResultSureOrderDialogBean bean, SureOrderDialog.OnSureOrderClickListener listener) {
        if (bean == null) {
            UIUtils.showToast(R.string.not_more_this_tickets);
            return;
        }
        if (mSureOrderDialog == null) {
            mSureOrderDialog = new SureOrderDialog(mSoftActivity.get(), bean);
        } else {
            mSureOrderDialog.notifyData(bean);
        }

        if (!mSoftActivity.get().isFinishing() && !mSureOrderDialog.isShowing()) {
            mSureOrderDialog.show();
        }
        mSureOrderDialog.setOnSureOrderClickListener(listener);
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

    public void clickBackForResult(Intent intent) {
        Activity activity = mSoftActivity.get();
        activity.setResult(Activity.RESULT_OK, intent);
        if (!activity.isFinishing()) {
            mSoftActivity.get().finish();
        }
    }

    public void clickBackForResult(Parcelable parcelable) {
        Activity activity = mSoftActivity.get();
        Intent intent = new Intent();
        intent.putExtra(ARouterConfig.Key.PARCELABLE, parcelable);
        activity.setResult(Activity.RESULT_OK, intent);
        if (!activity.isFinishing()) {
            mSoftActivity.get().finish();
        }
    }

    public void setNoDataView(@NonNull SmartRefreshLayout refreshLayout, boolean hasData) {
        //refreshLayout.resetNoMoreData();
        refreshLayout.setNoMoreData(hasData);
    }

    public void showPayDialog(@NonNull String money, @NonNull PayDialog.OnPayDialogClickListener listener) {
        if (mPayDialog == null) {
            mPayDialog = new PayDialog(mSoftActivity.get());
        }
        mPayDialog.setMoney(money);
        if (!mPayDialog.isShowing() && !mSoftActivity.get().isFinishing()) {
            mPayDialog.show();
        }
        mPayDialog.setOnPayDialogClickListener(listener);

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
     * @param hostTeamId 自己队Id，对手Id,比赛Id,裁判Id
     */
    public void startActivityToPostEvaluation(long hostTeamId, long guestTeamId, long agreeId, long refereeId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.REFEREE_ID, refereeId);
        bundle.putLong(ARouterConfig.Key.TEAM_ID, hostTeamId);
        bundle.putLong(ARouterConfig.Key.OPPONENT_ID, guestTeamId);
        bundle.putLong(ARouterConfig.Key.AGREE_ID, agreeId);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_POST_EVALUATION, bundle);
    }

    public void startActivityToTeamMatchResult(ResultBallDetailsBean.MatchBean bean) {
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_TEAM_MATCH_RESULT, ARouterConfig.Key.PARCELABLE, bean);
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
     * @param
     */
   /* public void startActivityToPostEvaluationForReferee(long refereeId, long agreeId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.INPUT_EVALUATION_FLAG, Contast.InputEvaluationType.REFEREE);
        bundle.putLong(ARouterConfig.Key.REFEREE_ID, refereeId);
        bundle.putLong(ARouterConfig.Key.AGREE_ID, agreeId);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_POST_EVALUATION, bundle);
    }*/
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

    public void startActivityToMap(double longitude, double latitude, String positionName) {
        Bundle bundle = new Bundle();
        bundle.putDouble(ARouterConfig.Key.LONGITUDE, longitude);
        bundle.putDouble(ARouterConfig.Key.LATITUDE, latitude);
        bundle.putString(ARouterConfig.Key.POSITION_NAME, positionName);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_MAP, bundle);
    }

    /**
     * 绑定或者更新手机号
     *
     * @param signCode        可为空唯一标识第三方
     * @param bandPhoneStatus 绑定手机号的类型：第三方登录，换绑手机号
     */
    public void startActivityToUpdatePhoneForResult(String signCode, int bandPhoneStatus) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.BAND_PHONE_STATUS, bandPhoneStatus);
        bundle.putString(ARouterConfig.Key.SIGN_CODE, signCode);
        ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_UPDATE_PHONE, mSoftActivity.get(), bundle, UpdatePhoneActivity.REQUEST_CODE_BAND_PHONE);
    }

    /**
     * 跳转到社区详情
     *
     * @param bean
     */
    public void startActivityToCommunityRecommendDetailsForResult(ResultCommunityDataBean bean, Fragment fragment) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_COMMUNITY_DETAILS, mSoftActivity.get(), ARouterConfig.Key.PARCELABLE, bean);
        } else {
            ARouterIntent.startActivityForResult(fragment, CommunityDetailsActivity.class, ARouterConfig.Key.PARCELABLE, bean);
        }
    }

    public void startActivityToPreview(int position, ArrayList<String> pathData) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.POSITION, position);
        bundle.putStringArrayList(ARouterConfig.Key.ARRAY_LIST_STRING, pathData);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_IMAGE_PREVIEW, bundle);
    }

    public void updateDianZan(@NonNull CommunityDataAdapter adapter, @NonNull List<ResultCommunityDataBean> data, int position) {
        ResultCommunityDataBean bean = data.get(position);
        if (bean.hasPraise == 1) {
            bean.hasPraise = 0;
            bean.praiseCount = bean.praiseCount > 0 ? bean.praiseCount -= 1 : 0;
        } else if (bean.hasPraise == 0) {
            bean.hasPraise = 1;
            bean.praiseCount++;
        }
        adapter.notifyDataSetChanged();
    }

    public void updateAttention(@NonNull RecyclerView.Adapter adapter, @NonNull List<ResultCommunityDataBean> data, int position) {
        ResultCommunityDataBean bean = data.get(position);
        int hasFollow;
        if (bean.hasFollow == 1) {
            hasFollow = 0;
        } else {
            hasFollow = 1;
        }
        for (int i = 0; i < data.size(); i++) {
            if (bean.userId == data.get(i).userId) {
                data.get(i).hasFollow = hasFollow;
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void updateAttention(RecyclerView.Adapter adapter, List<ResultCommunityDataBean> data, ResultAttentionFanBean messageBean) {
        if (adapter == null || data == null || messageBean == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            ResultCommunityDataBean bean = data.get(i);
            if (messageBean.userId == bean.userId) {
                bean.hasFollow = messageBean.isFollow;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void resultCommunityData(RecyclerView.Adapter adapter, ResultCommunityDataBean resultBean, List<ResultCommunityDataBean> data) {
        if (adapter == null || data == null || data.size() == 0) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            ResultCommunityDataBean bean = data.get(i);
            if (resultBean.tweetId == bean.tweetId) {
                int index = data.indexOf(bean);
                data.remove(index);
                if (!resultBean.isDelete) {
                    data.add(index, resultBean);
                }
            }
            if (resultBean.userId == bean.userId) {
                bean.hasFollow = resultBean.hasFollow;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void resultCommunityData(RecyclerView.Adapter adapter, ResultAttentionFanBean messageBean, List<ResultCommunityDataBean> data, boolean isAttentionPage) {
        if (adapter == null || messageBean == null || data == null) {
            return;
        }

        if (isAttentionPage) {
            Iterator<ResultCommunityDataBean> iterator = data.iterator();
            while (iterator.hasNext()) {
                ResultCommunityDataBean bean = iterator.next();
                if (bean.userId == messageBean.userId) {
                    iterator.remove();
                }
            }
        } else {
            for (int i = 0; i < data.size(); i++) {
                ResultCommunityDataBean bean = data.get(i);
                if (messageBean.userId == bean.userId) {
                    bean.hasFollow = messageBean.isFollow;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * @param adapter
     * @param resultBean
     * @param data
     * @param isAttentionPage 是不是关注pager
     */
    public void resultCommunityData(RecyclerView.Adapter adapter, ResultCommunityDataBean resultBean, List<ResultCommunityDataBean> data, boolean isAttentionPage) {
        if (adapter == null || data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            ResultCommunityDataBean bean = data.get(i);
            if (resultBean.tweetId == bean.tweetId) {
                int index = data.indexOf(bean);
                data.remove(index);
                if (!resultBean.isDelete) {
                    data.add(index, resultBean);
                }
            }
            if (resultBean.userId == bean.userId) {
                bean.hasFollow = resultBean.hasFollow;
            }
        }
        Iterator<ResultCommunityDataBean> iterator = data.iterator();
        while (iterator.hasNext()) {
            ResultCommunityDataBean bean = iterator.next();
            if (bean.userId == resultBean.userId && isAttentionPage) {
                iterator.remove();
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void showDeleteCommunityDialog(BaseDialog.OnItemClickSureAndCancelListener listener) {
        HintDialog deleteDialog = new HintDialog.Builder(mSoftActivity.get())
                .setShowSingButton(false)
                .setTitle(R.string.hint)
                .setBody(R.string.you_sure_delete_community)
                .builder();
        deleteDialog.setOnItemClickSureAndCancelListener(listener);
        deleteDialog.show();
    }

    public <T extends Fragment> T getTopicFragment(int flag, long topicId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARouterConfig.Key.TOPIC_STATUS, flag);
        bundle.putLong(ARouterConfig.Key.TOPIC_ID, topicId);
        return ARouterIntent.getFragment(ARouterConfig.Path.FRAGMENT_TOPIC_DYNAMICS, bundle);
    }

    public void startActivityToTopicForResult(ResultRecommendHotBean bean, int requestCode, Fragment fragment) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_TOPIC_DYNAMICS, mSoftActivity.get(), ARouterConfig.Key.PARCELABLE, bean, requestCode);
        } else {
            ARouterIntent.startActivityForResult(fragment, TopicDynamicsActivity.class, ARouterConfig.Key.PARCELABLE, bean, requestCode);
        }
    }

    public ShareWebBean getAboutBallDetailShareBean(long agreeId, String date, String address) {
        String shareUrl = IApiService.H5.ABOUT_BALL_DETAILS + "?" + ARouterConfig.Key.SHARE_ID + "=" + agreeId + "&" + ARouterConfig.Key.SHARE_TYPE + "=" + IApiService.TypeId.ABOUT_DETAILS;
        String content = date + " " + address;
        return getShareBean(UIUtils.getString(R.string.share_about_about_details_title), content, shareUrl);
    }

    public void showCreateTeamDialog() {
        if (mCreateDialog == null) {
            mCreateDialog = new HintDialog.Builder(mSoftActivity.get())
                    .setTitle(R.string.hint)
                    .setBody(R.string.you_are_not_captain_to_create_team)
                    .setShowSingButton(false)
                    .builder();
        }
        if (!mCreateDialog.isShowing()) {
            mCreateDialog.show();
        }
        mCreateDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_MANAGE_BALL_TEAM, mSoftActivity.get());
                mCreateDialog.dismiss();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mCreateDialog.dismiss();
            }
        });
    }

    public void startActivityToOrderEvaluate(long orderId, int status, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_STATUS, status);
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ORDER_COMPLETE_EVALUATE_CANCEL, mSoftActivity.get(), bundle, status);
        } else {
            ARouterIntent.startActivityForResult(fragment, OrderCompleteEvaluateCancelActivity.class, bundle, status);
        }
    }

    public boolean isMediaVideo(@NonNull List<MediaSelectorFile> data) {
        return data.size() == 1 && FileUtils.existsFile(data.get(0).filePath)
                && DataUtils.isVideo(data.get(0).filePath);
    }

    public <T> void setRefreshViewMoreStatus(@NonNull SmartRefreshLayout refreshLayout, @NonNull List<T> data, int countSize) {
        refreshLayout.setEnableLoadMore(!DataUtils.isEmptyList(data) && data.size() == countSize);
    }

    public void startActivityToAttentionAndFans(@Nullable Fragment fragment, int status, int requestCode) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ATTENTION_AND_FANS, mSoftActivity.get(), requestCode, ARouterConfig.Key.ATTENTION_AND_FANS, status);
        } else {
            ARouterIntent.startActivityForResult(fragment, AttentionAndFansActivity.class, requestCode, ARouterConfig.Key.ATTENTION_AND_FANS, status);
        }
    }

    public void startActivityToUserDynamicForResult(@Nullable Fragment fragment, long userId, int requestCode) {
        DataUtils.addSellingPoint(mSoftActivity.get(), SellingPointsEvent.Key.A0404);
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_MY_DYNAMIC, mSoftActivity.get(), ARouterConfig.Key.USER_ID, userId, requestCode);
        } else {
            ARouterIntent.startActivityForResult(fragment, MyDynamicActivity.class, ARouterConfig.Key.USER_ID, userId, requestCode);
        }
    }

    public boolean isShowRedPoint() {
        return getRedPointCount() > 0;

    }

    private int getRedPointCount() {
        UserManger userManger = UserManger.get();
        if (userManger.isLogin()) {
            String redPointJson = userManger.getRedPointJson();
            if (!DataUtils.isEmpty(redPointJson)) {
                List<ResultRedPointInfoBean> redPointData = DataUtils.jsonToListRedPoint(redPointJson);
                if (redPointData != null && redPointData.size() > 0) {
                    int sum = 0;
                    for (int i = 0; i < redPointData.size(); i++) {
                        sum += redPointData.get(i).unReadNum;
                    }
                    return sum;
                }
            }
        }
        return 0;
    }

    public void startWebUrlActivityForResult(@Nullable Fragment fragment, String url) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_WEB_URL, mSoftActivity.get(), WebUrlActivity.REQUEST_CODE, ARouterConfig.Key.WEB_URL, url);
        } else {
            ARouterIntent.startActivityForResult(fragment, WebUrlActivity.class, ARouterConfig.Key.WEB_URL, url, WebUrlActivity.REQUEST_CODE);
        }
    }

    public void startGoodDetailsActivityForResult(@Nullable Fragment fragment, int requestCode, long orderId) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_ORDER_DETAILS, mSoftActivity.get(), ARouterConfig.Key.ORDER_ID, orderId, requestCode);
        } else {
            ARouterIntent.startActivityForResult(fragment, GoodDetailsActivity.class, ARouterConfig.Key.ORDER_ID, orderId, requestCode);
        }

    }

    public void startGoodRefundDetailActivityForResult(@Nullable Fragment fragment, int requestCode, long orderId) {
        if (fragment == null) {
            ARouterIntent.startActivityForResult(ARouterConfig.Path.ACTIVITY_GOOD_REFUND, mSoftActivity.get(), ARouterConfig.Key.ORDER_ID, orderId, requestCode);
        } else {
            ARouterIntent.startActivityForResult(fragment, GoodRefundActivity.class, ARouterConfig.Key.ORDER_ID, orderId, requestCode);
        }
    }

    public void showGoodStatusErrorDialog(int status, final HintDialog.OnItemClickListener onItemClickListener) {
        String body = UIUtils.getString(R.string.order_error);
        switch (status) {
            //待支付
            case Contast.MyGoodStatus.WAIT_PAY:

                break;
            //已付款
            case Contast.MyGoodStatus.PAYING:
                body = UIUtils.getString(R.string.order_is_pay);

                break;
            //已完成
            case Contast.MyGoodStatus.COMPLETE:
                break;
            //已取消
            case Contast.MyGoodStatus.CANCEL:
                break;
            //退款中
            case Contast.MyGoodStatus.REFUNDING:
                body = UIUtils.getString(R.string.order_is_refund);
                break;
            //已退款
            case Contast.MyGoodStatus.REFUNDED:
                break;
            default:
                break;
        }
        HintDialog hintDialog = new HintDialog.Builder(mSoftActivity.get())
                .setTitle(R.string.hint)
                .setBody(body)
                .setSure(R.string.sure)
                .builder();
        hintDialog.show();
        hintDialog.setOnItemClickListener(new HintDialog.OnItemClickListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onClickSure(view);
                }
                hintDialog.dismiss();
            }
        });
    }
}
