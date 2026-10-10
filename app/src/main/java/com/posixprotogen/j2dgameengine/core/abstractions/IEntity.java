package com.posixprotogen.j2dgameengine.core.abstractions;

import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

public interface IEntity {

    void setup(SpriteLoader spriteLoader);

    void update();

    void draw(J2DCanvas j2d);

    int getLayer();

    GLVec2f getPosition();

    boolean isAlive();
}
