package com.emag.jester;

import android.graphics.PointF;

import java.util.List;

public class ScaleTransformer {
    private static String TAG = ScaleTransformer.class.getSimpleName();

    public static double getScale(List<Pointer> pointers) {
        final double currPointerDist = Math.hypot(pointers.get(0).currentPoint.x - pointers.get(1).currentPoint.x, pointers.get(0).currentPoint.y - pointers.get(1).currentPoint.y);
        final double prevPointerDist = Math.hypot(pointers.get(0).previousPoint.x - pointers.get(1).previousPoint.x, pointers.get(0).previousPoint.y - pointers.get(1).previousPoint.y);

        return currPointerDist / prevPointerDist;
    }

    public static PointF getAnchorPoint(List<Pointer> pointers, double scale) {
        final double t1x = pointers.get(0).previousPoint.x;
        final double t1y = pointers.get(0).previousPoint.y;
        final double t2x = pointers.get(1).previousPoint.x;
        final double t2y = pointers.get(1).previousPoint.y;

        return new PointF((float)((t1x + t2x) / 2), (float)((t1y + t2y) /2));
    }

    public static void transform(List<Pointer> pointers, PointF centroid, Transformation outTransformation) {
        outTransformation.scale = 1.f;
        if (pointers.size() != 2) {
            return;
        }

        double scale = getScale(pointers);
        outTransformation.scale = (float) scale;

        final PointF anchor = getAnchorPoint(pointers, scale);

        final double newCentroidX = (centroid.x - anchor.x) * scale + anchor.x;
        final double newCentroidY = (centroid.y - anchor.y) * scale + anchor.y;
        outTransformation.addTranslation((float) newCentroidX - centroid.x, (float) newCentroidY - centroid.y);;
    }
}
