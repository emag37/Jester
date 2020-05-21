package com.emag.jester;

import android.graphics.PointF;

import java.util.List;

public class RotationTransformer {
    private static String TAG = RotationTransformer.class.getSimpleName();

    private static PointF locatePivot(Pointer pointerA, Pointer pointerB) {
        return getIntersectPoint(pointerA.currentPoint, pointerB.currentPoint, pointerA.previousPoint, pointerB.previousPoint);
    }

    private static PointF getIntersectPoint(PointF p0, PointF p1, PointF p2, PointF p3) {
        // Adapted from the C algorithm
        // https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect

        float s1x, s1y, s2x, s2y;
        s1x = p1.x - p0.x;
        s1y = p1.y - p0.y;
        s2x = p3.x - p2.x;
        s2y = p3.y - p2.y;

        float s, t;
        final float v = -s2x * s1y + s1x * s2y;
        s = (-s1y * (p0.x - p2.x) + s1x * (p0.y - p2.y)) / v;
        t = (s2x * (p0.y - p2.y) - s2y * (p0.x - p2.x)) / v;

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            return new PointF(p0.x + (t * s1x), p0.y + (t * s1y));
        }

        return null;
    }

    private static double getRotationRads(List<Pointer> pointers) {
        if (pointers.size() != 2) {
            return 0.f;
        }

        final float prevVectX = pointers.get(1).previousPoint.x - pointers.get(0).previousPoint.x;
        final float prevVectY = pointers.get(1).previousPoint.y - pointers.get(0).previousPoint.y;
        final float currVectX = pointers.get(1).currentPoint.x - pointers.get(0).currentPoint.x;
        final float currVectY = pointers.get(1).currentPoint.y - pointers.get(0).currentPoint.y;

        double angle =  Math.atan2(currVectY, currVectX) - Math.atan2(prevVectY, prevVectX);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle <= -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public static RotationEventData getRotationEvent(List<Pointer> pointers) {
        if (pointers.size() != 2) {
            return null;
        }

        double rotationRads = getRotationRads(pointers);
        PointF pivot = locatePivot(pointers.get(0), pointers.get(1));
        if (pivot == null) {
            return null;
        }

        return new RotationEventData((float) Math.toDegrees(rotationRads), pivot);
    }

    public static void transform(List<Pointer> pointers, Transformation outTransformation) {
        outTransformation.rotationDegrees = 0;

        if (pointers.size() != 2) {
            return;
        }

        final double rotationRads = getRotationRads(pointers);
        outTransformation.rotationDegrees = (float) Math.toDegrees(rotationRads);
    }
}
