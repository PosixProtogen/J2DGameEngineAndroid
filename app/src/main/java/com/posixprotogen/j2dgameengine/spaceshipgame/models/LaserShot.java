package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.enums.Layer;
import com.posixprotogen.j2dgameengine.core.utils.GLRect;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

public class LaserShot extends AbstractEntity {

    public float w;
    public float h = 0.72f;
    public float speed = 1.8f;
    private GLSprite shot;
    private GLRect hitbox;

    public LaserShot(float w, float h) {
        this.h = h - 0.08f;
        this.w = w;
    }

    @Override
    public void setup() {
        shot = getGameContext().getSpriteLoader().getTexture("shot.png");
        shot.resize(0.06f);
        hitbox = new GLRect(shot.getWidht(), shot.getHeight(), w, h);
        hitbox.setPos(w, h);

        getGameContext().getSoundPlayer().playSound("sounds/laser_shot.wav", 5f);
    }

    @Override
    public void update() {
        hitbox.setPos(w, h);
        h -= speed * getGameContext().getLastFrameTime();

        if (h<0) {
            die();
        }
    }

    @Override
    public void draw() {
        getGameContext().getJ2DCanvas().submit(Layer.PROJECTILE, shot, w, h);
    }

    public GLRect getHitbox(){
        //return new Rect(50,100,100,50);
        return hitbox;
    }

    public void destroy(){
        die();
    }
}
