package com.emag.jester;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;

import androidx.annotation.Size;

import java.util.List;

public class GestureTransformer {

   private static final int X = 0;
   private static final int Y = 1;


   private static PointF getInitialAnchorPoint(List<Pointer> pointers) {
      PointF anchor = new PointF(0, 0);

      for(Pointer p : pointers) {
         anchor.x += p.previousPoint.x;
         anchor.y += p.previousPoint.y;
      }

      anchor.x /= pointers.size();
      anchor.y /= pointers.size();

      return anchor;
   }

   public static Transformation transformFromGesture(List<Pointer> pointers, PointF centroid) {
      Transformation retT = new Transformation();
      ScaleTransformer.transform(pointers, centroid, retT);     // % (relative "around" prev midpoint)
      RotationTransformer.transform(pointers, centroid, retT);  // angle (relative "around" prev midpoint)
      TranslationTransformer.transform(pointers, retT);         // vec, how much midpoints moved?

      PointF anchorPoint = getInitialAnchorPoint(pointers);

      Matrix tm = new Matrix();
      tm.setTranslate(-anchorPoint.x, -anchorPoint.y);
      tm.postRotate(retT.rotationDegrees);
      tm.postScale(retT.scale, retT.scale);
      tm.setTranslate(anchorPoint.x, anchorPoint.y);
      tm.setTranslate(retT.tX, retT.tY);

      float[] points = new float[] {centroid.x, centroid.y};
      tm.mapPoints(points);

      retT.tX = points[0] - centroid.x;
      retT.tY = points[1] - centroid.y;

      return retT;
   }
}
