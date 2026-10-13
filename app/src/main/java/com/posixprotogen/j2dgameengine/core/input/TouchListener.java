package com.posixprotogen.j2dgameengine.core.input;

import android.view.MotionEvent;

public interface TouchListener {
    void onTouch(MotionEvent event, float x, float y);
}
