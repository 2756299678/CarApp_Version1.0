package com.example.baohui.carapp_version10.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


import com.example.baohui.carapp_version10.R;
/**
 * Created by Zhanghe on 2018/4/26.
 */

public class ColorArcProgressBar extends View {
    private int diameter = 500;  //直径
    private float centerX;  //圆心X坐标
    private float centerY;  //圆心Y坐标

    private Paint allArcPaint;
    private Paint progressPaint;
    private Paint vTextPaint;
    private Paint hintPaint;
    private Paint degreePaint;
    private Paint curSpeedPaint;

    private RectF bgRect;

    private ValueAnimator progressAnimator;
    private PaintFlagsDrawFilter mDrawFilter;
    private SweepGradient sweepGradient;
    private Matrix rotateMatrix;

    private float startAngle = 155;
    private float sweepAngle = 230;
    private float currentAngle = 0.01f;
    private float lastAngle;
    private int[] colors = new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.RED};
    private float maxValues = 120;
    private float curValues = 0;
    private float bgArcWidth = dipToPx(10);
    private float progressWidth = dipToPx(10);
    private float textSize = dipToPx(50);
    private float hintSize = dipToPx(9);
    private float curSpeedSize = dipToPx(9);
    private int aniSpeed = 1500;
    private float longDegree = dipToPx(13);
    private float shortDegree = dipToPx(5);
    private final int DEGREE_PROGRESS_DISTANCE = dipToPx(1);

    private String hintColor = "#8d9298";
    private String bgArcColor = "#F0F0F0";
    private String titleString;
    private String hintString;

    private boolean isNeedTitle;
    private boolean isNeedUnit;
    private boolean isNeedDial;
    private boolean isNeedContent;
    private boolean isNeedProgress = false;

    // sweepAngle / maxValues 的值
    private float k;

    public ColorArcProgressBar(Context context) {
        super(context, null);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initCofig(context, attrs);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCofig(context, attrs);
        initView();
    }

    /**
     * 初始化布局配置
     */
    private void initCofig(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar);
        int color1 = a.getColor(R.styleable.ColorArcProgressBar_front_color1, Color.GREEN);
        int color2 = a.getColor(R.styleable.ColorArcProgressBar_front_color2, color1);
        int color3 = a.getColor(R.styleable.ColorArcProgressBar_front_color3, color1);
        int color4 = a.getColor(R.styleable.ColorArcProgressBar_front_color4, color1);
        int color5 = a.getColor(R.styleable.ColorArcProgressBar_front_color5, color1);
        colors = new int[]{color1, color2, color3, color4, color5, color5};

        sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_total_engle, 230);
        bgArcWidth = a.getDimension(R.styleable.ColorArcProgressBar_back_width, dipToPx(10));
        progressWidth = a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10));
        isNeedTitle = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_title, false);
        isNeedContent = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_content, false);
        isNeedUnit = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_unit, false);
        isNeedDial = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_dial, false);
        hintString = a.getString(R.styleable.ColorArcProgressBar_string_unit);
        titleString = a.getString(R.styleable.ColorArcProgressBar_string_title);
        curValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 0);
        maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 120);
        setCurrentValues(curValues);
        setMaxValues(maxValues);
        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE);
        int height = (int) (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE);
        setMeasuredDimension(width, height);
    }

    private void initView() {

        diameter = 3 * getScreenWidth() / 5;
        //弧形的矩阵区域
        bgRect = new RectF();
        bgRect.top = longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE;
        bgRect.left = longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE;
        bgRect.right = diameter + (longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE);
        bgRect.bottom = diameter + (longDegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE);

        //圆心
        centerX = (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2;
        centerY = (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE) / 2;

        //外部刻度线
        degreePaint = new Paint();
        degreePaint.setAntiAlias(true);
        degreePaint.setColor(Color.parseColor("#C8C8C8"));
        degreePaint.setStrokeWidth(dipToPx(1f));
        degreePaint.setTextSize(dipToPx(10f));
        //整个弧形
        allArcPaint = new Paint();
        allArcPaint.setAntiAlias(true);
        allArcPaint.setStyle(Paint.Style.STROKE);
        allArcPaint.setStrokeCap(Paint.Cap.ROUND);
        allArcPaint.setStrokeWidth(bgArcWidth);
        allArcPaint.setColor(Color.parseColor(bgArcColor));

        //当前进度的弧形
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(Color.GREEN);

        //内容显示文字
        vTextPaint = new Paint();
        vTextPaint.setTextSize(textSize);
        vTextPaint.setColor(Color.BLACK);
        vTextPaint.setTextAlign(Paint.Align.CENTER);

        //显示单位文字
        hintPaint = new Paint();
        hintPaint.setTextSize(hintSize);
        hintPaint.setColor(Color.parseColor(hintColor));
        hintPaint.setTextAlign(Paint.Align.CENTER);

        //显示标题文字
        curSpeedPaint = new Paint();
        curSpeedPaint.setTextSize(curSpeedSize);
        curSpeedPaint.setColor(Color.WHITE);
        curSpeedPaint.setTextAlign(Paint.Align.CENTER);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        sweepGradient = new SweepGradient(centerX, centerY, colors, null);
        rotateMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //抗锯齿
        canvas.setDrawFilter(mDrawFilter);

        if (isNeedDial) {
            //画刻度线 TODO 画刻度线可以优化
            for (int i = 0; i < 156; i++) {
                if (i < 28 || i > 128) {
                    canvas.rotate(2.30769f, centerX, centerY);
                    continue;
                }
                canvas.drawLine(centerX, centerY + diameter / 2 - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE - (longDegree - shortDegree) / 2,
                        centerX, centerY + diameter / 2 - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE - (longDegree - shortDegree) / 2 - shortDegree, degreePaint);
                if (((i - 28) % 20) == 0) {
                    if (i == 128) {
                        canvas.rotate(-2.30769f * 2, centerX, centerY);
                    } else {
                        canvas.rotate(-2.30769f, centerX, centerY);
                    }
                    canvas.rotate(180f, centerX, centerY + diameter / 2 - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE - (longDegree - shortDegree) / 2 - shortDegree - dipToPx(10));
                    canvas.drawText(String.valueOf(i - 28), centerX, centerY + diameter / 2 - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE - (longDegree - shortDegree) / 2 - shortDegree - dipToPx(10), degreePaint);
                    canvas.rotate(-180f, centerX, centerY + diameter / 2 - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE - (longDegree - shortDegree) / 2 - shortDegree - dipToPx(10));
                    if (i == 128) {
                        canvas.rotate(2.30769f * 2, centerX, centerY);
                    } else {
                        canvas.rotate(2.30769f, centerX, centerY);
                    }
                }
                canvas.rotate(2.30769f, centerX, centerY);
            }
        }

        //整个弧
        canvas.drawArc(bgRect, startAngle, sweepAngle, false, allArcPaint);

        //设置渐变色
        rotateMatrix.setRotate(150, centerX, centerY);
        sweepGradient.setLocalMatrix(rotateMatrix);
        progressPaint.setShader(sweepGradient);

        //当前进度
        if (isNeedProgress) {
            canvas.drawArc(bgRect, startAngle, currentAngle, false, progressPaint);
        }

        if (isNeedContent) {
            canvas.drawText(String.format("%.0f", curValues), centerX, centerY, vTextPaint);
        }
        if (isNeedUnit) {
            canvas.drawText(hintString, centerX, centerY + textSize / 2.6f, hintPaint);
        }
        if (isNeedTitle) {
            RectF rectF = new RectF();
            rectF.left = centerX - dipToPx(32);
            rectF.top = centerY + textSize / 1.1f - dipToPx(11);
            rectF.right = centerX + dipToPx(32);
            rectF.bottom = centerY + textSize / 1.1f + dipToPx(4);
            canvas.drawRoundRect(rectF, dipToPx(15), dipToPx(15), vTextPaint);        //绘制圆角矩形
            canvas.drawText(titleString, centerX, centerY + textSize / 1.1f, curSpeedPaint);
        }

        invalidate();

    }

    /**
     * 设置最大值
     */
    public void setMaxValues(float maxValues) {
        this.maxValues = maxValues;
        k = sweepAngle / maxValues;
    }

    /**
     * 设置当前值
     */
    public void setCurrentValues(float currentValues) {
        if (currentValues > maxValues) {
            currentValues = maxValues;
        }
        if (currentValues < 0) {
            currentValues = 0;
        }
        this.curValues = currentValues;
        lastAngle = currentAngle;
        setAnimation(lastAngle, currentValues * k, aniSpeed);
    }

    /**
     * 设置整个圆弧宽度
     */
    public void setBgArcWidth(int bgArcWidth) {
        this.bgArcWidth = bgArcWidth;
    }

    /**
     * 设置进度宽度
     */
    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    /**
     * 设置速度文字大小
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置单位文字大小
     */
    public void setHintSize(int hintSize) {
        this.hintSize = hintSize;
    }


    public void setNeedProgress(boolean needProgress) {
        isNeedProgress = needProgress;
    }

    /**
     * 设置单位文字
     */
    public void setUnit(String hintString) {
        this.hintString = hintString;
        invalidate();
    }

    /**
     * 设置直径大小
     */
    public void setDiameter(int diameter) {
        this.diameter = dipToPx(diameter);
    }

    /**
     * 设置标题
     */
    private void setTitle(String title) {
        this.titleString = title;
    }

    /**
     * 设置是否显示标题
     */
    private void setIsNeedTitle(boolean isNeedTitle) {
        this.isNeedTitle = isNeedTitle;
    }

    /**
     * 设置是否显示单位文字
     */
    private void setIsNeedUnit(boolean isNeedUnit) {
        this.isNeedUnit = isNeedUnit;
    }

    /**
     * 设置是否显示外部刻度盘
     */
    private void setIsNeedDial(boolean isNeedDial) {
        this.isNeedDial = isNeedDial;
    }

    /**
     * 为进度设置动画
     */
    private void setAnimation(final float last, final float current, int length) {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                curValues = currentAngle / k;
                if (vTextPaint != null) {
                    if (0 <= curValues && curValues < 20f) {
                        vTextPaint.setColor(Color.parseColor("#FB8301"));
                        titleString = "心跳频率低";
                    } else if (20f <= curValues && curValues < 40f) {
                        vTextPaint.setColor(Color.parseColor("#FBB004"));
                        titleString = "心跳频率低";
                    } else if (40f <= curValues && curValues < 60f) {
                        vTextPaint.setColor(Color.parseColor("#C9C730"));
                        titleString = "心跳频率低";
                    } else if (60f <= curValues && curValues < 90f) {
                        vTextPaint.setColor(Color.parseColor("#71CC80"));
                        titleString = "心跳频率中";
                    } else if (90f <= curValues && curValues < 110f) {
                        vTextPaint.setColor(Color.parseColor("#71CC80"));
                        titleString = "心跳频率高";
                    }
                    else {
                        vTextPaint.setColor(Color.parseColor("#0598F6"));
                        titleString = "心跳频率高";
                    }
                }
            }
        });
        progressAnimator.start();
    }

    /**
     * dip 转换成px
     */
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 得到屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
