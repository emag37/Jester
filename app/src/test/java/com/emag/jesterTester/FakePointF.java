package com.emag.jesterTester;

import android.graphics.PointF;

public class FakePointF extends PointF {
    public FakePointF(float x, float y) {
        super.x = x;
        super.y = y;
    }

    @Override
    public String toString() {
        return "(X: " + x + " Y: " + y + ")";
    }
}
