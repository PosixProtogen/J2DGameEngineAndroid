package com.posixprotogen.j2dgameengine.spaceshipgame.gamelogic;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.Asteroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidRandom extends AbstractEntity {
    private Random rand = new Random();
    private long timeElapse = System.currentTimeMillis();

    @Override
    public void update() {
        if (System.currentTimeMillis() > timeElapse + 500) {
            float posX = rand.nextFloat() * (getGameContext().getDisplay().width);
            float size = rand.nextFloat() * (0.1f - 0.05f) + 0.05f;
            float speed = rand.nextFloat() * (0.5f - 0.1f) + 0.1f;
            Asteroid asteroid = new Asteroid(posX, size, speed);

            getGameContext().addEntity(asteroid);

            timeElapse = System.currentTimeMillis();
        }
    }
}
