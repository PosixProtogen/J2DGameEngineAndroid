package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

public class SpaceBackground extends AbstractEntity {
    private GLSprite background;
    private GLSprite starsFirstLayer;
    private GLSprite starsSecondLayer;
    private GLSprite starThirdLayer;
    private float speed = 0.03f;
    private float deslocA = 0.0f;
    private float deslocB = 0.0f;
    private float deslocC = 0.0f;
    private float deslocD = 0.0f;

    @Override
    public void setup(SpriteLoader spriteLoader) {
        background = spriteLoader.getTexture("background.png");
        starsFirstLayer = spriteLoader.getTexture("starsoneb.png");
        starsFirstLayer.resize(0.2f);
        starsSecondLayer = spriteLoader.getTexture("starsoneb.png");
        starsSecondLayer.resize(0.3f);
        starThirdLayer = spriteLoader.getTexture("starsoneb.png");
        starThirdLayer.resize(0.4f);
        background.resize(2);
    }

    @Override
    public void update() {
        float deslocCalc = speed * getGameEngine().getLastFrameTime();

        deslocA += deslocCalc;

        if (deslocA>=background.getSizes().v1) {
            deslocA = 0.0f;
        }
        deslocB += deslocCalc*1.5f;

        if (deslocB>= starsFirstLayer.getSizes().v1) {
            deslocB = 0.0f;
        }
        deslocC += deslocCalc * 2;

        if (deslocC>= starsSecondLayer.getSizes().v1) {
            deslocC = 0.0f;
        }
        deslocD += deslocCalc * 2.5f;

        if (deslocD>= starThirdLayer.getSizes().v1) {
            deslocD = 0.0f;
        }
    }

    @Override
    public void draw(J2DCanvas j2d) {
        j2d.setBackgroundColor(0.0f, 0.0f, 0.0f,0.0f);

        j2d.drawSprite(background, getGameEngine().getWidth()/2,(background.getSizes().v1/2*-1)+1 + deslocA);
        j2d.drawSprite(background, getGameEngine().getWidth()/2,(background.getSizes().v1/2*-3)+1 + deslocA);

        for (int i=0; i<4; i++) {
            for (int a=-1; a<8; a++) {
                j2d.drawSprite(starsFirstLayer, (i* starsFirstLayer.getSizes().v0)+ starsFirstLayer.getSizes().v0/2,(a* starsFirstLayer.getSizes().v1)+(starsFirstLayer.getSizes().v1/2)+deslocB);
            }
        }

        for (int i=0; i<2; i++){
            for (int a=-1; a<4; a++) {
                j2d.drawSprite(starsSecondLayer, (i* starsSecondLayer.getSizes().v0)+ starsSecondLayer.getSizes().v0/2,(a* starsSecondLayer.getSizes().v1)+(starsSecondLayer.getSizes().v1/2)+deslocC);
            }
        }

        for (int i=0; i<2; i++) {
            for(int a=-1; a<3; a++) {
                j2d.drawSprite(starThirdLayer, (i* starThirdLayer.getSizes().v0)+ starThirdLayer.getSizes().v0/2,(a* starThirdLayer.getSizes().v1)+(starThirdLayer.getSizes().v1/2)+deslocD);
            }
        }
    }
}
