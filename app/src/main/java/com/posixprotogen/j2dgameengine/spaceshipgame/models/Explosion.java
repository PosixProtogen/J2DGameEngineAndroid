package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.enums.Layer;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

public class Explosion extends AbstractEntity {

    private GLSprite explosion;
    private float x;
    private float y;
    private float size;
    private float speed;

    public Explosion(float x, float y, float size, float speed) {
        this.x = x;
        this.y = y;
        this.size = size * 2;
        this.speed = speed;
    }

    @Override
    public void setup() {
        explosion = getGameContext().getSpriteLoader().getTexture("explosionb.png");
        explosion.resize(size);

        getGameContext().getSoundPlayer().playSound("sounds/explosion.wav", 5f);
    }

    long timeElapse = System.currentTimeMillis();

    @Override
    public void update() {
        y += speed * getGameContext().getLastFrameTime();

        if (System.currentTimeMillis() > timeElapse+64) {
            if(explosion.nextSprite()){
                die();
            }
            timeElapse = System.currentTimeMillis();
        }
    }

    @Override
    public void draw() {
        getGameContext().getJ2DCanvas().submit(Layer.EFFECT, explosion, x, y);
    }
}
