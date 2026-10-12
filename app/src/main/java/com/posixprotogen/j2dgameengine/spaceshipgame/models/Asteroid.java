package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.enums.Layer;
import com.posixprotogen.j2dgameengine.core.utils.GLRect;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

import java.util.Random;


public class Asteroid extends AbstractEntity {

    private GLSprite asteroid;
    private float speed = 0.3f;
    private float h = -0.1f;
    private float posX;
    private float size;
    private boolean init = false;
    private float angle = 1;
    private int direction;
    private GLRect hitbox;

    public Asteroid(float posX, float size, float speed) {
        this.posX = posX;
        this.size = size;
        this.speed = speed;
        this.direction = new Random().nextInt(2);
    }

    @Override
    public void setup() {
        asteroid = getGameContext().getSpriteLoader().getTexture("asteroidb.png");
        asteroid.resize(size);
        init = true;
        hitbox = new GLRect(asteroid.getWidht(), asteroid.getHeight(), posX, h);
        hitbox.setPos(posX, h);
    }

    @Override
    public void update() {
        h += speed * getGameContext().getLastFrameTime();

        if (direction == 0) {
            angle += ((8.8f * getGameContext().getLastFrameTime()) * (speed*10));
        } else {
            angle -= ((8.8f * getGameContext().getLastFrameTime()) * (speed*10));
        }

        asteroid.setAngle(angle);
        if(h > 0.5f){
            //explode();
        }

        if(h > 1.1f){
            die();
        }

        hitbox.setPos(posX, h);
    }

    @Override
    public void draw() {
        getGameContext().getJ2DCanvas().submit(Layer.WORLD, asteroid, posX, h);
        //j2d.drawRect(hitbox);
    }

    public void explode() {
        getGameContext().addEntity(new Explosion(posX, h, size, speed));

        die();
    }

    public GLRect getHitbox(){
        //return new Rect(50,100,100,50);

        return hitbox;
    }
}
