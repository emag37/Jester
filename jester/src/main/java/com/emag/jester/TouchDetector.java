package com.emag.jester;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TouchDetector implements View.OnTouchListener {

    private static final String TAG = TouchDetector.class.getSimpleName();
    private ArrayList<Pointer> activePointers = new ArrayList<>();
    private ArrayList<TouchDetectorListener> listeners = new ArrayList<>();

    @Override
    public boolean onTouch(View v, @NonNull MotionEvent ev) {
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

        listeners
        .forEach(l-> l.handleTouch(activePointers));
        return true;
    }

    public void addListener(@NonNull TouchDetectorListener newListener) {
        if (listeners.stream().noneMatch(l -> l == newListener)) {
            listeners.add(newListener);
        }
    }

    public void removeListener(@NonNull TouchDetectorListener toRemove) {
        listeners.removeIf(l -> l == toRemove);
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
