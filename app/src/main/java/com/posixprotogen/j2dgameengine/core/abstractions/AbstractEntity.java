package com.posixprotogen.j2dgameengine.core.abstractions;

import com.posixprotogen.j2dgameengine.core.gameworld.GameContext;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

public abstract class AbstractEntity {

    private boolean alive = true;
    protected GLVec2f position = new GLVec2f(0, 0);
    private GameContext gameContext;

    public void setup() {

    }

    public void update() {

    }

    public void draw() {

    }

    public GLVec2f getPosition() {
        return position;
    }

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
        if (this.gameContext != null) {
            throw new RuntimeException("The GameContext cannot be changed.");
        }
        this.gameContext = gameContext;
    }
}
