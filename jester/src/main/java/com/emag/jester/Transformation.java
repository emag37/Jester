package com.emag.jester;

import androidx.annotation.Size;

public class Transformation {
    public float tX = 0.f;
    public float tY = 0.f;
    public float scale = 1.f;
    public float rotationDegrees = 0.f;

    public void addTranslation(@Size(2) float[] xy) {
        tX += xy[0];
        tY += xy[1];
    }

    public void addTranslation(float x, float y) {
        tX += x;
        tY += y;
    }
}
