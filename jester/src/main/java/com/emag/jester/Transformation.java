package com.emag.jester;

import androidx.annotation.Size;

public class Transformation {
    public float tX = 0.f;
    public float tY = 0.f;
    public float scale = 1.f;
    public float rotationDegrees = 0.f;

    public void add(Transformation other) {
        tX += other.tX;
        tY += other.tY;
        scale *= other.scale;
        rotationDegrees += other.rotationDegrees;
    }
}
