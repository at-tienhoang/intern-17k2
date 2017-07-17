package vn.asiantech.internship.ui.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by quanghai on 06/07/2017.
 */
public class CustomView extends View {
    private int mUnit = 100;
    private static final int MARGIN = 30;
    private Paint mPaint;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.0f;

    public CustomView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setTextSize(50);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(5);
        }
        canvas.scale(mScaleFactor, mScaleFactor);
        drawPivotX(canvas);
        drawPivotY(canvas);
        drawArrow(canvas);
        drawParabol(canvas);
    }

    //y = 3x^2 - 4x + 1
    private void drawParabol(Canvas canvas) {
        float x = getWidth() / 2 + 10 * mUnit;
        float y = getHeight() / 2 - (3 * x * x - 4 * x + 1) * mUnit;
        for (double i = -10; i < 10; i += 0.05f) {
            float x2 = (float) (getWidth() / 2 + i * mUnit);
            float y2 = (float) (getHeight() / 2 - (3 * i * i - 4 * i + 1) * mUnit);
            canvas.drawLine(x, y, x2, y2, mPaint);
            x = x2;
            y = y2;
        }
    }

    private void drawArrow(Canvas canvas) {
        canvas.drawText("0", getWidth() / 2 - 50, getHeight() / 2 + 50, mPaint);

        Path path = new Path();
        path.moveTo(getWidth() - MARGIN, getHeight() / 2 - 10);
        path.lineTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth() - MARGIN, getHeight() / 2 + 10);

        path.moveTo(getWidth() / 2 + 10, MARGIN);
        path.lineTo(getWidth() / 2, 0);
        path.lineTo(getWidth() / 2 - 10, MARGIN);
        canvas.drawPath(path, mPaint);
    }

    private void drawPivotX(Canvas canvas) {
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
        for (int i = getWidth() / 2; i < getWidth(); i += mUnit) {
            canvas.drawLine(i, getHeight() / 2 + 10, i, getHeight() / 2 - 10, mPaint);
        }
        for (int i = getWidth() / 2; i > 0; i -= mUnit) {
            canvas.drawLine(i, getHeight() / 2 + 10, i, getHeight() / 2 - 10, mPaint);
        }
    }

    private void drawPivotY(Canvas canvas) {
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint);
        for (int i = getHeight() / 2; i < getHeight(); i += mUnit) {
            canvas.drawLine(getWidth() / 2 - 10, i, getWidth() / 2 + 10, i, mPaint);
        }
        for (int i = getHeight() / 2; i > 0; i -= mUnit) {
            canvas.drawLine(getWidth() / 2 - 10, i, getWidth() / 2 + 10, i, mPaint);
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        performClick();
////        mScaleDetector.onTouchEvent(event);
////        return true;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mUnit += 20;
//                break;
//        }
//        return true;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mUnit += 20;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Created by quanghai on 06/07/2017.
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            invalidate();
            return true;
        }
    }
}
