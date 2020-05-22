package com.emag.jester;

import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import java.util.List;

public class TranslationTransformer {
    private static final String TAG = TranslationTransformer.class.getSimpleName();

    private static final int X = 0;
    private static final int Y = 1;

    private final PointF initialAnchorPoint;

    private PointF getCentroidTranslation(List<Pointer> pointers) {
        PointF pointMovedTo = new PointF(0,0 );

        for(Pointer p : pointers) {
            pointMovedTo.x += p.currentPoint.x;
            pointMovedTo.y += p.currentPoint.y;
        }

        pointMovedTo.x /= pointers.size();
        pointMovedTo.y /= pointers.size();

        pointMovedTo.offset(-initialAnchorPoint.x, -initialAnchorPoint.y);
        return pointMovedTo;
    }

    public void transform(List<Pointer> pointers, Transformation outTransformation) {
        final PointF movedto = getCentroidTranslation(pointers);

        outTransformation.tX = movedto.x;
        outTransformation.tY = movedto.y;
    }

    public TranslationTransformer(@NonNull PointF initialAnchorPoint) {
        this.initialAnchorPoint = initialAnchorPoint;
    }
}
