package com.work.guaishouxingqiu.aboutball;

import android.view.View;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/17 10:01
 * 更新时间: 2019/6/17 10:01
 * 描述:延点击
 */
public abstract class DelayedClickListener implements View.OnClickListener {
    private static final int WAIT_TIME = 3000;
    private long lastTime;
    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastTime > WAIT_TIME) {
            onDelayedClick(v);
            lastTime = clickTime;
        }
    }
    public abstract void onDelayedClick(View view);
}
