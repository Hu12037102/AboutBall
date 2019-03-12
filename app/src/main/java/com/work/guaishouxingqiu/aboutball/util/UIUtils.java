package com.work.guaishouxingqiu.aboutball.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.router.ARouterConfig;
import com.work.guaishouxingqiu.aboutball.weight.HintDialog;
import com.work.guaishouxingqiu.aboutball.weight.Toasts;

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
     * @param view
     * @param editText
     */
    public static void clickClearEditData(@NonNull View view,@NonNull EditText editText){
        view.setOnClickListener(v -> {
            editText.setText(null);
            view.setVisibility(View.GONE);
        });
    }
    public static void resultBaseData(@NonNull BaseBean baseBean,@NonNull Activity activity){
        switch (baseBean.code) {
            case IApi.Code.MESSAGES_CODE_ERROR:
                Toasts.with().showToast(baseBean.title);
                break;
            case IApi.Code.USER_NO_EXIST:
                HintDialog hintDialog = new HintDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setBody(R.string.this_phone_not_register)
                        .setSure(R.string.go_register)
                        .builder();
                hintDialog.show();
                hintDialog.setOnItemClickListener(view -> {
                    UIUtils.startActivity(ARouterConfig.Path.ACTIVITY_REGISTER);
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
            default:
                break;
        }
    }

    public static void startActivity(@NonNull String path) {
        ARouter.getInstance().build(path).navigation();
    }
}
