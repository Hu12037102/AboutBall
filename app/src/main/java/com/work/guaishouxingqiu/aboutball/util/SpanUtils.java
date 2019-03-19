package com.work.guaishouxingqiu.aboutball.util;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 11:54
 * 更新时间: 2019/3/19 11:54
 * 描述: 文本相关的工具类
 */
public class SpanUtils {
    /**
     * 设置textView中部分字体颜色
     *
     * @param colorRes
     * @param start
     * @param end
     * @param textView
     */
    public static void setTextColor(@ColorRes int colorRes, int start, int end, @NonNull TextView textView) {
        SpannableString ss = new SpannableString(textView.getText());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), colorRes));
        ss.setSpan(foregroundColorSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
    }

    /**
     * 设置话题部分TextView可以点击
     * @param textView
     * @param clickTextColor
     * @param start
     * @param end
     * @param onClickTextListener
     */
    public static void setClickText(@NonNull TextView textView, @ColorRes int clickTextColor, int start, int end, @NonNull OnClickTextListener onClickTextListener) {
        SpannableString ss = new SpannableString(textView.getText());
        ss.setSpan(new ClickText(onClickTextListener), start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), clickTextColor)),start,end,SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        //不设置 没有点击事件
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //设置点击背景是透明色
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    public static class ClickText extends ClickableSpan {
        private OnClickTextListener onClickTextListener;

        public ClickText(SpanUtils.OnClickTextListener onClickTextListener) {
            this.onClickTextListener = onClickTextListener;
        }

        @Override
        public void onClick(View widget) {
            if (onClickTextListener != null) {
                onClickTextListener.onClick(widget);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置不要下划线
            ds.setUnderlineText(false);
        }
    }

    public interface OnClickTextListener {
        void onClick(View view);
    }
}
