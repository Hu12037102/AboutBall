package com.work.guaishouxingqiu.aboutball.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 11:54
 * 更新时间: 2019/3/19 11:54
 * 描述: 文本相关的工具类
 */
public class SpanUtils {
    public static final int ALIGN_FONTCENTER = -2;
    /**
     * 设置textView中部分字体颜色
     *
     * @param colorRes
     * @param start
     * @param end
     * @param data
     */
    public static SpannableString getTextColor(@ColorRes int colorRes, int start, int end, @NonNull CharSequence data) {
        SpannableString ss = new SpannableString(data);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(UIUtils.getContext(), colorRes));
        ss.setSpan(foregroundColorSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getTextSize(int textSize, int start, int end, @NonNull String data) {
        SpannableString ss = new SpannableString(data);
        ss.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getTextSizeAndColor(int textSize, @ColorRes int textColor, int start, int end, @NonNull String data) {
        SpannableString ss = new SpannableString(data);
        ss.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(UIUtils.getContext(), textColor));
        ss.setSpan(foregroundColorSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }
    public static SpannableString getTopText(@ColorRes int textColor,int start,int end,@NonNull String data){
        SpannableString ss = new SpannableString(data);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(UIUtils.getContext(), textColor));
        ss.setSpan(foregroundColorSpan, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(R.drawable.shape_stick_view);
        ss.setSpan(backgroundColorSpan,start, end,SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }
    public static SpannableString getTextDrawable( @DrawableRes int drawableRes, int start, int end, @NonNull String content){

        SpannableString ss = new SpannableString(content);
       // TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(UIUtils.getContext(), android.R.style.TextAppearance_Material);
        CenterImageSpan imageSpan = new CenterImageSpan(UIUtils.getContext(),drawableRes, ALIGN_FONTCENTER);
       // ss.setSpan(textAppearanceSpan,start,content.length(),SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(imageSpan,start,end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 设置话题部分TextView可以点击
     *
     * @param textView
     * @param clickTextColor
     * @param start
     * @param end
     * @param onClickTextListener
     */
    public static void setClickText(@NonNull TextView textView, @ColorRes int clickTextColor, int start, int end, @NonNull OnClickTextListener onClickTextListener) {
        SpannableString ss = new SpannableString(textView.getText());
        ss.setSpan(new ClickText(onClickTextListener), start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), clickTextColor)), start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
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
    static class CenterImageSpan extends ImageSpan{

        public CenterImageSpan(Context context, int resourceId) {
            super(context, resourceId);
        }

        public CenterImageSpan(Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                         Paint paint) {

            //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
            // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
            // drawable的流对象，并最终获取drawable对象
            Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
            canvas.save();

            //获取画笔的文字绘制时的具体测量数据
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();

            //系统原有方法，默认是Bottom模式)
            int transY = bottom - drawable.getBounds().bottom;
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= fm.descent;
            } else if (mVerticalAlignment == ALIGN_FONTCENTER) {    //此处加入判断， 如果是自定义的居中对齐
                //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
                // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
                transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
            }

            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }
        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }

    }
}
