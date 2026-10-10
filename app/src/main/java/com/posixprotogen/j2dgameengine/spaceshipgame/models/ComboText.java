package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.J2DCanvas;
import com.posixprotogen.j2dgameengine.core.SpriteLoader;
import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

public class ComboText extends AbstractEntity {

    private GLSprite alphabet;
    private String textString = "0";
    private float size = 0.05f;
    private int count = 0;
    private boolean hide = true;

    @Override
    public void setup() {
        alphabet = getGameContext().getSpriteLoader().getTexture("alphabet.png");
        alphabet.resize(0.05f);
        alphabet.setNumSprite(2);
    }

    long timeElapse = System.currentTimeMillis();

    @Override
    public void update() {
        if(size > 0.05f){
            size -= 0.1f * getGameContext().getLastFrameTime();
        }

        if (System.currentTimeMillis() > timeElapse + 2000) {
            hide = true;
        }
        alphabet.resize(size);
    }

    public void incrementCounter(){
        count++;
        textString = "" +count;
        size = 0.1f;

        timeElapse = System.currentTimeMillis();
        hide = false;
    }

    public void resetCounter(){
        count = 0;
        textString = "" +count;
        size = 0.1f;

        timeElapse = System.currentTimeMillis();
        hide = false;
    }

    @Override
    public void draw() {
        if(!hide){
            float pos = 0.0f;

            for(int i=0; i<textString.length(); i++){
                alphabet.setNumSprite(textString.charAt(i) - '0' + 26);
                getGameContext().getJ2DCanvas().drawSpritePosition(alphabet, 0.1f + pos, 0.5f);
                pos+=0.07;
            }
            alphabet.setNumSprite(23);
            getGameContext().getJ2DCanvas().drawSpritePosition(alphabet, 0.1f + pos, 0.5f);
        }
    }
}
