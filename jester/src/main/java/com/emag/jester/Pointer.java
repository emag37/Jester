package com.emag.jester;

import android.graphics.PointF;

import androidx.annotation.Nullable;

public class Pointer implements Cloneable {
    public final int id;
    public PointF currentPoint;

    public void update(float newX, float newY) {
        if (currentPoint != null) {
            currentPoint.set(newX, newY);
        } else {
            currentPoint = new PointF(newX,newY);
        }
    }

    public Pointer(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Pointer && ((Pointer)obj).id == id;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
