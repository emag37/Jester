package com.emag.jester;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.Size;

import java.util.ArrayList;
import java.util.List;

public class Gesture {
    private static PointF getAnchorPoint(List<Pointer> pointers) {
        PointF anchor = new PointF(0, 0);

        for(Pointer p : pointers) {
            anchor.x += p.currentPoint.x;
            anchor.y += p.currentPoint.y;
        }

        anchor.x /= pointers.size();
        anchor.y /= pointers.size();

        return anchor;
    }

    private final PointF initAnchorPoint;
    public final int nPointers;
    private List<Pointer> currentPointers;
    private ScaleTransformer scaleTransformer;
    private RotationTransformer rotationTransformer;
    private TranslationTransformer translationTransformer;
    private final Matrix tm = new Matrix();

    public Gesture(List<Pointer> initPointers) {
        this.initAnchorPoint = getAnchorPoint(initPointers);
        nPointers = initPointers.size();

        translationTransformer = new TranslationTransformer(initAnchorPoint);
        if (nPointers == 2) {
            rotationTransformer = new RotationTransformer(initPointers);
            scaleTransformer = new ScaleTransformer(initPointers);
        }
        currentPointers = new ArrayList<>();
        for (Pointer init : initPointers) {
            Pointer toAdd = new Pointer(init.id);
            toAdd.update(init.currentPoint.x, init.currentPoint.y);
            currentPointers.add(toAdd);
        }
    }

    public void updateFromPointers(List<Pointer> newPointers) {
        if (newPointers.size() != nPointers) {
            throw new RuntimeException("Cannot change the number of pointers within the same gesture!");
        }
        for(Pointer newP : newPointers) {
            for (Pointer currentP : currentPointers) {
                if (currentP.equals(newP)) {
                    currentP.update(newP.currentPoint.x, newP.currentPoint.y);
                }
            }
        }
    }

    public Transformation getTransformationForPoints(@Size(2) float[] points) {
        Transformation transformation = new Transformation();
        if (scaleTransformer != null) {
            scaleTransformer.transform(currentPointers, transformation);     // % (relative "around" prev midpoint)
        }

        if (rotationTransformer != null) {
            rotationTransformer.transform(currentPointers, transformation);  // angle (relative "around" prev midpoint)
        }

        translationTransformer.transform(currentPointers, transformation); // vec, how much midpoints moved?

        tm.reset();
        tm.setTranslate(-initAnchorPoint.x, -initAnchorPoint.y);
        if (nPointers == 2) {
            tm.postRotate(transformation.rotationDegrees);
            tm.postScale(transformation.scale, transformation.scale);
        }
        tm.postTranslate(initAnchorPoint.x, initAnchorPoint.y);
        tm.postTranslate(transformation.tX, transformation.tY);

        float[] newPoints = new float[] {points[0], points[1]};
        tm.mapPoints(newPoints);

        transformation.tX = newPoints[0] - points[0];
        transformation.tY = newPoints[1] - points[1];
        return transformation;
    }
}
