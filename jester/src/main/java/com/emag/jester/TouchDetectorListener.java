package com.emag.jester;

import java.util.ArrayList;

public interface TouchDetectorListener {
    void handleTouch(ArrayList<Pointer> pointers);
}
