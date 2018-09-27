package com.example.baohui.carapp_version10.view;

/**
 * Created by BaoHui on 2018/6/1.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.baohui.carapp_version10.R;

public class SixangleView extends View {

    private float[] params = new float[6];

    private Point[][] points;

    private Point[] pointsabilty = new Point[6];

    private int color1 = Color.rgb(51, 205, 207);

    private Paint paint, paint1, paint2, painttxt;

    private int radius = 250;

    private int textsize = 40;

    private static final double  Pai = 3.1415926;
    CharSequence[] str ;

    public SixangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        // TODO Auto-generated constructor stub
    }


    private void init(Context mContext, AttributeSet attrs) {

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SixangleView);

        points = new Point[5][6];
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color1);

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);


        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        for (int i = 0; i < 6; i++) {
            pointsabilty[i] = new Point();
        }
        painttxt = new Paint();
        painttxt.setAntiAlias(true);
        painttxt.setColor(Color.WHITE);

        painttxt.setTextSize(a.getDimension(R.styleable.SixangleView_txtSize, textsize));
        paint.setColor(a.getColor(R.styleable.SixangleView_sixangle_edgecolor,color1));
        paint1.setColor(a.getColor(R.styleable.SixangleView_sixangle_fillcolor,Color.argb(60, 51, 205, 207)));
        paint2.setColor(a.getColor(R.styleable.SixangleView_sixangle_secondfillcolor,Color.argb(180, 255, 126, 39)));


        str= a.getTextArray(R.styleable.SixangleView_sixangle_strarray);

        a.recycle();
    }

    /*******
     * 画最外围的圆点和直线
     *
     * @param canvas
     */
    private void canvesDotAndLine(Canvas canvas) {
        if (points[0][0] == null) {
            return;
        }
        Point point = null;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        for (int i = 0; i < 7; i++) {
            canvas.drawCircle(points[0][i % 6].x, points[0][i % 6].y, 10, paint);
            canvas.drawLine(cx, cy, points[0][i % 6].x, points[0][i % 6].y, paint);
        }
        for (int i = 1; i < 5; i++) {
            Point tempPointpoint = null;
            for (int j = 0; j < 7; j++) {

                tempPointpoint = points[i][j % 6];
                if (j == 0) {
                    point = tempPointpoint;
                    continue;
                }

                canvas.drawLine(point.x, point.y, tempPointpoint.x, tempPointpoint.y, paint);
                point = tempPointpoint;
            }

        }

    }

    /********
     * 绘制文字
     *
     * @param canvas
     */
    private void canvesDrawText(Canvas canvas) {
        if (points[0][0] == null) {
            return;
        }
        float sm = painttxt.measureText(str[1].toString());
        //float sh = painttxt.descent() - painttxt.ascent();

        FontMetrics fm = painttxt.getFontMetrics();
        float sh =(int) Math.ceil(fm.descent - fm.ascent) + 2;

        canvas.drawText(str[1].toString(), points[0][1].x-sm/2, points[0][1].y-5-(int) Math.ceil(fm.bottom - fm.leading), painttxt);
        sm = painttxt.measureText(str[4].toString());
        canvas.drawText(str[4].toString(), points[0][4].x-sm/2, points[0][4].y+sh, painttxt);

        sm = painttxt.measureText(str[2].toString());
        canvas.drawText(str[2].toString(), points[0][2].x+15, points[0][2].y, painttxt);
        sm = painttxt.measureText(str[3].toString());
        canvas.drawText(str[3].toString(), points[0][3].x+15, points[0][3].y+sh/2, painttxt);

        sm = painttxt.measureText(str[0].toString());
        canvas.drawText(str[0].toString(), points[0][0].x-15-sm, points[0][0].y, painttxt);
        sm = painttxt.measureText(str[5].toString());
        canvas.drawText(str[5].toString(), points[0][5].x-15-sm, points[0][5].y+sh/2, painttxt);



    }

    private void canvesArea(Canvas canvas) {
        if (points[0][0] == null) {
            return;
        }

        for (int i = 1; i < 5; i++) {
            Path path = new Path();
            path.moveTo(points[i][0].x, points[i][0].y);
            path.lineTo(points[i][1].x, points[i][1].y);
            path.lineTo(points[i][2].x, points[i][2].y);
            path.lineTo(points[i][3].x, points[i][3].y);
            path.lineTo(points[i][4].x, points[i][4].y);
            path.lineTo(points[i][5].x, points[i][5].y);
            path.close();
            canvas.drawPath(path, paint1);
        }

    }

    /******
     * 绘制能力值
     *
     * @param canvas
     */
    private void canvesAblity(Canvas canvas) {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int cx = w / 2;
        int cy = h / 2;
        for (int j = 0; j < 6; j++) {
            double n = (Pai / 6) * (1 + j * 2);
            pointsabilty[j].y = cy - (int) (Math.sin(n) * ((radius*4)/5 * (params[j] / 10)));
            pointsabilty[j].x = cx - (int) (Math.cos(n) * ((radius*4)/5 * (params[j] / 10)));
        }
        Path path = new Path();
        path.moveTo(pointsabilty[0].x, pointsabilty[0].y);
        path.lineTo(pointsabilty[1].x, pointsabilty[1].y);
        path.lineTo(pointsabilty[2].x, pointsabilty[2].y);
        path.lineTo(pointsabilty[3].x, pointsabilty[3].y);
        path.lineTo(pointsabilty[4].x, pointsabilty[4].y);
        path.lineTo(pointsabilty[5].x, pointsabilty[5].y);
        path.close();
        canvas.drawPath(path, paint2);

    }

    /**********
     *
     * 根据项目的顺序填充能力值
     *
     */
    public void setData(String params0, String params1, String params2, String params3, String params4,
                        String params5) {

        try {
            Float float0 = Float.valueOf(params0);
            params[0] = float0.floatValue();
            Float float1 = Float.valueOf(params1);
            params[1] = float1.floatValue();
            Float float2 = Float.valueOf(params2);
            params[2] = float2.floatValue();
            Float float3 = Float.valueOf(params3);
            params[3] = float3.floatValue();
            Float float4 = Float.valueOf(params4);
            params[4] = float4.floatValue();
            Float float5 = Float.valueOf(params5);
            params[5] = float5.floatValue();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvesDotAndLine(canvas);
        canvesArea(canvas);
        canvesAblity(canvas);
        canvesDrawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        initPoints(w,h);
    }

    /***********
     * 初始化六边形的坐标值
     * @param w
     * @param h
     */
    private void initPoints(int w ,int h){
        int cx = w / 2;
        int cy = h / 2;

        FontMetrics fm = painttxt.getFontMetrics();
        float sh =(int) Math.ceil(fm.descent - fm.ascent) + 2;

        radius = cx<cy? cx-(int)sh-5:cy-(int)sh-5;
        int degreen = 0;
        for (int i = 0; i < 5; i++) {
            degreen = (radius / 5) * i;
            for (int j = 0; j < 6; j++) {
                points[i][j] = new Point();
                double n = (Pai / 6) * (1 + j * 2);
                //计算六边行的坐标值
                points[i][j].y = cy - (int) (Math.sin(n) * (radius - degreen));
                points[i][j].x = cx - (int) (Math.cos(n) * (radius - degreen));
            }

        }
    }

}
