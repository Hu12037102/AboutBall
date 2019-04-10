package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

/**
 * @ClassName: Toasts
 * @Author: 胡庆岭
 * @CreateTime: 2019/3/4 13:54
 * @UpdateTime: 2019/3/4 13:54
 * @Description: Toast工具类
 */
public class Toasts {
    private static Toasts mToasts;


    private Toasts() {
    }

    public static Toasts with() {
        synchronized (Toasts.class) {
            if (mToasts == null) {
                synchronized (Toasts.class) {
                    mToasts = new Toasts();

                }
            }
        }
        return mToasts;
    }

    private void initToast(Object o, @NonNull Object... obj) {
        // if (mToastView == null) {
        View toastView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_toast_view, null);
        TextView tvToast = toastView.findViewById(R.id.tv_toast);
        Toast toast = new Toast(UIUtils.getContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
      //  toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, ScreenUtils.dp2px(UIUtils.getContext(), 0));
      /*  } else {
            if (o instanceof String) {
                String text = (String) o;
                mTvToast.setText(text);
            } else if (o instanceof Integer) {
                int resText = (Integer) o;
                if (obj == null || obj.length == 0) {
                    mTvToast.setText(resText);
                } else {
                    mTvToast.setText(UIUtils.getContext().getString(resText, obj));
                }
            }

        }*/
        if (o instanceof String) {
            String text = (String) o;
            tvToast.setText(text);
        } else if (o instanceof Integer) {
            int resText = (Integer) o;
            if (obj == null || obj.length == 0) {
                tvToast.setText(resText);
            } else {
                tvToast.setText(UIUtils.getContext().getString(resText, obj));
            }
        }
        toast.show();
    }


    public void showToast(@NonNull String text) {
        if (DataUtils.isEmpty(text)) {
            return;
        }

       // initToast(text);

       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
        }
        mToast.show();*/
           Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int textRes) {
          //  initToast(textRes);

       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), textRes, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(textRes);
        }
        mToast.show();*/
        Toast.makeText(UIUtils.getContext(), textRes, Toast.LENGTH_SHORT).show();
    }

    public void showToast(@StringRes int textRes, Object... object) {
       // initToast(textRes, object);

       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), UIUtils.getContext().getString(text, object), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(UIUtils.getContext().getString(text, object));
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        Log.w("showToast--", "showToast");
        mToast.show();*/
         Toast.makeText(UIUtils.getContext(), UIUtils.getContext().getString(textRes, object), Toast.LENGTH_SHORT).show();
    }
}
