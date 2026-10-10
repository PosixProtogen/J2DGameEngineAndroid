package com.posixprotogen.j2dgameengine.core.listeners;

public interface GestureListener {
    void onDown(float x, float y);

    void onUp(float x, float y);

    void onMoving(float x, float y);
}
