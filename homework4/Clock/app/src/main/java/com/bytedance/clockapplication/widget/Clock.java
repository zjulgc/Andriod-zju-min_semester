package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;

    private boolean mShowAnalog = true;
    public Handler handler;

    public final int REFRESH = 0;
    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        size = Math.min(widthWithoutPadding, heightWithoutPadding);
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        numbersColor = Color.WHITE;

        HandlerThread handlerThread = new HandlerThread("background");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == REFRESH)
                    Clock.this.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                sendEmptyMessageDelayed(REFRESH, 1000);
            }
        };
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = Math.min(getWidth(), getHeight());

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor
        String[] ClockNum = {"12", "01", "02","03","04","05","06","07","08","09","10","11"};

        Paint mypaint = new Paint();
        mypaint.setTextSize(60);
        mypaint.setColor(hoursValuesColor);
        mypaint.setTextAlign(Paint.Align.CENTER);
        mypaint.setAntiAlias(true);

        Paint.FontMetrics fontMetrics = mypaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        int x;
        int y;
        int centerY = (int) (mCenterY + (bottom - top) / 2 - bottom - mRadius + mWidth * 0.09f);
        int r = mCenterY - centerY;

        for(int i = 0; i < ClockNum.length; ++ i)
        {
            x = (int) (mCenterX + r * Math.sin(Math.toRadians(i * 30)));
            y = (int) (mCenterY - r * Math.cos(Math.toRadians(i * 30)));
            canvas.drawText(ClockNum[i], x, y + (bottom - top) / 2 - bottom, mypaint);
        }
    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor
        Paint hourpaint = new Paint();
        hourpaint.setAntiAlias(true);
        hourpaint.setColor(hoursNeedleColor);
        hourpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        hourpaint.setStrokeWidth(mWidth * 0.025f);
        hourpaint.setStrokeCap(Paint.Cap.ROUND);

        Paint minupaint = new Paint();
        minupaint.setAntiAlias(true);
        minupaint.setColor(minutesNeedleColor);
        minupaint.setStyle(Paint.Style.FILL_AND_STROKE);
        minupaint.setStrokeWidth(mWidth * 0.018f);
        minupaint.setStrokeCap(Paint.Cap.ROUND);

        Paint secpaint = new Paint();
        secpaint.setAntiAlias(true);
        secpaint.setColor(secondsNeedleColor);
        secpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        secpaint.setStrokeWidth(mWidth * 0.01f);
        secpaint.setStrokeCap(Paint.Cap.ROUND);

        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int senconds = calendar.get(Calendar.SECOND);

        int hourneedlen = (int) (mRadius * 0.3f);
        int minuneedlen = (int) (mRadius * 0.5f);
        int secneedlen = (int) (mRadius * 0.7f);

        float hourangle = hours * 30 + (minutes + (float)senconds / 60) / 60 * 30;
        float minuangle = (minutes + (float)senconds / 60) / 60 * 360;
        float secdangle = (float)senconds / 60 * 360;

        canvas.save();
        canvas.drawLine(mCenterX,
                        mCenterY,
                        (float)(mCenterX + hourneedlen * Math.sin(Math.toRadians(hourangle))),
                        (float)(mCenterY - hourneedlen * Math.cos(Math.toRadians(hourangle))),
                        hourpaint
                );
        canvas.restore();

        canvas.save();
        canvas.drawLine(mCenterX,
                mCenterY,
                (float)(mCenterX + minuneedlen * Math.sin(Math.toRadians(minuangle))),
                (float)(mCenterY - minuneedlen * Math.cos(Math.toRadians(minuangle))),
                minupaint
        );
        canvas.restore();

        canvas.save();
        canvas.drawLine(mCenterX,
                mCenterY,
                (float)(mCenterX + secneedlen * Math.sin(Math.toRadians(secdangle))),
                (float)(mCenterY - secneedlen * Math.cos(Math.toRadians(secdangle))),
                secpaint
        );
        canvas.restore();

    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor
        Paint mypaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mypaint.setColor(DEFAULT_PRIMARY_COLOR);
        mypaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY,30, mypaint);

    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }


    public boolean isShowAnalog() {
        return mShowAnalog;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.sendEmptyMessage(REFRESH);
    }
}