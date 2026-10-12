package com.posixprotogen.j2dgameengine.core.gameworld;

import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SoundPlayer;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.TextLoader;
import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.listeners.GestureListener;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

import java.util.List;

public interface GameContext {
    float getLastFrameTime();

    int getFps();

    GLVec2f getDisplay();

    void addEntity(AbstractEntity entity);

    void removeEntity(AbstractEntity entity);

    <T extends AbstractEntity> List<T> getEntities(Class<T> clazz);

    void addTouchListener(GestureListener listener);

    SpriteLoader getSpriteLoader();

    J2DCanvas getJ2DCanvas();

    SoundPlayer getSoundPlayer();

    TextLoader getTextLoader();
}
