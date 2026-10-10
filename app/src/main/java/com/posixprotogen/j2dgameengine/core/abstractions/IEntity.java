package com.posixprotogen.j2dgameengine.core.abstractions;

import com.posixprotogen.j2dgameengine.core.GameContext;
import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

public interface IEntity {

    void setup();

    void update();

    void draw();

    int getLayer();

    GLVec2f getPosition();

    boolean isAlive();
}
