package com.emag.jester;

import android.graphics.PointF;
import android.util.Log;

import java.util.List;

public class RotationTransformer {
    private static String TAG = RotationTransformer.class.getSimpleName();

    private final float initAngle;

    private float getVectorAngle(List<Pointer> pointers) {
        if (pointers.size() != 2) {
            throw new RuntimeException("Cannot init RotationTransformer with nPointers != 2");
        }

        final float vectX = pointers.get(1).currentPoint.x - pointers.get(0).currentPoint.x;
        final float vectY = pointers.get(1).currentPoint.y - pointers.get(0).currentPoint.y;
        return (float) Math.atan2(vectY, vectX);
    }

    private float getRotationRads(List<Pointer> pointers) {
        float newAngle = getVectorAngle(pointers);

        float angle =  newAngle - initAngle;
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public void transform(List<Pointer> pointers, Transformation outTransformation) {
        final float rotationRads = getRotationRads(pointers);

        outTransformation.rotationDegrees = (float) Math.toDegrees(rotationRads);
    }

    public RotationTransformer(List<Pointer> initPointers) {
        initAngle = getVectorAngle(initPointers);
    }
}
