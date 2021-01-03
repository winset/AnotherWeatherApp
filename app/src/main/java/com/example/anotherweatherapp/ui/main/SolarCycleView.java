package com.example.anotherweatherapp.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.anotherweatherapp.R;
import com.example.anotherweatherapp.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.anotherweatherapp.ui.main.MainFragment.TAG;


public class SolarCycleView extends View {


    private Paint mLinePaint;
    private Paint mSemicirclePaint;

    private int paddingLeft = 50;
    private int paddingRight = 50;
    private int bottomMargin = 100;
    //Semicircle
    private int topMargin = 200;
    private int circlePadding = 50;
    private PathMeasure circlePathMeasure;
    private Matrix matrix;
    private float length;
    private float disMultiplier;
    private float startPoint = 0;
    //Middle text
    private String mMiddleText;
    private float mMiddleTextSize;
    //Time text
    private int bottomTextMargin = 50;
    private float mBottomTextSize;
    private String sunrise = "8:00";
    private String sunset = "17:00";
    private Bitmap bitmap;

    private float[] pos;
    private float[] tan;

    private final int animationStep = 8;
    private Boolean startAnimation = false;


    public SolarCycleView(Context context) {
        super(context);
        init(null);
    }

    public SolarCycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SolarCycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SolarCycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setTime(Long start, Long end) {
        // convert UTC to current timezone
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        output.setTimeZone(TimeZone.getDefault());
        sunrise = output.format(start * 1000L);
        sunset = output.format(end * 1000L);

        //get current time and convert to UTC
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("HH:mm");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        outputFmt.format(time);

        long currentTime = outputFmt.getCalendar().getTime().getTime();

        Log.d(TAG, "setTime: ex " + (currentTime - start * 1000L));
        Log.d(TAG, "setTime: ex1 " + (end * 1000L - start * 1000L));
        disMultiplier = (float) (currentTime - (start * 1000L)) / (end * 1000L - start * 1000L);
        Log.d(TAG, "setTime: " + disMultiplier);
        Log.d(TAG, "setTime: current time " + currentTime);
        Log.d(TAG, "setTime: end time " + end * 1000);
        Log.d(TAG, "setTime: start time " + start * 1000);
        if (disMultiplier > 1)
            disMultiplier = 1;
        if (disMultiplier < 0)
            disMultiplier = 0;
        Log.d(TAG, "setTime: " + disMultiplier);

        //for update length
        invalidate();
    }

    private void init(@Nullable AttributeSet attrs) {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSemicirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SolarCycleView);

        int color = typedArray.getColor(R.styleable.SolarCycleView_line_color, Color.BLUE);
        float strokeWidth = typedArray.getFloat(R.styleable.SolarCycleView_stroke_width, 5f);
        mMiddleText = typedArray.getString(R.styleable.SolarCycleView_middle_text);
        if (mMiddleText == null) {
            mMiddleText = "Sunshine and sunrise";
        }

        mMiddleTextSize = typedArray.getFloat(R.styleable.SolarCycleView_middle_textSize, 40f);
        mBottomTextSize = typedArray.getFloat(R.styleable.SolarCycleView_bottom_textSize, 30f);
        mLinePaint.setColor(color);
        mLinePaint.setStrokeWidth(strokeWidth);
        mSemicirclePaint.setColor(color);
        mSemicirclePaint.setStrokeWidth(strokeWidth);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec),
                measureDimension(desiredHeight, heightMeasureSpec));
    }


    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize) {
            Log.e("SolarCycleView", "The view is too small, the content might get cut");
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBottomLine(canvas);
        drawSemicircle(canvas);
        drawMiddleText(canvas);
        drawBitmap(canvas);
        drawTime(canvas);
    }

    private void drawBottomLine(Canvas canvas) {
        canvas.drawLine(paddingLeft,
                getHeight() - bottomMargin,
                getWidth() - paddingRight,
                getHeight() - bottomMargin,
                mLinePaint);
    }

    private void drawSemicircle(Canvas canvas) {
        Path semicircle = new Path();
        Point topPoint = new Point(getWidth() / 2, -getHeight() + topMargin);
        Point endPoint = new Point(getWidth() - paddingRight - circlePadding,
                getHeight() - bottomMargin);

        mSemicirclePaint.setStyle(Paint.Style.STROKE);
        mSemicirclePaint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0));

        semicircle.moveTo(paddingLeft + circlePadding, getHeight() - bottomMargin);
        semicircle.quadTo(
                topPoint.x,
                topPoint.y,
                endPoint.x,
                endPoint.y);

        circlePathMeasure = new PathMeasure(semicircle, false);
        length = circlePathMeasure.getLength() * disMultiplier;
        Log.d(TAG, "drawSemicircle: " + length + " AAAAAAAA " + circlePathMeasure.getLength());

        matrix = new Matrix();
        pos = new float[2];
        tan = new float[2];

        canvas.drawPath(semicircle, mSemicirclePaint);
    }


    private void drawBitmap(Canvas canvas) {
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (bitmap == null){
            Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_wb_sunny_24);
            bitmap = convertToBitmap(drawable, 100, 100);
        }


        if (startAnimation){
            if (startPoint < length) {
                bitmapPosition(bitmap,startPoint);
                canvas.drawBitmap(bitmap, matrix, bitmapPaint);

                startPoint += animationStep;
                invalidate();
            } else {
                bitmapPosition(bitmap,length);
                canvas.drawBitmap(bitmap, matrix, bitmapPaint);
            }
        }else {
            bitmapPosition(bitmap,startPoint);
            canvas.drawBitmap(bitmap, matrix, bitmapPaint);
        }
    }

    public void startAnimation() {
        if (!startAnimation){
            startAnimation = true;
            invalidate();
        }
    }

    private void bitmapPosition(Bitmap bitmap,float point){
        int bm_offsetX, bm_offsetY;
        bm_offsetX = bitmap.getWidth() / 2;
        bm_offsetY = bitmap.getHeight() / 2;

        circlePathMeasure.getPosTan(point, pos, tan);
        matrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
        matrix.postTranslate(pos[0] - bm_offsetX, pos[1] - bm_offsetY);

    }

    private void drawTime(Canvas canvas) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(mBottomTextSize);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Point leftPoint = new Point((paddingLeft + circlePadding), getHeight() - bottomTextMargin);
        Point rightPoint = new Point(getWidth() - paddingRight - circlePadding,
                getHeight() - bottomTextMargin);

        canvas.drawText(sunrise, leftPoint.x, leftPoint.y, textPaint);
        canvas.drawText(sunset, rightPoint.x, rightPoint.y, textPaint);

    }


    private void drawMiddleText(Canvas canvas) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(mMiddleTextSize);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Point middlePoint = new Point((getWidth() / 2), getHeight() - bottomMargin - 20);

        canvas.drawText(mMiddleText, middlePoint.x, middlePoint.y, textPaint);
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

}
