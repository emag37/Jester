package com.emag.jester;

import java.util.ArrayList;

public class RotationGestureRecognizer implements TouchDetectorListener {
    private RotationEvent listener;

    public void setListener(RotationEvent listener) {
        this.listener = listener;
    }

    @Override
    public void handleTouch(ArrayList<Pointer> pointers) {
        RotationEventData event = RotationTransformer.getRotationEvent(pointers);

        if (event != null && listener != null) {
            listener.onRotation(pointers, event);
        }
    }
}
