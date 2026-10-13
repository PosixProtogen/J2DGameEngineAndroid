package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.render.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.assets.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.entity.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.render.Layer;
import com.posixprotogen.j2dgameengine.core.render.GLSprite;
import com.posixprotogen.j2dgameengine.core.render.GLVec2f;

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
    public void setup() {
        SpriteLoader spriteLoader = getGameContext().getSpriteLoader();

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
        float deslocCalc = speed * getGameContext().getLastFrameTime();

        deslocA += deslocCalc;

        if (deslocA>=background.getSizes().height) {
            deslocA = 0.0f;
        }
        deslocB += deslocCalc*1.5f;

        if (deslocB>= starsFirstLayer.getSizes().height) {
            deslocB = 0.0f;
        }
        deslocC += deslocCalc * 2;

        if (deslocC>= starsSecondLayer.getSizes().height) {
            deslocC = 0.0f;
        }
        deslocD += deslocCalc * 2.5f;

        if (deslocD>= starThirdLayer.getSizes().height) {
            deslocD = 0.0f;
        }
    }

    @Override
    public void draw() {
        J2DCanvas j2DCanvas = getGameContext().getJ2DCanvas();

        j2DCanvas.setBackgroundColor(0.0f, 0.0f, 0.0f,0.0f);

        GLVec2f display = getGameContext().getDisplay();

        j2DCanvas.submit(Layer.BACKGROUND_FAR, background, display.width/2,(background.getSizes().height/2*-1)+1 + deslocA);
        j2DCanvas.submit(Layer.BACKGROUND_FAR, background, display.width/2,(background.getSizes().height/2*-3)+1 + deslocA);

        for (int i=0; i<4; i++) {
            for (int a=-1; a<8; a++) {

                j2DCanvas.submit(Layer.BACKGROUND_FAR, starsFirstLayer, (i* starsFirstLayer.getSizes().width)+ starsFirstLayer.getSizes().width/2,(a* starsFirstLayer.getSizes().height)+(starsFirstLayer.getSizes().height/2)+deslocB);
            }
        }

        for (int i=0; i<2; i++){
            for (int a=-1; a<4; a++) {
                j2DCanvas.submit(Layer.BACKGROUND_FAR, starsSecondLayer, (i* starsSecondLayer.getSizes().width)+ starsSecondLayer.getSizes().width/2,(a* starsSecondLayer.getSizes().height)+(starsSecondLayer.getSizes().height/2)+deslocC);
            }
        }

        for (int i=0; i<2; i++) {
            for(int a=-1; a<3; a++) {
                j2DCanvas.submit(Layer.BACKGROUND, starThirdLayer, (i* starThirdLayer.getSizes().width)+ starThirdLayer.getSizes().width/2,(a* starThirdLayer.getSizes().height)+(starThirdLayer.getSizes().height/2)+deslocD);
            }
        }
    }
}
