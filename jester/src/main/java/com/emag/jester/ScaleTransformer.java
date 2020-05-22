package com.emag.jester;

import java.util.List;

public class ScaleTransformer {
    private static String TAG = ScaleTransformer.class.getSimpleName();

    private final float MAX_INCREMENTAL_SCALE = 2.0f;
    private final double initPointerDist;

    private static double getPointerDist(List<Pointer> pointers) {
        return Math.hypot(pointers.get(0).currentPoint.x - pointers.get(1).currentPoint.x, pointers.get(0).currentPoint.y - pointers.get(1).currentPoint.y);
    }

    public double getScale(List<Pointer> pointers) {
        return getPointerDist(pointers) / initPointerDist;
    }

    public void transform(List<Pointer> pointers, Transformation outTransformation) {
        if (pointers.size() != 2) {
            throw new RuntimeException("Cannot get scale transform with nPointers != 2");
        }

        double scale = getScale(pointers);
        if (Math.abs(scale - outTransformation.scale) < MAX_INCREMENTAL_SCALE) {
            outTransformation.scale = (float) scale;
        }
    }

    public ScaleTransformer(List<Pointer> initPointers) {
        if (initPointers.size() != 2) {
            throw new RuntimeException("Cannot init ScaleTransformer with nPointers != 2");
        }

        initPointerDist = getPointerDist(initPointers);
    }
}
