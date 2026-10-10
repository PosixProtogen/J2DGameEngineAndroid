package com.posixprotogen.j2dgameengine.core.abstractions;

import com.posixprotogen.j2dgameengine.core.GameEngine;
import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

public abstract class AbstractEntity implements IEntity {
    protected boolean alive = true;
    protected int layer = 0;
    protected GLVec2f position = new GLVec2f(0, 0);
    private GameEngine gameEngine;

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void setup(SpriteLoader spriteLoader) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(J2DCanvas j2d) {

    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public GLVec2f getPosition() {
        return position;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
}
