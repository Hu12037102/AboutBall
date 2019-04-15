package com.work.guaishouxingqiu.aboutball.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.router.ARouterIntent;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;
import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:36
 * 更新时间: 2019/3/4 13:36
 * 描述: 界面相关的工具类
 */
public class UIUtils {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void init(@NonNull Context context) {
        UIUtils.mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 判断清除按钮是否要不要显示
     *
     * @param editText
     * @param imageView
     */
    public static void checkClearImageStatus(@NonNull EditText editText, @NonNull ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageView.setVisibility(DataUtils.isEmpty(editText.getText().toString()) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 点击清除按钮
     *
     * @param view
     * @param editText
     */
    public static void clickClearEditData(@NonNull View view, @NonNull EditText editText) {
        view.setOnClickListener(v -> {
            editText.setText(null);
            view.setVisibility(View.GONE);
        });
    }

    public static void resultBaseData(@NonNull BaseBean baseBean, @NonNull Activity activity) {
        switch (baseBean.code) {
            case IApi.Code.USER_NO_EXIST:
                HintDialog hintDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_not_register)
                        .setSure(R.string.go_register)
                        .builder();
                hintDialog.show();
                hintDialog.setOnItemClickListener(view -> {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER);
                    hintDialog.dismiss();
                });
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.SERVICE_ERROR:
                Toasts.with().showToast(baseBean.message);
                break;
            case IApi.Code.USER_EXIST:
                final HintDialog loginDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_is_register)
                        .setSure(R.string.login_immediately)
                        .builder();
                loginDialog.show();
                loginDialog.setOnItemClickListener(view -> {
                    activity.finish();
                    loginDialog.dismiss();
                });
                break;
            case IApi.Code.USER_NOT_LOGIN:
                final HintDialog notLoginDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.is_go_to_login)
                        .setSure(R.string.login_immediately)
                        .builder();
                notLoginDialog.show();
                notLoginDialog.setOnItemClickListener(view -> {
                    ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);
                    notLoginDialog.dismiss();
                });
                break;
            default:
                break;
        }
    }

    public static void setGameIconStatus(int stateId, TextView textView) {
        switch (stateId) {
            case Contast.GAME_STATUS_STARTING:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_live_status, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(),R.color.colorFFDB2F23));
                break;
            case Contast.GAME_STATUS_FINISH:
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_recommend_no_start, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(),R.color.colorFFA6A6A6));
                break;
            default:
                break;
        }
    }

    public static void uCropImage(Activity activity, File firstFile, File lastFile, int scaleX, int scaleY,
                                  int cropWidth, int cropHeight) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setLogoColor(ContextCompat.getColor(activity, R.color.color_4));
        options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.color_2));
        UCrop.of(Uri.fromFile(firstFile), Uri.fromFile(lastFile))
                .withAspectRatio(scaleX, scaleY)
                .withMaxResultSize(cropWidth, cropHeight)
                .withOptions(options)
                .start(activity);

    }

    public static String getString(@StringRes int res, Object... obj) {
        return UIUtils.getContext().getString(res, obj);
    }

    public static View loadNotMoreView(ViewGroup viewGroup) {
        return LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_not_more, viewGroup, false);
    }

    public static void parseScanCode(String result) {
        String[] splits = result.split(",");
        if (splits.length >= 2) {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterConfig.Key.ACTION_ID, splits[0]);
            bundle.putString(ARouterConfig.Key.URL, splits[1]);
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_WEB_DATA, bundle);
        }
    }
    public static void showLoginDialog(Context context){
        HintDialog notLoginDialog = new HintDialog.Builder(context)
                .setTitle(R.string.hint)
                .setBody(R.string.is_go_to_login)
                .setSure(R.string.login_immediately)
                .builder();
        notLoginDialog.show();
        notLoginDialog.setOnItemClickListener(view -> {
            ARouterIntent.startActivity(ARouterConfig.Path.ACTIVITY_LOGIN);
            notLoginDialog.dismiss();
        });
    }
}
