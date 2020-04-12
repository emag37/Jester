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

    private static double getTranslation(double p, double p_prime, double scale) {
        return (p_prime - scale * p) / (1 - scale);
    }

    public static PointF getAnchorPoint(List<Pointer> pointers, double scale) {
        final double t1x = getTranslation(pointers.get(0).previousPoint.x,pointers.get(0).currentPoint.x, scale);
        final double t1y = getTranslation(pointers.get(0).previousPoint.y,pointers.get(0).currentPoint.y, scale);
        //double t2x = getTranslation(pointers.get(1).previousPoint.x,pointers.get(1).currentPoint.x, scale);
        //double t2y = getTranslation(pointers.get(1).previousPoint.y,pointers.get(1).currentPoint.y, scale);

        return new PointF((float)t1x, (float)t1y);
    }

    public static void transform(List<Pointer> pointers, PointF centroid, Transformation outTransformation) {
        outTransformation.scale = 1.f;
        if (pointers.size() != 2) {
            return;
        }

        double scale = getScale(pointers);
        if (scale >  0.995 && scale < 1.005) {
            return;
        }
        outTransformation.scale = (float) scale;

        final PointF anchor = getAnchorPoint(pointers, scale);

        final double newCentroidX = (centroid.x - anchor.x) * scale + anchor.x;
        final double newCentroidY = (centroid.y - anchor.y) * scale + anchor.y;
        outTransformation.addTranslation((float) newCentroidX - centroid.x, (float) newCentroidY - centroid.y);;
    }
}
