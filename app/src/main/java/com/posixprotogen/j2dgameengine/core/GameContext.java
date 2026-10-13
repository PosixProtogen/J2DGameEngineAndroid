package com.posixprotogen.j2dgameengine.core;

import com.posixprotogen.j2dgameengine.core.render.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.assets.SoundPlayer;
import com.posixprotogen.j2dgameengine.core.assets.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.assets.TextLoader;
import com.posixprotogen.j2dgameengine.core.entity.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.input.GestureListener;
import com.posixprotogen.j2dgameengine.core.render.GLVec2f;

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
