package com.example.baohui.carapp_version10.view;

/**
 * 自定义View的对这两个属性应该不陌生.：Paint画笔, Canvas画布
 * Created by BaoHui on 2018/4/21.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.baohui.carapp_version10.R;

public class CirclePgBar extends View {

    //背景绘制
    private Paint mBackPaint;
    //前景绘制
    private Paint mFrontPaint;
    //文字绘制
    private Paint mTextPaint;
    //数字绘制
    private Paint mTextPaintnumber;
    //图片绘制
    private Rect heartpic;
    //画笔宽度
    private float mStrokeWidth = 50;
    //一半的画笔宽度
    private float mHalfStrokeWidth = mStrokeWidth / 2;
    //半径
    private float mRadius = 300;
    //构造矩形
    private RectF mRect;
    //进度
    private int mProgress = 0;
    //目标值，想改多少就改多少
    private int mTargetProgress = 80;
    //最大值
    private int mMax = 100;

    //最终值
    private String end;
    private int mWidth;
    private int mHeight;


    public CirclePgBar(Context context) {
        super(context);
        init();
    }

    public CirclePgBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePgBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCurrentValues(String currentValues)
    {
        String str = "";
        str = currentValues;
        this.end = str;
    }

    //完成相关参数初始化
    private void init() {
        mBackPaint = new Paint();
        //设置颜色
        mBackPaint.setColor(Color.WHITE);
        //设置抗锯齿
        mBackPaint.setAntiAlias(true);
        //画笔的样式 FILL：填充内容；STROKE：描边；FILL_AND_STROKE：填充内容并描边。
        mBackPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的宽度
        mBackPaint.setStrokeWidth(mStrokeWidth);

        mFrontPaint = new Paint();
        mFrontPaint.setColor(Color.RED);
        mFrontPaint.setAntiAlias(true);
        mFrontPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的线帽 ROUND：圆形 SQUARE：方形
        mFrontPaint.setStrokeCap(Paint.Cap.ROUND);
        mFrontPaint.setStrokeWidth(mStrokeWidth);


        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(80);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextPaintnumber = new Paint();
        mTextPaintnumber.setColor(Color.RED);
        mTextPaintnumber.setAntiAlias(true);
        mTextPaintnumber.setTextSize(120);
        mTextPaintnumber.setTextAlign(Paint.Align.CENTER);

        setCurrentValues(end);
    }


    //重写测量大小的onMeasure方法和
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    //重写绘制View的核心方法onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        initRect();
        //结束点=进度占的总份数
        float angle = mProgress / (float) mMax * 360;
        //用mBackPaint画园，圆心坐标（mWidth / 2, mHeight / 2）半径 mRadius
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBackPaint);
        //用mFrontPaint画弧 起始点-234，起始点开始需要显示的多少角度到结束点为angle 如果为true有连线，
        canvas.drawArc(mRect, -234, angle, false, mFrontPaint);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.heart2);
        int picbitwith=bitmap.getWidth();
        int picbitheight= bitmap.getHeight();
        heartpic=new Rect(mWidth / 2-picbitwith/4, 80, mWidth / 2-picbitwith/4+ picbitwith/2,80+ picbitheight/2);
       canvas.drawBitmap(bitmap,null,heartpic,null);//限定图片显示范围



        //用mTextPaint画文字，drawText (String text, float x, float y, Paint paint)//坐标绘制
        canvas.drawText(end,mWidth / 2 + mHalfStrokeWidth, mHeight / 2 + mHalfStrokeWidth-20, mTextPaintnumber);
        canvas.drawText("次/分钟",mWidth / 2 + mHalfStrokeWidth, mHeight / 2 + mHalfStrokeWidth+130, mTextPaint);
        //canvas.drawText(mProgress + "%", mWidth / 2 + mHalfStrokeWidth, mHeight / 2 + mHalfStrokeWidth, mTextPaint);
        if (mProgress < mTargetProgress) {
            mProgress += 1;
            invalidate();
        }

    }

    public int getRealSize(int measureSpec) {
        int result = 1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算，直径+画笔宽度
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }

        return result;
    }

    private void initRect() {
        if (mRect == null) {
            mRect = new RectF();
            //直径
            int viewSize = (int) (mRadius * 2);
            //边界设置
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;
            mRect.set(left, top, right, bottom);
        }
    }


}
