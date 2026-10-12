package com.posixprotogen.j2dgameengine.spaceshipgame.models;

import com.posixprotogen.j2dgameengine.core.TextSprite;
import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.enums.Layer;
import com.posixprotogen.j2dgameengine.core.utils.GLTint;

public class Hud extends AbstractEntity {

    private String comboText = "0";
    private TextSprite comboTextSprite;
    private float size = 0.05f;

    private TextSprite fpsSprite;

    private int count = 0;
    private boolean hide = true;
    private float colorDefault = 1.0f;

    @Override
    public void setup() {
        getGameContext().getTextLoader().loadFont("fonts/Orbitron-Bold.ttf");
        getGameContext().getTextLoader().loadFont("fonts/Iceland-Regular.ttf");

        comboTextSprite = getGameContext().getTextLoader().create("fonts/Orbitron-Bold.ttf");
        comboTextSprite.setSize(0.2f);
        comboTextSprite.setSpacing(0.005f);
        comboTextSprite.setTint(GLTint.modulate(1, colorDefault, colorDefault, 1));

        fpsSprite = getGameContext().getTextLoader().create("fonts/Iceland-Regular.ttf");
        fpsSprite.setSize(0.02f);
        fpsSprite.setSpacing(0.005f);
        fpsSprite.setTint(GLTint.modulate(0, 1, 0, 1));

    }

    long timeElapse = System.currentTimeMillis();

    @Override
    public void update() {
        if(size > 0.05f){
            size -= 0.2f * getGameContext().getLastFrameTime();
        }

        if (System.currentTimeMillis() > timeElapse + 2000) {
            hide = true;
        }
        comboTextSprite.setSize(size);
        comboTextSprite.setText(comboText);

        fpsSprite.setText(getGameContext().getFps());
    }

    public void incrementCounter(){
        if (count == 0) {
            comboTextSprite.setTint(GLTint.modulate(1, 1, 1, 1));
        }
        count++;
        comboText = "" + count + "x";
        size = 0.1f;

        if (colorDefault > 0) {
            colorDefault -= 0.02f;
        }
        comboTextSprite.setTint(GLTint.modulate(1, colorDefault, colorDefault, 1));

        timeElapse = System.currentTimeMillis();
        hide = false;
    }

    public void resetCounter(){
        count = 0;
        comboText = "ouch!";
        size = 0.1f;
        colorDefault = 1.0f;
        comboTextSprite.setTint(GLTint.modulate(1, 0, 1, 1));

        timeElapse = System.currentTimeMillis();
        hide = false;
    }

    @Override
    public void draw() {
        if (!hide) {
            getGameContext().getJ2DCanvas().submit(Layer.HUD, comboTextSprite, 0.01f, 0.5f);
        }
        getGameContext().getJ2DCanvas().submit(Layer.HUD, fpsSprite, 0.f, 0.02f);
    }
}
