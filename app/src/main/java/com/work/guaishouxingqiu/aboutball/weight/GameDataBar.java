package com.work.guaishouxingqiu.aboutball.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.work.guaishouxingqiu.aboutball.R;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/25 15:36
 * 更新时间: 2019/3/25 15:36
 * 描述: 比赛数据状态条
 */
public class GameDataBar extends View {
    private @ColorInt
    int mBarColors = ContextCompat.getColor(getContext(), R.color.colorFF3586FF);
    private Paint mBarPaint;
    private RectF mBarRectF;
    private boolean isDrawLeftToRight = true;//默认是从左边开始画的
    private float mDrawProportion = 1.0f;//画的默认比例

    public void setBarColor(@ColorInt int barColorRes) {
        mBarColors = barColorRes;
        invalidate();
    }


    public void setProportion(float proportion) {
        mDrawProportion = proportion;
        invalidate();
    }

    public GameDataBar(Context context) {
        this(context, null);
    }

    public GameDataBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorFFF5F5F5));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GameDataBar);
        if (typedArray != null) {
            isDrawLeftToRight = typedArray.getBoolean(R.styleable.GameDataBar_gdb_draw_left, true);
            mDrawProportion = typedArray.getFloat(R.styleable.GameDataBar_gab_draw_proportion, 1.0f);
            mBarColors = typedArray.getColor(R.styleable.GameDataBar_gab_color, ContextCompat.getColor(getContext(), R.color.colorFF3586FF));
            typedArray.recycle();
        }
        initPaint();
    }


    private void initPaint() {
        mBarPaint = new Paint();
        mBarPaint.setDither(true);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBarPaint.setStrokeWidth(getHeight());
        mBarPaint.setColor(mBarColors);
        if (isDrawLeftToRight) {
            mBarRectF = new RectF(0, 0, getWidth() * mDrawProportion, getHeight());
        } else {
            mBarRectF = new RectF(getWidth() - getWidth() * mDrawProportion, 0, getWidth(), getHeight());
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mBarRectF, mBarPaint);
    }
}
