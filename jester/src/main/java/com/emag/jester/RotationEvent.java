package com.emag.jester;

import java.util.List;

public interface RotationEvent {
    void onRotation(List<Pointer> pointers, RotationEventData eventData);
}
