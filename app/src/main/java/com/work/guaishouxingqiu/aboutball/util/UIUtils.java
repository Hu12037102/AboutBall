package com.work.guaishouxingqiu.aboutball.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

    public static void clickClearEditData(@NonNull View view,@NonNull EditText editText){
        view.setOnClickListener(v -> {
            editText.setText(null);
            view.setVisibility(View.GONE);
        });
    }
}
