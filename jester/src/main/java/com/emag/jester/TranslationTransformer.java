package com.emag.jester;

import androidx.annotation.Size;

import java.util.List;

public class TranslationTransformer {
    private static final String TAG = TranslationTransformer.class.getSimpleName();

    private static final int X = 0;
    private static final int Y = 1;

    private static float getDistance(@Size(2) float[] xy) {
        return (float) Math.hypot(xy[X], xy[Y]);
    }

    private static @Size(2) float[] getCentroidTranslation(List<Pointer> pointers) {
        float[] oldCentroid = {0,0};
        float[] newCentroid = {0,0};

        for(Pointer p : pointers) {
            oldCentroid[X] += p.previousPoint.x;
            oldCentroid[Y] += p.previousPoint.y;
            newCentroid[X] += p.currentPoint.x;
            newCentroid[Y] += p.currentPoint.y;
        }

        oldCentroid[X] /= pointers.size();
        oldCentroid[Y] /= pointers.size();
        newCentroid[X] /= pointers.size();
        newCentroid[Y] /= pointers.size();

        return new float[]{newCentroid[X] - oldCentroid[X], newCentroid[Y] - oldCentroid[Y]};
    }

    public static void transform(List<Pointer> pointers, Transformation outTransformation) {
        final float[] centroidT = getCentroidTranslation(pointers);

        if (getDistance(centroidT) < 1000) {
            outTransformation.addTranslation(centroidT);
        }
    }
}
