package com.emag.jesterTester;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.emag.jester.Transformation;

public class TestView extends View {
    private static final String TAG = TestView.class.getSimpleName();
    PointF pivotPoint;
    Paint pivotPaint;
    Paint pointerPaint;
    Paint rectPaint;
    float currentRotation;
    PointF p1,p2;
    RectF rect;

    private void initPaint() {
        pivotPaint = new Paint();
        pivotPaint.setAntiAlias(true);
        pivotPaint.setARGB(255,255,0,0);
        pivotPaint.setStyle(Paint.Style.FILL);

        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setARGB(255,0,0,180);
        pointerPaint.setStyle(Paint.Style.FILL);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setARGB(255,200,200,200);
        resetRect();
    }

    public void resetRect() {
        rect = new RectF(200f,200f, 600f, 600f);
        currentRotation = 0.f;
        invalidate();
    }
    public TestView(Context context) {
        super(context);
        initPaint();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    public void setPointers(PointF p1, PointF p2) {
        this.p1= p1;
        this.p2= p2;
        invalidate();
    }

    public final RectF getRect() {
        return rect;
    }

    private void performScale(float scaleFactor) {
        Matrix mT = new Matrix();
        mT.setTranslate(-rect.centerX(), -rect.centerY());
        mT.postScale(scaleFactor, scaleFactor);
        mT.postTranslate(rect.centerX(), rect.centerY());
        mT.mapRect(rect);
    }

    public void transformRect(Transformation t) {
        //Log.d(TAG, "Rotating rectangle: " + rotationDeg);
        currentRotation += t.rotationDegrees;
        performScale(t.scale);
        rect.offset(t.tX, t.tY);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pivotPoint != null) {
            canvas.drawCircle(pivotPoint.x, pivotPoint.y, 15.0f, pivotPaint);
        }

        canvas.save();
        canvas.rotate(currentRotation, rect.centerX(), rect.centerY());
        canvas.drawRect(rect, rectPaint);
        canvas.restore();
        if (p1 != null) {
            canvas.drawCircle(p1.x, p1.y, 10.0f, pointerPaint);
        }
        if (p2 != null) {
            canvas.drawCircle(p2.x, p2.y, 10.0f, pointerPaint);
        }

    }


}
