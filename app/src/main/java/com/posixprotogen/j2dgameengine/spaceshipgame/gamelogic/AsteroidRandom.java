package com.posixprotogen.j2dgameengine.spaceshipgame.gamelogic;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.Asteroid;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidRandom extends AbstractEntity {
    private Random rand = new Random();
    private ArrayList<Asteroid> asteroids = new ArrayList();
    private long timeElapse = System.currentTimeMillis();

    @Override
    public void update() {
        ArrayList<Asteroid> asteroidsAlive = new ArrayList();
        for (Asteroid a : asteroids) {
            if(a.isAlive()){
                asteroidsAlive.add(a);
            }
        }
        asteroids = asteroidsAlive;

        if(System.currentTimeMillis() > timeElapse+500) {
            float posX = rand.nextFloat() * (getGameEngine().getWidth());
            float size = rand.nextFloat() * (0.1f - 0.05f) + 0.05f;
            float speed = rand.nextFloat() * (0.5f - 0.1f) + 0.1f;
            Asteroid asteroid = new Asteroid(posX, size, speed);

            getGameEngine().addEntity(asteroid);

            asteroids.add(asteroid);
            timeElapse = System.currentTimeMillis();
        }
    }
    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }
}
