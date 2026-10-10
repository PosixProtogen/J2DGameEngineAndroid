package com.posixprotogen.j2dgameengine;

import com.posixprotogen.j2dgameengine.core.GameEngine;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.spaceshipgame.SpaceShipMain;

public class J2DGameEngineMain {

    public void loadTextures(SpriteLoader spriteLoader){
        spriteLoader.addTexture("background.png");
        spriteLoader.addTexture("starsoneb.png");
        spriteLoader.addTexture("ship.png");
        spriteLoader.addTexture("explosionb.png", 4, 4);
        spriteLoader.addTexture("asteroidb.png");
        spriteLoader.addTexture("shot.png");
        spriteLoader.addTexture("alphabet.png", 17, 3);
    }

    public void setupEntitiesGame(GameEngine gameEngine){
        gameEngine.addEntity(new SpaceShipMain());
    }
}
