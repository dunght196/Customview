package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomClock extends View {
    private Paint mPaint;
    private int mColorStrock;
    private int mWidthStrock;
    private int mXTitle;
    private int yTitle;
    private String title;

    public CustomClock(Context context) {
        super(context);
    }

    public CustomClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inits(context, attrs);
    }

    private void inits(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomClock_XML);
        mColorStrock = typedArray.getColor(R.styleable.CustomClock_XML_color_strock, Color.GREEN);
        mWidthStrock = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_XML_width_strock_circle, 20);
        mXTitle = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_XML_x_title, 200);
        yTitle = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_XML_y_title, 200);
        title = typedArray.getString(R.styleable.CustomClock_XML_title);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //set style cho paint
        mPaint.setStyle(Paint.Style.STROKE);
        //set size text
        mPaint.setTextSize(100);

        float t = 1080 / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        Log.d("dz196", "widthDp =" + t);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 800;
        int desiredHeight = 600;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        Log.d("dz196", "widthSize =" + widthSize);
        Log.d("dz196", "heightSize =" + heightSize);

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            Log.d("dz196", "EXACTLY ");
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Log.d("dz196", "AT_MOST");
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            Log.d("dz196", "UNSPECIFIED ");
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectOval = new RectF(0, 0, 200, 200);
        Log.d("dz196", "Rect:" + rectOval.left + "," + rectOval.right + "," + rectOval.top + "," + rectOval.right);
        mPaint.setStrokeWidth(10);
//        mPaint.setColor(mColorStrock);
        canvas.drawRect(rectOval, mPaint);
      /*  mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.parseColor("#8BC34A"));
        canvas.drawText(title, mXTitle, yTitle, mPaint);*/
    }
}
