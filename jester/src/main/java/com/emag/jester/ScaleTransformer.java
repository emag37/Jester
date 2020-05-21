package com.emag.jester;

import java.util.List;

public class ScaleTransformer {
    private static String TAG = ScaleTransformer.class.getSimpleName();

    public static double getScale(List<Pointer> pointers) {
        final double currPointerDist = Math.hypot(pointers.get(0).currentPoint.x - pointers.get(1).currentPoint.x, pointers.get(0).currentPoint.y - pointers.get(1).currentPoint.y);
        final double prevPointerDist = Math.hypot(pointers.get(0).previousPoint.x - pointers.get(1).previousPoint.x, pointers.get(0).previousPoint.y - pointers.get(1).previousPoint.y);

        return currPointerDist / prevPointerDist;
    }

    public static void transform(List<Pointer> pointers, Transformation outTransformation) {
        outTransformation.scale = 1.f;
        if (pointers.size() != 2) {
            return;
        }

        double scale = getScale(pointers);
        outTransformation.scale = (float) scale;
    }
}
