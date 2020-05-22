package com.emag.jester;

import android.view.MotionEvent;

import java.util.ArrayList;

public interface TouchDetectorListener {
    void handleTouch(MotionEvent ev, ArrayList<Pointer> pointers);
}
