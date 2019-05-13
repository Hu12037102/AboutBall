package com.work.guaishouxingqiu.aboutball.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.http.IApi;
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


    public void onDestroy() {
        Activity activity = mSoftActivity.get();
        if (activity != null) {
            mSoftActivity.clear();
        }
    }

}
