package com.posixprotogen.j2dgameengine;

import com.posixprotogen.j2dgameengine.core.GameContext;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.spaceshipgame.SpaceShipMain;

public class J2DGameEngineMain {

    public void setupGame(GameContext gameContext){
        SpriteLoader spriteLoader = gameContext.getSpriteLoader();
        spriteLoader.addTexture("background.png");
        spriteLoader.addTexture("starsoneb.png");
        spriteLoader.addTexture("ship.png");
        spriteLoader.addTexture("explosionb.png", 4, 4);
        spriteLoader.addTexture("asteroidb.png");
        spriteLoader.addTexture("shot.png");
        spriteLoader.addTexture("alphabet.png", 17, 3);

        gameContext.getSoundPlayer().addSound("sounds/laser_shot.wav");
        gameContext.getSoundPlayer().addSound("sounds/explosion.wav");

        gameContext.addEntity(new SpaceShipMain());
    }
}
