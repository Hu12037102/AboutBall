package com.work.guaishouxingqiu.aboutball.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.my.activity.OrderEvaluateActivity;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.other.DownloadApkHelp;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.BaseDialog;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

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
    private HintDialog mUpdateDialog;
    private HintDialog mUserNoExitDialog;
    private HintDialog mLoginDialog;
    private HintDialog mNotLoginDialog;
    private HintDialog mCancelOrderDialog;

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
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER);
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
        if (mUpdateDialog == null) {
            mUpdateDialog = new HintDialog.Builder(context)
                    .setTitle(R.string.update_apk)
                    .setBody(updateBean.content)
                    .setShowSingButton(false)
                    .builder();
        }
        if (!mUpdateDialog.isShowing()) {
            mUpdateDialog.show();
        }
        mUpdateDialog.setOnItemClickSureAndCancelListener(new BaseDialog.OnItemClickSureAndCancelListener() {
            @Override
            public void onClickSure(@NonNull View view) {
                DownloadApkHelp.loadApk(UIUtils.getContext(), updateBean.updateUrl);
                mUpdateDialog.dismiss();
            }

            @Override
            public void onClickCancel(@NonNull View view) {
                mUpdateDialog.dismiss();
            }
        });

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
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);
            mNotLoginDialog.dismiss();
        });
    }

    public void startActivityToOrderPay(long orderId, int flag) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterConfig.Key.ORDER_ID, orderId);
        bundle.putInt(ARouterConfig.Key.ORDER_FLAG, flag);
        ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WAIT_PAY_ORDER_DETAILS, bundle);
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

    public void setNoDataView(@NonNull SmartRefreshLayout refreshLayout, boolean hasData ){
        //refreshLayout.resetNoMoreData();
        refreshLayout.setNoMoreData(hasData);
    }


}
