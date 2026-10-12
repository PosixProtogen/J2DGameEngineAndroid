package com.posixprotogen.j2dgameengine.spaceshipgame.gamelogic;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.Asteroid;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.Hud;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.LaserShot;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.PlayerShip;

import java.util.List;

public class CollisionCore extends AbstractEntity {

    private PlayerShip playerShip;
    private Hud hud;

    public CollisionCore(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public void setup() {
        hud = new Hud();
        getGameContext().addEntity(hud);
    }

    @Override
    public void update() {
        List<Asteroid> asteroidsAlive = getGameContext().getEntities(Asteroid.class);
        List<LaserShot> laserShots = getGameContext().getEntities(LaserShot.class);

        for (Asteroid asteroid : asteroidsAlive) {
            if (asteroid.getHitbox() == null){
                continue;
            }

            for (LaserShot laserShot : laserShots) {
                if (laserShot.getHitbox() == null){
                    continue;
                }

                if(asteroid.getHitbox().intersect(laserShot.getHitbox()) && laserShot.isAlive()){
                    hud.incrementCounter();
                    asteroid.explode();
                    laserShot.destroy();
                }
            }

            if (playerShip.isHitMe(asteroid.getHitbox())) {
                hud.resetCounter();
                asteroid.explode();
            }
        }
    }
}
