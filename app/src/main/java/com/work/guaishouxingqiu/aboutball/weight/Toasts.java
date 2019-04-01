package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

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
    private  Toast mToast;

    private Toasts() {

    }
    public static Toasts with(){
        synchronized (Toasts.class) {
            if (mToasts == null) {
                synchronized (Toasts.class) {
                    mToasts = new Toasts();
                }
            }
        }
        return mToasts;
    }

    public void showToast(@NonNull String text) {
        if (DataUtils.isEmpty(text)){
            return;
        }
       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
        }
        mToast.show();*/
        Toast.makeText(UIUtils.getContext(),text,Toast.LENGTH_SHORT).show();
    }
    public void showToast(int textRes) {
       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), textRes, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(textRes);
        }
        mToast.show();*/
        Toast.makeText(UIUtils.getContext(),textRes,Toast.LENGTH_SHORT).show();
    }

    public synchronized void showToast( @StringRes int text, Object... object) {

       /* if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), UIUtils.getContext().getString(text, object), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(UIUtils.getContext().getString(text, object));
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        Log.w("showToast--", "showToast");
        mToast.show();*/
        Toast.makeText(UIUtils.getContext(),UIUtils.getContext().getString(text, object),Toast.LENGTH_SHORT).show();
    }
}
