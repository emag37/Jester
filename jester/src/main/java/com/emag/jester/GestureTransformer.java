package com.emag.jester;

import android.graphics.PointF;
import android.util.Log;

import java.util.List;

public class GestureTransformer {
   private String TAG = this.getClass().getSimpleName();
   private PointF currentCentroid;
   private Gesture currentGesture;
   private Transformation lastTransformation;

   public GestureTransformer(List<Pointer> initPointers, PointF initCentroid) {
      this.currentCentroid = initCentroid;
      currentGesture = new Gesture(initPointers);
      lastTransformation = new Transformation();
   }

   public void updateGesture(List<Pointer> pointers) {
     if (currentGesture.nPointers != pointers.size()) {
        Transformation thisTransformation = currentGesture.getTransformationForPoints(new float[] {currentCentroid.x,currentCentroid.y});
        currentCentroid = new PointF(currentCentroid.x + thisTransformation.tX, currentCentroid.y + thisTransformation.tY);
        lastTransformation.add(thisTransformation);

        currentGesture = new Gesture(pointers);
     }
      currentGesture.updateFromPointers(pointers);
   }

   public Transformation getTransformation() {
      Transformation retTransformation = currentGesture.getTransformationForPoints(new float[] {currentCentroid.x,currentCentroid.y});
      retTransformation.add(lastTransformation);

      return retTransformation;
   }
}
