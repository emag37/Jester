package com.emag.jester;

import android.graphics.PointF;

import androidx.annotation.Nullable;

public class Pointer {
    public final int id;
    public PointF currentPoint;
    public PointF previousPoint;

    public void update(float newX, float newY) {
        if (currentPoint != null) {
            previousPoint.set(currentPoint);
            currentPoint.set(newX, newY);
        } else {
            currentPoint = new PointF(newX,newY);
            previousPoint = new PointF(currentPoint.x, currentPoint.y);
        }
    }

    public Pointer(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Pointer && ((Pointer)obj).id == id;
    }
}
