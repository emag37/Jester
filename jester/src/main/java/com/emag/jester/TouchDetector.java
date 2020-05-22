package com.emag.jester;

import android.util.SizeF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TouchDetector {

    private static final String TAG = TouchDetector.class.getSimpleName();
    private ArrayList<Pointer> activePointers = new ArrayList<>();
    private ArrayList<TouchDetectorListener> listeners = new ArrayList<>();

    public TouchDetector() {
    }

    public void updateWithTouch(@NonNull MotionEvent ev) {
        int action = ev.getActionMasked();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
            case (MotionEvent.ACTION_MOVE) :
            case MotionEvent.ACTION_POINTER_DOWN:
                updateActivePointer(ev);
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case (MotionEvent.ACTION_UP) :
            case (MotionEvent.ACTION_CANCEL) :
                deactivatePointer(ev);
                break;
            default :
                break;
        }
    }

    public List<Pointer> getActivePointers() {
        return activePointers;
    }

    private void updateActivePointer(MotionEvent ev) {
        for (int i=0; i< ev.getPointerCount(); i++) {
            Pointer pt = getActivePointer(ev.getPointerId(i));
            pt.update(ev.getX(i), ev.getY(i));
        }
    }

    private void deactivatePointer(MotionEvent ev) {
        for (int i=0; i< ev.getPointerCount(); i++) {
            deactivatePointer(ev.getPointerId(i));
        }
    }

    private void deactivatePointer(final int pointerId) {
        activePointers.removeIf(p -> p.id == pointerId);
    }

    private @NonNull Pointer getActivePointer(final int pointerId) {
        Pointer found = activePointers.stream()
                .filter(p -> p.id == pointerId)
                .findFirst()
                .orElse(null);
        if (found != null) {
            return found;
        }
        Pointer newPt = new Pointer(pointerId);
        activePointers.add(newPt);
        return newPt;
    }

}
