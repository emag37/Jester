package com.emag.jester;

import android.graphics.PointF;

public class RotationEventData {
    public float rotationDegrees;
    public PointF pivot;

    public RotationEventData(float degrees, PointF pivot) {
        this.rotationDegrees = degrees;
        this.pivot = pivot;
    }
}
