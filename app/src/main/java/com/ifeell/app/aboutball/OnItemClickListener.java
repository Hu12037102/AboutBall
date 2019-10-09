package com.ifeell.app.aboutball;

import androidx.annotation.NonNull;
import android.view.View;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/27 18:07
 * 更新时间: 2019/3/27 18:07
 * 描述:
 */
public interface OnItemClickListener {
    void onClickItem(@NonNull View view, int position);
}
