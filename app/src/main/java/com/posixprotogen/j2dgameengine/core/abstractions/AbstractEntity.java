package com.posixprotogen.j2dgameengine.core.abstractions;

import com.posixprotogen.j2dgameengine.core.GameContext;
import com.posixprotogen.j2dgameengine.core.GameEngine;
import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

public abstract class AbstractEntity implements IEntity {

    private boolean alive = true;
    protected int layer = 0;
    protected GLVec2f position = new GLVec2f(0, 0);
    private GameContext gameContext;

    @Override
    public void setup() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

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

    public void die() {
        alive = false;
        getGameContext().removeEntity(this);
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }
}
