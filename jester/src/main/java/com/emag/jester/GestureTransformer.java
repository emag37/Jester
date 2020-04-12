package com.emag.jester;

import android.graphics.PointF;

import java.util.List;

public class GestureTransformer {
   public static Transformation transformFromGesture(List<Pointer> pointers, PointF centroid) {
      Transformation retT = new Transformation();
      ScaleTransformer.transform(pointers,centroid, retT);
      RotationTransformer.transform(pointers, centroid, retT);
      TranslationTransformer.transform(pointers, retT);

      return retT;
   }
}
