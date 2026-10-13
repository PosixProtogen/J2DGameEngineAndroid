package com.posixprotogen.j2dgameengine.spaceshipgame;


import com.posixprotogen.j2dgameengine.core.entity.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.input.GestureListener;
import com.posixprotogen.j2dgameengine.core.render.GLRect;
import com.posixprotogen.j2dgameengine.spaceshipgame.gamelogic.AsteroidRandom;
import com.posixprotogen.j2dgameengine.spaceshipgame.gamelogic.CollisionCore;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.PlayerShip;
import com.posixprotogen.j2dgameengine.spaceshipgame.models.SpaceBackground;

public class SpaceShipMain extends AbstractEntity {
    private SpaceBackground spaceBackground;
    private PlayerShip playerShip;
    private GLRect pointer = new GLRect(0.1f, 0.1f, 0.0f, 0.0f);

    @Override
    public void setup() {
        spaceBackground = new SpaceBackground();
        getGameContext().addEntity(spaceBackground);

        playerShip = new PlayerShip();
        getGameContext().addEntity(playerShip);

        AsteroidRandom asteroidRandom = new AsteroidRandom();
        getGameContext().addEntity(asteroidRandom);

        getGameContext().addEntity(new CollisionCore(playerShip));
        getGameContext().addTouchListener(new Touch());

        getGameContext().getSoundPlayer().playMusic("musics/The_spaceship_is_ready.mp3", 8f, true, 45);
    }

    class Touch implements GestureListener{

        private boolean moveShip = false;
        @Override
        public void onDown(float x, float y) {
            pointer.setPos(x, y);
            if(pointer.intersect(playerShip.getHitbox())){
                moveShip = true;
                playerShip.startFire();
            }
        }

        @Override
        public void onUp(float x, float y) {
            pointer.setPos(0.0f, 0.0f);
            moveShip = false;
            playerShip.stopFire();
        }

        @Override
        public void onMoving(float x, float y) {
            if (moveShip) {
                playerShip.fireAndMove(x, y);
            }
        }
    }
}
